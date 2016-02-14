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
		acceleration = World.gravity;
		prevPosition = position;
		if(anchored) return;
		Vector prevVelocity = velocity;
		int count = 5;
		while(World.isColliding(this) && count < 10){
			position = position.subtract(prevVelocity);
			count++;
		}
		if(!World.isColliding(this))
			velocity = velocity.add(acceleration.multiply(dt));	
		if(Math.abs(velocity.x) <= 0.001){
			velocity.x = 0;
		}
		if(Math.abs(velocity.y) <= 0.001){
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
			if(Math.abs(s2.velocity.x) <= 0.005) s2.velocity.x = 0;
			if(Math.abs(s2.velocity.y) <= 0.005) s2.velocity.y = 0;
		}
		if	(s2 instanceof Wall){
			if (s1.drawY() + s1.drawHeight() - s2.drawY() == 0){
				s1.velocity.y *= -Math.pow(World.energyConserved,1.0/2);
			}
			else{
				s1.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
			if(Math.abs(s1.velocity.x) <= 0.005) s1.velocity.x = 0;
			if(Math.abs(s1.velocity.y) <= 0.005) s1.velocity.y = 0;
		}
		if	(s1 instanceof Ramp){
			if (s1.drawX() + s1.drawWidth() - s2.drawX() == 0 || s1.drawX() - s2.drawX() - s2.drawWidth() == 0){
				s2.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
			else {
				Vector perpendicular;
				Vector projection;
				@SuppressWarnings("unused")
				Vector parallel;
				if (((Ramp)s1).positive) {
					perpendicular = new Vector(-s1.drawHeight(),s1.drawWidth()).normalize().multiply(s2.velocity.length());
				}
				else {
					perpendicular = new Vector(s1.drawHeight(),s1.drawWidth()).normalize().multiply(s2.velocity.length());
				}
				projection = s2.velocity.project(perpendicular);
				s2.velocity = s2.velocity.add(projection);
			} 
		}
		if	(s2 instanceof Ramp){
			if (s2.drawX() + s2.drawWidth() - s1.drawX() == 0 || s2.drawX() - s1.drawX() - s1.drawWidth() == 0){
				s1.velocity.x *= -Math.pow(World.energyConserved,1.0/2);
			}
			else {
				Vector perpendicular;
				Vector projection;
				@SuppressWarnings("unused")
				Vector parallel;
				if (((Ramp)s2).positive) {
					perpendicular = new Vector(-s2.drawHeight(),s2.drawWidth()).normalize().multiply(s2.velocity.length());
				}
				else {
					perpendicular = new Vector(s2.drawHeight(),s2.drawWidth()).normalize().multiply(s2.velocity.length());
				}
				projection = s1.velocity.project(perpendicular).multiply(-Math.pow(World.energyConserved,1.0/2));
				s1.velocity = s1.velocity.add(perpendicular).subtract(projection);
			}
		}
	}
	
	public String toString(){
		return ""+this.getClass().toString().substring(6)+": "+"position: "+position+"velocity: "+velocity;
	}
}
