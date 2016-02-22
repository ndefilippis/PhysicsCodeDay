package physicsday.model;

import java.awt.Graphics;
import java.awt.geom.Area;

import physicsday.util.BoundingBox;
import physicsday.view.PhysicsPanel;

public class Circle extends Shape{
	double radius;
	
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
