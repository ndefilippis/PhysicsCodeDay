package physicsday.model;

import java.awt.Graphics;
import physicsday.util.Vector;
import physicsday.view.PhysicsPanel;

public class Body {
	public Shape shape;
	private State currState;
	private State prevState;
	private double mass;
	private double invMass;
	private double inertia;
	private double invInertia;
	double staticFriction = 0.3;
	double dynamicFriction = 0.1;
	double restitution;
	
	public Body(Shape shape, double x, double y){
		this.shape = shape;
		shape.body = this;
		mass = 1;
		invMass = 1;
		currState = new State();
		currState.position = new Vector(x, y);
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
		this.currState.position = p;
		
	}
	
	public Vector getVelocity(){
		return currState.velocity;
	}
	public double getAngularVelocity(){
		return currState.angularVelocity;
	}
	
	public void draw(Graphics g, PhysicsPanel panel){
		shape.draw(g, panel);
	}
	
	public void update(double dt){
		if(!Vector.isValid(currState.velocity)){
			currState.velocity = prevState.velocity;
		}
		if(!Vector.isValid(currState.position)){
			currState.position = prevState.position;
		}
		prevState = new State(currState);
		currState.integrate(dt);
		shape.setOrientation(currState.orientation);
		currState.force = new Vector(0,0);
		currState.torque = 0;
	}
	
	public void applyForce(Vector f){
		currState.force = currState.force.add(f);
	}
	public void applyTorque(double t){
		currState.torque += t;
	}
	public void applyImpulse(Vector impulse, Vector contact){
		currState.velocity = currState.velocity.add(impulse.multiply(invMass));
		currState.angularVelocity += contact.Cross(impulse)*invInertia;
	}
	
	private class State{
		public Vector momentum;
		public Vector position;
		public double orientation;
		public double angularMomentum;
		
		public Vector velocity;
		public double angularVelocity;
		
		public Vector force;
		public double torque;
		
		public State(){
			momentum = new Vector();
			velocity = new Vector();
			position = new Vector();
			force = new Vector();
		}
		
		public State(State other){
			momentum = new Vector(other.momentum);
			velocity = new Vector(other.velocity);
			position = new Vector(other.position);
			force = new Vector(other.force);
			recalculate();
		}

		public void integrate(double dt) {
			System.out.println(force);
			momentum = momentum.add(force.multiply(dt));
			angularMomentum += torque*dt;
			recalculate();
			position = position.add(velocity.multiply(dt));
			orientation += angularVelocity * dt;
		}

		private void recalculate(){
			velocity = momentum.multiply(invMass);
			angularVelocity = angularMomentum*invInertia;
			if(velocity.x <= 0.005) velocity.x = 0;
			if(velocity.y <= 0.005) velocity.y = 0;
			if(angularVelocity <= 0.005) angularVelocity = 0;
		}
	}

	public String toString(){
		return ""+shape.getClass().toString().substring(6)+":    mass: "+mass+"    "+"position: "+currState.position+"   velocity: "+currState.velocity;
	}
	public void setMass(double mass) {
		this.mass = mass;
		this.invMass = 1/mass;
	}
	
	public double getInvMass() {
		return invMass;
	}

	public String saveString() {
		return shape.toString();
	}

	public Body copy() {
		return new Body(this);
	}

	public void setVelocity(Vector vector) {
		currState.velocity = vector;
	}

	public double getInvInertia() {
		return invInertia;
	}

	public void addPosition(Vector vec) {
		currState.position = currState.position.add(vec);
	}
}
