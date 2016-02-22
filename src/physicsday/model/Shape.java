package physicsday.model;

import physicsday.util.BoundingBox;
import physicsday.util.Mat22;

public abstract class Shape{
	protected double orientation;
	protected Mat22 u = new Mat22();
	public Body body;

	public Shape(){
	}
	
	public abstract Shape copy();
	public abstract BoundingBox boundingBox();

	public void setOrientation(double orientation) {
		this.orientation = orientation;
		u = new Mat22(orientation);
	}
}
