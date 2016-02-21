package physicsday.model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;

import physicsday.util.BoundingBox;
import physicsday.util.Vector;
import physicsday.view.PhysicsPanel;


public class Ramp extends Shape{
	boolean positive;
	double width, height;

	public Ramp(double width, double height, boolean positive) {
		this.width = width;
		this.height = height;
		this.positive = positive;
		body.setMass(Integer.MAX_VALUE);
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(255, 255, 0));
		/*g.fillPolygon(getOutline());
		g.setColor(Color.BLACK);
		g.drawPolygon(getOutline());*/
		//TODO: replace with Polygon
	}

	@Override
	public Shape copy() {
		Ramp r = new Ramp(width, height, this.positive);
		return r;
		
	}
	
	public BoundingBox boundingBox(){
		return new BoundingBox(body.getPosition(), width, height);
	}

	public void switchFacing() {
		positive = !positive;
	}

	@Override
	public void draw(Graphics g, PhysicsPanel panel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Area getScreenArea(PhysicsPanel panel) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
