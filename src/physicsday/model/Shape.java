package physicsday.model;

import physicsday.util.BoundingBox;
import physicsday.util.Mat22;
import physicsday.util.Vector;

public abstract class Shape{
	protected double orientation;
	public Mat22 u = new Mat22();
	public Body body;

	public Shape(){
	}
	
	public abstract Shape copy();
	public abstract BoundingBox boundingBox();
	public abstract void init();
	public abstract void resize(Vector add);
	
	public void setOrientation(double orientation) {
		this.orientation = orientation;
		u = new Mat22(orientation);
	}
}
