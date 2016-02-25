package physicsday.model;

import physicsday.util.Vector;

public class Manifold {
	private Body a;
	private Body b;
	double penetrationDepth;
	Vector normal;
	Vector[] contacts;
	int contactCount;
	private double restitution;
	private double dynamicFriction;
	private double staticFriction;

	public Manifold(Body a, Body b) {
		this.a = a;
		this.b = b;
		contacts = new Vector[2];
		contacts[0] = new Vector();
		contacts[1] = new Vector();
	}

	public void solve() {
		normal = new Vector();
		if (a.shape instanceof PolygonShape) {
			if (b.shape instanceof PolygonShape) {
				Collision.PolygonToPolygon(this, a, b);
			}
			if(b.shape instanceof CircleShape){
				Collision.PolygonToCircle(this, a, b);
			}
		}
		if(a.shape instanceof CircleShape){
			if(b.shape instanceof CircleShape){
				Collision.CircleToCircle(this, a, b);
			}
			if(b.shape instanceof PolygonShape){
				Collision.CircleToPolygon(this, a, b);
			}
		}
	}

	public void init() {
		restitution = Math.min(a.restitution, b.restitution);
		staticFriction = Math.sqrt(a.staticFriction * b.staticFriction);
		dynamicFriction = Math.sqrt(a.dynamicFriction * b.dynamicFriction);

		for (int i = 0; i < contactCount; i++) {
			Vector ra = contacts[i].subtract(a.getPosition());
			Vector rb = contacts[i].subtract(b.getPosition());

			Vector rv = b.getVelocity().add(rb.PreCross(b.getAngularVelocity()))
					.subtract(a.getVelocity().subtract(ra.PreCross(a.getAngularVelocity())));
			if (rv.lengthSquared() < 0.001) {
				restitution = 0;
			}
		}
	}

	public void applyImpulse() {
		if (Math.abs(a.getInvMass() + b.getInvMass()) == 0.00001) {
			System.out.println("HEY");
			infiniteMassCorrection();
			return;
		}

		for (int i = 0; i < contactCount; ++i) {
			Vector ra = contacts[i].subtract(a.getPosition());
			Vector rb = contacts[i].subtract(b.getPosition());

			Vector rv = b.getVelocity().add(rb.PreCross(b.getAngularVelocity()).subtracti(a.getVelocity())
					.subtracti((ra.PreCross(a.getAngularVelocity()))));

			double contactVel = rv.dot(normal);

			if (contactVel > 0) {
				return;
			}

			double raCrossN = ra.Cross(normal);
			double rbCrossN = rb.Cross(normal);
			double invMassSum = a.getInvMass() + b.getInvMass() + (raCrossN * raCrossN) * a.getInvInertia()
					+ (rbCrossN * rbCrossN) * b.getInvInertia();

			double j = -(1.0f + restitution) * contactVel;
			j /= invMassSum;
			j /= contactCount;

			Vector impulse = normal.multiply(j);
			a.applyImpulse(impulse.neg(), ra);
			b.applyImpulse(impulse, rb);
			
			rv = b.getVelocity().add(rb.PreCross(b.getAngularVelocity()).subtracti(a.getVelocity())
					.subtracti((ra.PreCross(a.getAngularVelocity()))));

			Vector t = new Vector(rv);
			t.addsi(normal, -rv.dot(normal));
			t.normalizei();

			double jt = -rv.dot(t);
			jt /= invMassSum;
			jt /= contactCount;

			if (jt == 0) {
				return;
			}
			Vector tangentImpulse;
			if (StrictMath.abs(jt) < j * staticFriction) {
				tangentImpulse = t.multiply(jt);
			} else {
				tangentImpulse = t.multiply(j).multiplyi(-dynamicFriction);
			}
			a.applyImpulse(tangentImpulse.neg(), ra);
			b.applyImpulse(tangentImpulse, rb);
		}
	}

	public void applyPositionalCorrection() {
		double correction = Math.max( penetrationDepth - 0.01, 0.0 ) / (a.getInvMass() + b.getInvMass()) * 0.4;
		a.setPosition(a.getPosition().addsi( normal, -a.getInvMass() * correction ));
		b.setPosition(b.getPosition().addsi( normal, b.getInvMass() * correction ));
	}

	public void infiniteMassCorrection() {
		a.setVelocity(new Vector(0, 0));
		b.setVelocity(new Vector(0, 0));
	}
}
