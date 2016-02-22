package physicsday;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import physicsday.controller.PhysicsEngine;
import physicsday.controller.PhysicsInput;
import physicsday.controller.PhysicsLoop;
import physicsday.controller.PhysicsTime;
import physicsday.controller.VariableLoop;
import physicsday.model.Body;
import physicsday.model.Polygon;
import physicsday.model.World;
import physicsday.util.Vector;
import physicsday.view.PhysicsScreen;

public class PhysicsDay implements PhysicsEngine{
	private static PhysicsDay physicsDay;	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		PhysicsDay physicsDay = new PhysicsDay();
		PhysicsLoop loop = new VariableLoop();
		PhysicsScreen screen = new PhysicsScreen(640, 480, loop, physicsDay);
		screen.setBackground(Color.WHITE);
		PhysicsScreen.showWindow(screen, "PhysicsDay");
	}

	public World world;
	public boolean playing;
	public double accumulator;
	public int xScale;
	public int yScale;
	public int xOffset;
	public int yOffset;

	@Override
	public void start(World world) {
		world = new World();
		accumulator = 0;
		playing = true;
	}

	@Override
	public void input(PhysicsInput input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PhysicsTime time, World world) {
		accumulator += time.seconds;
		if(accumulator >= 1/60.0){
			world.update(1/60.0);
			
			accumulator -= 1 / 60.0;
		}
	}

	@Override
	public void draw(Graphics2D gr, World world) {
		for(Body b : world.getBodies()){
			if(b.shape instanceof Polygon){
				Polygon p = (Polygon)b.shape;
				int[] xpoints = new int[p.numVerticies];
				int[] ypoints = new int[p.numVerticies];
				for(int i = 0; i < p.numVerticies; i++){
					Vector vi = p.verticies[i];
					vi = p.u.multiply(vi).add(p.body.getPosition());
					xpoints[i] = (int)getScreenCoorinates(vi).x;
					ypoints[i] = (int)getScreenCoorinates(vi).y;
				}
				gr.fillPolygon(xpoints, ypoints, p.numVerticies);
			}
		}
	}

	private Vector getScreenCoorinates(Vector vi) {
		double x = vi.x*xScale + xOffset;
		double y = vi.y*yScale + yOffset;
		return new Vector(x, y);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}
}
