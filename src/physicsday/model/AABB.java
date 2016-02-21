package physicsday.model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import physicsday.util.BoundingBox;
import physicsday.util.Vector;
import physicsday.view.PhysicsPanel;

public abstract class AABB extends Shape{
	protected double height;
	protected double width;
	
	public AABB(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics g, PhysicsPanel panel){
		double color = (int)Math.min(body.getInvMass(), 1);
		backgroundColor = new Color((int)(128*color), (int)(200*color), (int)(128*color));
		g.setColor(backgroundColor);
		Vector pos = panel.toScreenCoords(body.getPosition());
		Vector size = panel.toScreenSize(new Vector(width, height));
		g.fillRect((int)(pos.x-size.x/2), (int)(pos.y-size.y/2), (int)size.x, (int)size.y);
		g.setColor(Color.BLACK);
		g.drawRect((int)(pos.x-size.x/2), (int)(pos.y-size.y/2), (int)size.x, (int)size.y);
	}

	public double getWidth() {
		return width;
	}
	public double getHeight(){
		return height;
	}
	public BoundingBox boundingBox(){
		return new BoundingBox(body.getPosition(), width, height);
	}
	public Area getScreenArea(PhysicsPanel panel){
		Vector top = panel.toScreenCoords(new Vector(body.getPosition().x-width/2, body.getPosition().y - height/2));
		Vector size = panel.toScreenSize(new Vector(width, height));
		return new Area(new Rectangle2D.Double(top.x, top.y, size.x, size.y));
	}
}
