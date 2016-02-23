package physicsday;

import java.awt.Color;
import java.awt.Graphics;
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
		PhysicsScreen screen = new PhysicsScreen(1600, 1200, loop, physicsDay);
		screen.setBackground(Color.WHITE);
		PhysicsScreen.showWindow(screen, "PhysicsDay");	
	}

	public World world;
	public boolean playing;
	public double accumulator;
	
	public int xScale = 25;
	public int yScale = 25;
	public int xOffset = 0;
	public int yOffset = 0;

	@Override
	public void start(World world) {
		world = new World();
		accumulator = 0;
		playing = true;
		time = System.nanoTime();
	}

	@Override
	public void input(PhysicsInput input) {
		// TODO Auto-generated method stub
		
	}
	
	long time;
	@Override
	public void update(World world) {
		long currTime = System.nanoTime();
		accumulator += (currTime - time)/1000000000.0;
		while(accumulator >= 1/60.0){
			world.update(1/60.0);
			accumulator -= 1 / 60.0;
		}
		time = currTime;
	}

	public int drawGridlines(Graphics g){
		double xEvery = xScale;
		int count = 0;
		
		while(xEvery <= 5){
			xEvery *= (5+Math.pow(2, count/15.0));
			count++;
		}
		double yEvery = yScale;
		count = 0;
		while(yEvery <= 5){
			yEvery *= (5+Math.pow(2, count/15.0));
			count++;
		}
		int result = count;
		count = 0;
		while(xEvery >= 50){
			xEvery /= 5;
			count--;
		}
		count = 0;
		while(yEvery >= 50){
			yEvery /= 5;
			count--;
		}
		if(count != 0) result = count;
		g.setColor(Color.GRAY);
		for(int i = 0; i <= 1600/xEvery; i++){
			
			g.drawLine((int)(i*xEvery+xOffset%(xEvery*5)), 0, (int)(i*xEvery+xOffset%(xEvery*5)), 1200);
			if(i % 5 == 0){
				g.fillRect((int)((i-1)*xEvery+xOffset%(xEvery*5)), 0, 3, 1200);
				g.setColor(Color.GRAY);
			}
		}
		for(int i = 0; i <= 1200/yEvery; i++){
			g.drawLine(0, (int)(i*yEvery+yOffset%(yEvery*5)), 1600, (int)(i*yEvery+yOffset%(yEvery*5)));
			if(i % 5 == 0){
				g.fillRect(0, (int)((i-1)*yEvery+yOffset%(yEvery*5)), 1600, 3);
				g.setColor(Color.GRAY);
			}
		}
		return result;
	}

	@Override
	public void draw(Graphics2D gr, World world) {
		drawGridlines(gr);
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
		return true;
	}
}
