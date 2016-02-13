import java.awt.Graphics;
import java.awt.geom.Area;

public abstract class Shape{
	Vector velocity;
	Vector position;
	Vector acceleration;
	double height;
	double width;
	//Shape shape;
	
	public Shape(double x, double y, double width, double height){
		velocity = new Vector();
		acceleration = new Vector();
		position = new Vector(x, y);
		this.height = height;
		this.width = width;
	}
	
	public abstract void draw(Graphics g);
	
	public void update(double dt){
		velocity.add(acceleration.multiply(dt));
		position.add(velocity.multiply(dt));
	}
	
	public abstract double friction(Shape s);
	
	public abstract Area getArea();
	
	public abstract void collide(Shape s);
}
