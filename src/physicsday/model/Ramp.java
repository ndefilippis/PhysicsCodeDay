package physicsday.model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;

import physicsday.util.BoundingBox;
import physicsday.util.Vector;


public class Ramp extends Body{
	boolean positive;
	double width, height;

	public Ramp(double width, double height, boolean positive) {
		super(new PolygonShape(), width, height);
		this.width = width;
		this.height = height;
		this.positive = positive;
		setMass(Integer.MAX_VALUE);
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(255, 255, 0));
		/*g.fillPolygon(getOutline());
		g.setColor(Color.BLACK);
		g.drawPolygon(getOutline());*/
		//TODO: replace with Polygon
	}

	@Override
	public Body copy() {
		Ramp r = new Ramp(width, height, this.positive);
		return r;
		
	}
	
	public BoundingBox boundingBox(){
		return new BoundingBox(getPosition(), width, height);
	}

	public void switchFacing() {
		positive = !positive;
	}
	

}
