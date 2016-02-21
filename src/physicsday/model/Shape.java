package physicsday.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import physicsday.util.BoundingBox;
import physicsday.view.PhysicsPanel;

public abstract class Shape{
	protected double orientation;
	protected Color backgroundColor;
	protected Color outline;
	public Body body;

	public Shape(){
		backgroundColor = Color.BLACK;
		outline = Color.BLACK;
	}
	
	public abstract void draw(Graphics g, PhysicsPanel panel);
	
	public abstract Shape copy();
	public abstract Area getScreenArea(PhysicsPanel panel);
	public abstract BoundingBox boundingBox();

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
}
