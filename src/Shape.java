import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;

public abstract class Shape{
	protected Vector velocity;
	protected Vector position;
	protected Vector acceleration;
	protected Vector prevPosition;
	protected double height;
	protected double width;
	protected double mass;
	protected double inv_mass;
	protected boolean anchored;
	protected Color background;
	protected Color outline;
	
	protected class State{
		public Vector momentum;
		public Vector force;
		public Vector velocity;
		public Vector position;
		
		public State(){
			momentum = new Vector();
			velocity = new Vector();
			acceleration = World.gravity;
			position = new Vector();
			force = new Vector();
		}
	}
	
	public Shape(double x, double y, double width, double height){
		velocity = new Vector();
		acceleration = World.gravity;
		position = new Vector(x, y);
		mass = 1;
		inv_mass = 1;
		this.height = height;
		this.width = width;
	}
	
	public abstract void draw(Graphics g);
	
	public void update(double dt){
		acceleration = World.gravity;
		if(anchored){
			prevPosition = position;
			return;
		}
		Vector prevVelocity = velocity;
		velocity = velocity.add(acceleration.multiply(dt));	
		if(Math.abs(velocity.x) <= 0.001){
			velocity.x = 0;
		}
		if(Math.abs(velocity.y) <= 0.001){
			velocity.y = 0;
		}
		prevPosition = position;
		if(velocity.x == Double.NaN) velocity.x = prevVelocity.x;
		if(velocity.y == Double.NaN) velocity.y = prevVelocity.y;
		position = position.add(velocity.multiply(dt));
	}

	public abstract Polygon getOutline();
	
	public abstract double friction(Shape s);
	
	public abstract Area getArea();
	
	public int drawX(){
		return (int)(position.x*World.xScale+World.xOffset);
	}
	
	public int drawY(){
		return (int)(position.y*World.yScale+World.yOffset);
	}
	
	public int drawWidth(){
		return (int)(width*World.xScale);
	}
	
	public int drawHeight(){
		return (int)(height*World.yScale);
	}

	public static void collide(Shape s1, Shape s2) {
		if	(s1 instanceof Ramp){
			if (s1.drawX() + s1.drawWidth() - s2.drawX() == 0 || s1.drawX() - s2.drawX() - s2.drawWidth() == 0){
				s2.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
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
				s2.velocity = s2.velocity.multiply(s2.velocity.normalize().dot(perpendicular));
				s2.velocity = s2.velocity.rotate(perpendicular.theta());
			} 
		}
		if	(s2 instanceof Ramp){
			if (s2.drawX() + s2.drawWidth() - s1.drawX() == 0 || s2.drawX() - s1.drawX() - s1.drawWidth() == 0){
				s1.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
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
				s1.velocity = s1.velocity.multiply(s1.velocity.normalize().dot(perpendicular));
				s1.velocity = s1.velocity.rotate(perpendicular.theta()).multiply(Math.pow(World.energyConserved,1.0/2));
			}
		}
	}
	
	public static void collide(Collision c){
		Vector relativeVelocity = c.b.velocity.subtract(c.a.velocity);
		double velocityAlongNormal = relativeVelocity.dot(c.normal);
		if(velocityAlongNormal > 0){
			return;
		}
		double e = Math.sqrt(World.energyConserved);
		double j = -(1+e)*velocityAlongNormal;
		j /= c.a.inv_mass + c.b.inv_mass;
		
		Vector impulse = c.normal.multiply(j);
		c.a.velocity = c.a.velocity.subtract(impulse.multiply(c.a.inv_mass));
		c.b.velocity = c.b.velocity.add(impulse.multiply(c.b.inv_mass));
	}
	
	public String toString(){
		return ""+this.getClass().toString().substring(6)+":    mass: "+mass+"    "+"position: "+position+"   velocity: "+velocity;
	}

	public abstract Shape copy();
}
