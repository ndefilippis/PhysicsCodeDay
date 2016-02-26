package physicsday.view;

import java.awt.geom.Area;

import physicsday.model.Body;
import physicsday.util.Vector;

public class Camera {
	private Vector size;
	private Vector offset;
	private Vector scale;
	
	public Camera(double width, double height, double initialScale){
		this.size = new Vector(width, height);
		this.scale = new Vector(initialScale);
		this.offset = new Vector();
	}
	
	public Vector worldToScreen(Vector vec){
		return vec.multiplyc(scale).add(offset);
	}
	
	public Vector screenToWorld(Vector vec){
		return vec.subtract(offset).dividec(scale);
	}
	
	public Area getScreenArea(Body b){
		return null;
	}
}
