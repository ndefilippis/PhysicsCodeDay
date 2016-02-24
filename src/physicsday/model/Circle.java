package physicsday.model;

import physicsday.util.BoundingBox;

public class Circle extends Shape{
	public double radius;
	
	public Circle(double radius){
		this.radius = radius;
	}

	@Override
	public Shape copy() {
		return new Circle(radius);
	}

	@Override
	public BoundingBox boundingBox() {
		return new BoundingBox(body.getPosition(), radius, radius);
	}
}
