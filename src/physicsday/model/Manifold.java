package physicsday.model;

import physicsday.util.Vector;

public class Manifold {
	private Body a, b;
	double penetrationDepth;
	Vector normal;
	Vector[] contacts = new Vector[2];
	int contactCount;
	private double restitution;
	private double dynamicFriction;
	private double staticFriction;
	
	public Manifold(Body a, Body b){
		this.a = a;
		this.b = b;
	}
	
	public void solve(){
		normal = new Vector();
		if(a.shape instanceof AABB){
			if(b.shape instanceof AABB){
				Collision.AABBtoAABB(this, a, b);
			}
			if(b.shape instanceof Ramp){
				Collision.PolygonToPolygon(this, a, b);
			}
		}
		if(a.shape instanceof Ramp){
			if(b.shape instanceof Ramp){
				Collision.PolygonToPolygon(this, a, b);
			}
			if(b.shape instanceof AABB){
				Collision.PolygonToPolygon(this, a, b);
			}
		}
	}
	
	public void init(){
		restitution = Math.min(a.restitution, b.restitution);
		staticFriction = Math.sqrt(a.staticFriction*b.staticFriction);
		dynamicFriction = Math.sqrt(a.dynamicFriction*b.dynamicFriction);
		
		for(int i = 0; i < contactCount; i++){
			Vector ra = contacts[i].subtract(a.getPosition());
			Vector rb = contacts[i].subtract(b.getPosition());
			
			Vector rv = b.getVelocity().add(rb.PreCross(b.getAngularVelocity())).subtract(
						a.getVelocity().subtract(ra.PreCross(a.getAngularVelocity())));
			if(rv.lengthSquared() < 0.001){
				restitution = 0;
			}
		}
	}
	
	public void applyImpulse(){
		if(a.getInvMass() == 0 && b.getInvMass() == 0){
			infiniteMassCorrection();
			return;
		}
		for(int i = 0; i < contactCount; i++){
			Vector ra = contacts[i].subtract(a.getPosition());
			Vector rb = contacts[i].subtract(b.getPosition());
			
			Vector rv = b.getVelocity().add(rb.PreCross(b.getAngularVelocity())).subtract(
						a.getVelocity().subtract(ra.PreCross(a.getAngularVelocity())));
			double contactVel = rv.dot(normal);
			if(contactVel > 0) return;
			double raCrossN = ra.Cross(normal);
			double rbCrossN = rb.Cross(normal);
			double invMassSum = a.getInvMass() + b.getInvMass() + Math.pow(raCrossN, 2)*a.getInvInertia() + Math.pow(rbCrossN, 2)*b.getInvInertia();
			
			double j = -(1+restitution)*contactVel;
			j /= invMassSum*contactCount;
			Vector impulse = normal.multiply(j);
			a.applyImpulse(impulse.multiply(-1), ra);
			a.applyImpulse(impulse, rb);
			
			rv = b.getVelocity().add(rb.PreCross(b.getAngularVelocity())).subtract(
						a.getVelocity().subtract(ra.PreCross(a.getAngularVelocity())));
			Vector t = rv.subtract(normal.multiply(rv.dot(normal)));
			t = t.normalize();
			double jt = -rv.dot(t);
			jt /= invMassSum*contactCount;
			if(Math.abs(jt) <= 0.005) return;
			Vector tanImpulse;
			if(Math.abs(jt) < j*staticFriction){
				tanImpulse = t.multiply(jt);
			}
			else{
				tanImpulse = t.multiply(-j*dynamicFriction);
			}
			a.applyImpulse(tanImpulse.multiply(-1), ra);
			b.applyImpulse(tanImpulse, rb);
		}
	}
	
	public void applyPositionalCorrection(){
		double slop = 0.05;
		double percent = 0.4;
		Vector correction = normal.multiply(percent*Math.max(penetrationDepth - slop,  0)/(a.getInvMass()+b.getInvMass()));
		a.addPosition(correction.multiply(-1).multiply(a.getInvMass()));
		a.addPosition(correction.multiply(b.getInvMass()));
	}
	
	public void infiniteMassCorrection(){
		a.setVelocity(new Vector(0, 0));
		b.setVelocity(new Vector(0, 0));
	}	
}

