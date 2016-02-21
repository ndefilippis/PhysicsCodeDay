package physicsday.model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;

import physicsday.util.BoundingBox;
import physicsday.util.Vector;


public class Block extends AABB{
	public Block(double width, double height) {
		super(height, width);
	}

	@Override
	public Shape copy() {
		Block b = new Block(width, height);
		return b;
	}


}
