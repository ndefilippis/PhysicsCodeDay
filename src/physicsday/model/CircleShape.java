package physicsday.model;

import physicsday.util.BoundingBox;

public class CircleShape extends Shape{
	public double radius;
	
	public CircleShape(double radius){
		this.radius = radius;
	}

	@Override
	public Shape copy() {
		return new CircleShape(radius);
	}
	
	public void init(){
		double mass = Math.PI * radius * radius;
		body.setMass( mass );
		body.setInertia( radius * radius * mass );
	}

	@Override
	public BoundingBox boundingBox() {
		return new BoundingBox(body.getPosition(), radius*2, radius*2);
	}
}
