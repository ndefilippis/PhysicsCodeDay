import java.awt.Graphics;
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
		velocity = velocity.add(acceleration.multiply(dt));	
		if(Math.abs(velocity.x) <= 0.01){
			velocity.x = 0;
		}
		if(Math.abs(velocity.y) <= 0.01){
			velocity.y = 0;
		}
		position = position.add(velocity.multiply(dt));
	}
	
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
		s1.velocity.y *= -Math.pow(World.energyConserved,1.0/2);
		s2.velocity.y *= -Math.pow(World.energyConserved,1.0/2);
		s1.velocity.x *= Math.pow(World.energyConserved,1.0/2);
		s2.velocity.x *= Math.pow(World.energyConserved,1.0/2);
	}
}
