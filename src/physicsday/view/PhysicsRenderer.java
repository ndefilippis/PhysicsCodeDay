package physicsday.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import physicsday.model.Body;
import physicsday.model.CircleShape;
import physicsday.model.PolygonShape;
import physicsday.model.World;
import physicsday.util.Vector;

public class PhysicsRenderer implements Renderer{
	Vector scale;
	Vector offset;
	
	public PhysicsRenderer(){
		scale = new Vector(25, 25);
		offset = new Vector(0, 0);
	}

	private void drawCircle(CircleShape shape, Graphics2D gr) {
		gr.setColor(Color.GREEN);
		gr.fill(screenCircle(shape));
		gr.setColor(Color.RED);
		gr.draw(screenCircle(shape));
		Vector pos = shape.u.multiply(new Vector(shape.radius, 0)).add(shape.body.getPosition());
		pos = getScreenVector(pos);
		Vector c = getScreenVector(shape.body.getPosition());
		gr.drawLine((int)c.x, (int)c.y, (int)pos.x, (int)pos.y);
	}

	private void drawPolygon(PolygonShape shape, Graphics2D gr) {
		double weight = 1;
		gr.setColor(new Color((int)(weight*128), (int)(weight*200), (int)(weight*128)));
		if(shape.body.getInvMass() == 0){
			gr.setColor(Color.BLACK);
		}
		gr.fillPolygon(screenPolygon(shape));
		
		gr.setColor(Color.BLUE);
		gr.drawPolygon(screenPolygon(shape));
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
		Vector radius = scale.multiply(c.radius);
		return new Ellipse2D.Double(pos.x-radius.x, pos.y-radius.y, 2*radius.x, 2*radius.y);
	}
	
	@Override
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
	
	private Vector getScreenVector(Vector vec){
		return vec.multiplyc(scale).add(offset);
	}

	@Override
	public Vector getWorldCoordiantes(Vector vec) {
		return vec.subtract(offset).dividec(scale);
	}
	
	public Vector getScale() {
		return scale;
	}

	public Vector getOffset() {
		return offset;
	}

	public void renderOpaque(Body selectedBody, Graphics2D gr) {
		gr.setColor(new Color(56, 56, 56, 128));
		gr.fill(getScreenArea(selectedBody));
		gr.draw(getScreenArea(selectedBody));
	}

	public void renderSelected(Body selectedBody, Graphics2D gr) {
		gr.setColor(new Color(144, 144, 144, 128));
		gr.fill(getScreenArea(selectedBody));
		gr.setColor(Color.RED);
		gr.draw(getScreenArea(selectedBody));
	}

	@Override
	public void renderBody(Body b, Camera camera, Graphics2D gr, RenderFlag... flags) {
		if(b.shape instanceof CircleShape){
			drawCircle((CircleShape)b.shape, gr);
		}
		if(b.shape instanceof PolygonShape){
			drawPolygon((PolygonShape)b.shape, gr);
		}
	}

	@Override
	public void renderWorld(World world, Camera camera, Graphics2D gr, RenderFlag... flags) {
		// TODO Auto-generated method stub
		
	}

}
