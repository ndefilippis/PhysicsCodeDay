
public class Shape {
	Vector velocity;
	Vector position;
	Vector acceleration;
	
	public Shape(double x, double y){
		position = new Vector(x, y);
	}
	
	public void update(double dt){
		velocity.add(acceleration.multiply(dt));
	}
}
