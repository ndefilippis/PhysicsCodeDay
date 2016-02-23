package physicsday.model;

import java.util.ArrayList;
import physicsday.PhysicsDay;
import physicsday.util.Vector;

public class World {
	private ArrayList<Body> objects = new ArrayList<Body>();
	private ArrayList<Body> initState;
	private ArrayList<Manifold> contacts = new ArrayList<Manifold>();
	public Vector gravity = new Vector(0, 9.81);

	public World() {
		add(new Block(1, -2, 4, 4));
		add(new Wall(50, 24, 100, 10));
	}

	public Body add(Body b) {
		assert (b != null);
		objects.add(b);
		return b;
	}

	public void update(double dt) {
		contacts.clear();
		for (int i = 0; i < objects.size(); i++) {
			Body a = objects.get(i);
			for (int j = i + 1; j < objects.size(); j++) {
				Body b = objects.get(j);
				if (b.getInvMass() == 0 && a.getInvMass() == 0)
					continue;
				Manifold m = new Manifold(a, b);
				m.solve();
				if (m.contactCount > 0) {
					contacts.add(m);
				}
			}
		}
		
		for(int i = 0; i < objects.size(); i++){
			objects.get(i).integrateForces(dt, gravity);
		}
		for(int i = 0; i < contacts.size(); i++){
			contacts.get(i).init();
		}
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < contacts.size(); j++){
				contacts.get(j).applyImpulse();
			}
		}

		for(int i = 0; i < objects.size(); i++){
			objects.get(i).integrateVelocity(dt, gravity);
		}
		for(int i = 0; i < contacts.size(); i++){
			contacts.get(i).applyPositionalCorrection();
		}
		for(int i = 0; i < objects.size(); i++){
			objects.get(i).setForce(0, 0);
			objects.get(i).setTorque(0);
		}	
	}

	

	public void resetState() {
		objects = new ArrayList<Body>();
		for (Body b : initState) {
			objects.add(b.copy());
		}
	}

	

	public void removeObject(Body body) {
		objects.remove(body);
	}

	public void clear() {
		objects.clear();
		contacts.clear();
	}

	public ArrayList<Body> getBodies() {
		return objects;
	}
}
