package physicsday.model;

import javax.swing.JTextField;

import physicsday.util.Vector;

public class Body {
	public final Shape shape;
	private State currState, prevState;
	private double mass, invMass, inertia, invInertia;
	public double staticFriction;
	public double dynamicFriction;
	public double restitution;
	
	public Body(Shape shape, double x, double y){
		this.shape = shape;
		currState = new State();
		currState.position.set(x, y);
		staticFriction = 0.3;
		dynamicFriction = 0.2;
		restitution = 0.3;
		mass = 1;
		invMass = 1;
		shape.body = this;
		shape.init();
	}
	
	public Body(Body other) {
		this.shape = other.shape.copy();
		setMass(other.mass);
		this.currState = new State(other.currState);
	}

	public Vector getPosition(){
		return currState.position;
	}
	public void setPosition(Vector p){
		this.currState.position.set(p);;
		
	}
	public Vector getVelocity(){
		return currState.velocity;
	}
	public void setVelocity(Vector vector) {
		currState.velocity.set(vector);
	}
	public double getAngularVelocity(){
		return currState.angularVelocity;
	}
	
	
	public void applyForce(Vector f){
		currState.force.addi(f);
	}

	public void applyImpulse(Vector impulse, Vector contact){
		currState.velocity.addsi(impulse, invMass);
		currState.angularVelocity += contact.Cross(impulse)*invInertia;
	}
	
	public void integrateForces(double dt, Vector gravity){
		currState.integrateForces(dt, gravity);
	}
	public void integrateVelocity(double dt, Vector gravity){
		currState.integrateVelocity(dt, gravity);
		
	}
	
	private class State{
		public final Vector position;
		public double orientation;
		
		public final Vector velocity;
		public double angularVelocity;
		
		public final Vector force;
		public double torque;
		
		public State(){
			velocity = new Vector();
			position = new Vector();
			force = new Vector();
		}
		
		public State(State other){
			velocity = new Vector(other.velocity);
			position = new Vector(other.position);
			force = new Vector(other.force);
		}
		
		public void integrateForces(double dt, Vector gravity){
			if(invMass == 0) return;
			velocity.addsi(force, dt/2*invMass);
			velocity.addsi(gravity, dt/2.0);
			angularVelocity += torque*invInertia*dt/2.0;
		}
		
		public void integrateVelocity(double dt, Vector gravity){
			if(invMass == 0) return;
			position.addsi(velocity, dt);
			orientation += angularVelocity * dt;
			shape.setOrientation(orientation);
			integrateForces(dt, gravity);
		}
	}

	public String toString(){
		return ""+shape.getClass().toString().substring(6)+":    mass: "+mass+"    "+"position: "+currState.position+"   velocity: "+currState.velocity;
	}
	public void setMass(double mass) {
		this.mass = mass;
		this.invMass = 1/mass;
	}
	
	public void setInertia(double inertia) {
		this.inertia = inertia;
		this.invInertia = 1/inertia;
		
	}
	
	public double getInvMass() {
		return invMass;
	}

	public Body copy() {
		return new Body(this);
	}

	public double getInvInertia() {
		return invInertia;
	}

	public void setTorque(double t) {
		currState.torque = t;
	}
	
	public void setForce(double x, double y){
		currState.force.set(x, y);
	}

	public void setVelocity(double x, double y) {
		currState.velocity.set(x, y);
	}

	public double getOrientation() {
		return currState.orientation;
	}

	public void setAngularVelocity(double val) {
		currState.angularVelocity = val;
	}

	public void setOrientation(double val) {
		currState.orientation = val;
	}
}
