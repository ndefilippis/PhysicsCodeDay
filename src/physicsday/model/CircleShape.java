package physicsday.model;

import physicsday.util.BoundingBox;
import physicsday.util.Vector;

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

	@Override
	public void resize(Vector start, Vector end) {
		Vector rd = end.subtract(body.getPosition());
		radius = Math.min(Math.abs(rd.x), Math.abs(rd.y))+1;
		init();
	}

	@Override
	public String saveString() {
		return "c:"+radius;
	}
}
