package physicsday.model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;

import physicsday.util.Vector;


public class Block extends AABB{
	public Block(double x, double y, double width, double height) {
		super(x, y, height, width);
	}
	
	@Override
	public double friction(Shape s) {
		if(s instanceof Block){
			return 0.0;
		}
		return 0.0;
	}

	@Override
	public Shape copy() {
		Block b = new Block(currState.position.x, currState.position.y, getWidth(), getHeight());
		b.currState.velocity = new Vector(currState.velocity.x, currState.velocity.y);
		return b;
	}

}
