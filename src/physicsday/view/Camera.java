package physicsday.view;

import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physicsday.model.Body;
import physicsday.model.CircleShape;
import physicsday.model.PolygonShape;
import physicsday.util.Vector;

public class Camera {
	private Vector offset;
	private Vector scale;
	
	public Camera(double width, double height, double initialScale){
		this.scale = new Vector(initialScale);
		this.offset = new Vector();
	}
	
	private Polygon screenPolygon(PolygonShape p){
		int[] xpoints = new int[p.numVerticies];
		int[] ypoints = new int[p.numVerticies];
		for(int i = 0; i < p.numVerticies; i++){
			Vector v = p.u.multiply(p.verticies[i]).add(p.body.getPosition());
			Vector vScreen = getScreenVector(v);
			xpoints[i] = (int)vScreen.x;
			ypoints[i] = (int)vScreen.y;
		}
		return new Polygon(xpoints, ypoints, p.numVerticies);
	}
	
	private Ellipse2D screenCircle(CircleShape c) {
		Vector pos = getScreenVector(c.body.getPosition());
		Vector radius = getScreenSize(new Vector(c.radius));
		return new Ellipse2D.Double(pos.x-radius.x, pos.y-radius.y, 2*radius.x, 2*radius.y);
	}
	
	public Area getScreenArea(Body b) {
		if(b.shape instanceof PolygonShape){
			return new Area(screenPolygon((PolygonShape)b.shape));
		}
		else if (b.shape instanceof CircleShape){
			return new Area(screenCircle((CircleShape)b.shape));
		}
		return new Area();
	}
	
	public void zoom(Vector v){
		scale.multiplyci(v);
	}
	
	public void moveOffset(Vector v){
		offset.addi(v);
	}
	
	public Vector getScreenVector(Vector vec){
		return vec.multiplyc(scale).add(offset);
	}

	public Vector getWorldCoordiantes(Vector vec) {
		return vec.subtract(offset).dividec(scale);
	}

	public  Vector getScreenSize(Vector vector) {
		return vector.multiplyc(scale);
	}

	
}
