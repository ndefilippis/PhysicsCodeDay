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
		if(a.shape instanceof Polygon){
			if(b.shape instanceof Polygon){
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
			
			double j = -(1.0+restitution)*contactVel;
			j /= invMassSum*contactCount;
			Vector impulse = normal.multiply(j);
			a.applyImpulse(impulse.neg(), ra);
			b.applyImpulse(impulse, rb);
			
			rv = b.getVelocity().add(rb.PreCross(b.getAngularVelocity())).subtract(
						a.getVelocity().subtract(ra.PreCross(a.getAngularVelocity())));
			Vector t = new Vector(rv);
			t.addsi(normal, -rv.dot(normal));
			t.normalizei();
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
			a.applyImpulse(tanImpulse.neg(), ra);
			b.applyImpulse(tanImpulse, rb);
		}
	}
	
	public void applyPositionalCorrection(){
		double slop = 0.1;
		double percent = 0.2;
		double correction = percent*Math.max(penetrationDepth - slop,  0)/(a.getInvMass()+b.getInvMass());
		a.setPosition(a.getPosition().addsi(normal, -correction*a.getInvMass()));
		b.setPosition(b.getPosition().addsi(normal,  correction*b.getInvMass()));
	}
	
	public void infiniteMassCorrection(){
		a.setVelocity(new Vector(0, 0));
		b.setVelocity(new Vector(0, 0));
	}	
}

