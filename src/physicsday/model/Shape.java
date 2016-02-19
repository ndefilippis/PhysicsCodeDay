package physicsday.model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;

import physicsday.util.Vector;
import physicsday.view.PhysicsPanel;

public abstract class Shape{
	protected State currState;
	protected State prevState;
	private double height;
	private double width;
	private double mass;
	protected boolean anchored;
	protected double inv_mass;
	protected Color background;
	protected Color outline;
	protected double static_friction = 0.3;
	protected double dynamic_friction = 0.1;
	
	protected class State{
		public Vector momentum;
		public Vector force;
		public Vector velocity;
		protected Vector acceleration;
		public Vector position;
		
		public State(){
			momentum = new Vector();
			velocity = new Vector();
			position = new Vector();
			acceleration = World.gravity;
			force = new Vector();
		}
		public State(Vector position){
			momentum = new Vector();
			velocity = new Vector();
			this.position = position;
			acceleration = World.gravity;
			force = new Vector();
		}
		public State(Vector position, Vector velocity){
			this.velocity = velocity;
			momentum = velocity.multiply(inv_mass);
			this.position = position;
			acceleration = World.gravity;
			force = new Vector();
		}
		public State(State other){
			momentum = new Vector(other.momentum);
			velocity = new Vector(other.velocity);
			position = new Vector(other.position);
			acceleration = new Vector(other.acceleration);
			force = new Vector(other.force);
		}
	}
	
	public Shape(double x, double y, double width, double height){
		currState = new State(new Vector(x, y));
		setMass(1);
		inv_mass = 1;
		this.setHeight(height);
		this.setWidth(width);
	}
	
	public abstract void draw(Graphics g);
	
	public void update(double dt){
		currState.acceleration = World.gravity;
		if(!Vector.isValid(currState.velocity)){
			currState.velocity = prevState.velocity;
		}
		if(!Vector.isValid(currState.position)){
			currState.position = prevState.position;
		}
		prevState = new State(currState);
		if(!anchored){
			currState.velocity = currState.velocity.add(currState.acceleration.multiply(dt));	
			if(Math.abs(currState.velocity.x) <= 0.001){
				currState.velocity.x = 0;
			}
			if(Math.abs(currState.velocity.y) <= 0.001){
				currState.velocity.y = 0;
			}
			currState.position = currState.position.add(currState.velocity.multiply(dt));
		}
	}

	public abstract Polygon getOutline();
	
	public abstract double friction(Shape s);
	
	public abstract Area getArea();
	
	public int drawX(){
		return (int)(currState.position.x*PhysicsPanel.xScale+PhysicsPanel.xOffset);
	}
	
	public int drawY(){
		return (int)(currState.position.y*PhysicsPanel.yScale+PhysicsPanel.yOffset);
	}
	
	public int drawWidth(){
		return (int)(getWidth()*PhysicsPanel.xScale);
	}
	
	public int drawHeight(){
		return (int)(getHeight()*PhysicsPanel.yScale);
	}

	public static void collide(Shape s1, Shape s2) {
		if	(s1 instanceof Ramp){
			if (s1.drawX() + s1.drawWidth() - s2.drawX() == 0 || s1.drawX() - s2.drawX() - s2.drawWidth() == 0){
				s2.currState.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
			else {
				Vector perpendicular;
				@SuppressWarnings("unused")
				Vector parallel;
				if (((Ramp)s1).positive) {
					perpendicular = new Vector(-s1.drawHeight(),s1.drawWidth()).normalize();
				}
				else {
					perpendicular = new Vector(s1.drawHeight(),s1.drawWidth()).normalize();
				}
				s2.currState.velocity = s2.currState.velocity.multiply(s2.currState.velocity.normalize().dot(perpendicular));
				s2.currState.velocity = s2.currState.velocity.rotate(perpendicular.theta());
			} 
		}
		if	(s2 instanceof Ramp){
			if (s2.drawX() + s2.drawWidth() - s1.drawX() == 0 || s2.drawX() - s1.drawX() - s1.drawWidth() == 0){
				s1.currState.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
			else {
				Vector perpendicular;
				@SuppressWarnings("unused")
				Vector parallel;
				if (((Ramp)s2).positive) {
					perpendicular = new Vector(-s2.drawHeight(),s2.drawWidth()).normalize();
				}
				else {
					perpendicular = new Vector(s2.drawHeight(),s2.drawWidth()).normalize();
				}
				s1.currState.velocity = s1.currState.velocity.multiply(s1.currState.velocity.normalize().dot(perpendicular));
				s1.currState.velocity = s1.currState.velocity.rotate(perpendicular.theta()).multiply(Math.pow(World.energyConserved,1.0/2));
			}
		}
	}
	
	public static void collide(Collision c){
		Vector relativeVelocity = c.b.currState.velocity.subtract(c.a.currState.velocity);
		double velocityAlongNormal = relativeVelocity.dot(c.normal);
		if(velocityAlongNormal > 0){
			return;
		}
		double e = Math.sqrt(World.energyConserved);
		double j = -(1+e)*velocityAlongNormal;
		j /= c.a.inv_mass + c.b.inv_mass;
		Vector impulse = c.normal.multiply(j);
		c.a.currState.velocity = c.a.currState.velocity.subtract(impulse.multiply(c.a.inv_mass));
		c.b.currState.velocity = c.b.currState.velocity.add(impulse.multiply(c.b.inv_mass));
		double percent = 0.8;
		double slop = 0.1;
		Vector correction = c.normal.multiply(percent*(Math.max(c.penetration_depth-slop, 0)/(c.a.inv_mass+c.b.inv_mass)));
		c.a.currState.position = c.a.currState.position.subtract(correction.multiply(c.a.inv_mass));
		c.b.currState.position = c.b.currState.position.add(correction.multiply(c.b.inv_mass));
		
		relativeVelocity = c.b.currState.velocity.subtract(c.a.currState.velocity);
		Vector tangent = relativeVelocity.subtract(c.normal.multiply(relativeVelocity.dot(c.normal)));
		tangent = tangent.normalize();
		double jt = -relativeVelocity.dot(tangent);
		jt /=(c.a.inv_mass+c.b.inv_mass);
		double mu = Math.sqrt(c.a.static_friction*c.a.static_friction + c.b.static_friction*c.b.static_friction);
		Vector frictionImpulse;
		if(Math.abs(jt) < j * mu){
			frictionImpulse = tangent.multiply(jt);
		}
		else{
			double dynamicFriction =  Math.sqrt(c.a.dynamic_friction*c.a.dynamic_friction + c.b.dynamic_friction*c.b.dynamic_friction);
			frictionImpulse = tangent.multiply(-j*dynamicFriction);
		}
		c.a.currState.velocity = c.a.currState.velocity.subtract(frictionImpulse.multiply(c.a.inv_mass));
		c.b.currState.velocity = c.b.currState.velocity.add(frictionImpulse.multiply(c.b.inv_mass));
	}
	
	public String toString(){
		return ""+this.getClass().toString().substring(6)+":    mass: "+getMass()+"    "+"position: "+currState.position+"   velocity: "+currState.velocity;
	}
	public abstract Shape copy();

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
	public void setVelocity(Vector v){
		currState.velocity = v;
	}
	public Vector getVelocity(){
		return currState.velocity;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
		this.inv_mass = 1/mass;
	}

	public void setPosition(Vector vector) {
		currState.position = vector;
	}

	public Vector getPosition() {
		return currState.position;
	}
}
