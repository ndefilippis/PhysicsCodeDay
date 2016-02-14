import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;

public abstract class Shape{
	Vector velocity;
	Vector position;
	Vector acceleration;
	Vector prevPosition;
	double height;
	double width;
	double mass;
	boolean anchored;
	
	public Shape(double x, double y, double width, double height){
		velocity = new Vector();
		acceleration = World.gravity;
		position = new Vector(x, y);
		mass = 1;
		this.height = height;
		this.width = width;
	}
	
	public abstract void draw(Graphics g);
	
	public void update(double dt){
		prevPosition = position;
		if(anchored) return;
		Vector prevVelocity = velocity;
		velocity = velocity.add(acceleration.multiply(dt));	
		if(prevVelocity.lengthSquared() > velocity.lengthSquared() && Math.abs(velocity.x) <= 0.05){
			velocity.x = 0;
		}
		if(prevVelocity.lengthSquared() > velocity.lengthSquared() && Math.abs(velocity.y) <= 0.05){
			velocity.y = 0;
		}
		position = position.add(velocity.multiply(dt));
	}

	public abstract Polygon getOutline();
	
	public abstract double friction(Shape s);
	
	public abstract Area getArea();
	
	public int drawX(){
		return (int)(position.x*World.xScale);
	}
	
	public int drawY(){
		return (int)(position.y*World.yScale);
	}
	
	public int drawWidth(){
		return (int)(width*World.xScale);
	}
	
	public int drawHeight(){
		return (int)(height*World.yScale);
	}

	public static void collide(Shape s1, Shape s2) {
		s1.position = s1.prevPosition;
		s2.position = s2.prevPosition;
		if	(s1 instanceof Wall){
			if (s2.drawY() + s2.drawHeight() - s1.drawY() == 0){
				s2.velocity.y *= -Math.pow(World.energyConserved,1.0/2);
			}
			else{
				s2.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
		}
		if	(s2 instanceof Wall){
			if (s1.drawY() + s1.drawHeight() - s2.drawY() == 0){
				s1.velocity.y *= -Math.pow(World.energyConserved,1.0/2);
			}
			else{
				s1.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
		}
		if	(s1 instanceof Ramp){
			if (s1.drawX() + s1.drawWidth() - s2.drawX() == 0 || s1.drawX() - s2.drawX() - s2.drawWidth() == 0){
				s2.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
			else {
				Vector perpendicular;
				Vector projection;
				if (((Ramp)s1).positive) {
					perpendicular = new Vector(-s1.drawHeight(),s1.drawWidth()).normalize();
				}
				else {
					perpendicular = new Vector(s1.drawHeight(),s1.drawWidth()).normalize();
				}
				projection = s2.velocity.project(perpendicular).multiply(Math.pow(World.energyConserved,1.0/2));
				s2.velocity = s2.velocity.add(perpendicular).add(projection);
			}
			
		}
		if	(s2 instanceof Ramp){
			if (s2.drawX() + s2.drawWidth() - s1.drawX() == 0 || s2.drawX() - s1.drawX() - s1.drawWidth() == 0){
				s1.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
			else {
				Vector perpendicular;
				Vector projection;
				Vector parallel;
				if (((Ramp)s2).positive) {
					perpendicular = new Vector(-s2.drawHeight(),s2.drawWidth()).normalize();
				}
				else {
					perpendicular = new Vector(s2.drawHeight(),s2.drawWidth()).normalize();
				}
				projection = s1.velocity.project(perpendicular);
				s1.velocity = s1.velocity.subtract(projection).subtract(projection);
			}
		}
	}
	
	public String toString(){
		return ""+this.getClass().toString().substring(6)+": "+"position: "+position+"velocity: "+velocity;
	}
}
