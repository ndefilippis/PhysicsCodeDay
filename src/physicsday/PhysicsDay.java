package physicsday;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JLabel;

import physicsday.controller.PhysicsEngine;
import physicsday.controller.PhysicsInput;
import physicsday.controller.PhysicsLoop;
import physicsday.controller.VariableLoop;
import physicsday.model.Body;
import physicsday.model.CircleShape;
import physicsday.model.PolygonShape;
import physicsday.model.Wall;
import physicsday.model.World;
import physicsday.util.Vector;
import physicsday.view.PhysicsRenderer;
import physicsday.view.PhysicsScreen;
import physicsday.view.Renderer;

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
	public boolean running;
	public double accumulator;
	private Renderer renderer;
	
	long time;
	double elapsedTime;
	private boolean updating = false;

	@Override
	public void start(World world) {
		this.world = world;
		accumulator = 0;
		running = true;
		renderer = new PhysicsRenderer();
		time = System.nanoTime();
	}
	
	
	public static Body bodyToAdd;
	public Body selectedBody;
	@Override
	public void input(PhysicsInput input) {
		((PhysicsRenderer)renderer).moveOffset(input.draggedDistance);
		((PhysicsRenderer)renderer).zoom(new Vector(Math.pow(2, -input.scrollWheel/15.0)));
		if(input.keys > 0){
			if(input.keyDown[KeyEvent.VK_A]){
				PolygonShape p = PolygonShape.createBox(10, 10);
				Body b = new Body(p, 50*Math.random()+5, 5*Math.random()+5);
				b.setVelocity(100*Math.random()-50, 100*Math.random()-50);
				world.add(b);
			}
			if(input.keyDown[KeyEvent.VK_R]){
				
				world.clear();
				world.add(new Wall(50, 24, 100, 10));
			}
			if(input.keyDown[KeyEvent.VK_S]){
				CircleShape c = new CircleShape(Math.random()*7+1);
				Body b = new Body(c, 50*Math.random()+5, 5*Math.random()+5);
				b.setVelocity(10*Math.random()-5, 10*Math.random()-5);
				world.add(b);
			}
			if(input.keyDown[KeyEvent.VK_SPACE]){
				updating = !updating;
				time = System.nanoTime();
			}
		}
		if(input.mouseUp[MouseEvent.BUTTON1]){
			Body b;

			if((b = getSelectedItem(input.pressedLocation)) != null){
				selectedBody = b;
			}
			else{
				Vector worldPos = renderer.getWorldCoordiantes(input.pressedLocation);
				int n = (int)(10*Math.random()+5);
				Vector[] points = new Vector[n];
				for(int i = 0; i < n; i++){
					points[i] = new Vector(100*Math.random()-50, 100*Math.random()-50);
				}
				PolygonShape p = new PolygonShape(points, n);
				world.add(new Body(p, worldPos.x, worldPos.y));
			}
		}
		if(input.mouseUp[MouseEvent.BUTTON3]){
			Vector worldPos = renderer.getWorldCoordiantes(input.pressedLocation);
			CircleShape c = new CircleShape(1);
			world.add(new Body(c, worldPos.x, worldPos.y));
		}
	}
	
	private Body getSelectedItem(Vector pressedLocation) {
		for(Body b : world.getBodies()){
			if(renderer.getScreenArea(b).contains(pressedLocation.x, pressedLocation.y)){
				return b;
			}
		}
		return null;
	}
	
	@Override
	public void update(World world) {
		long currTime = System.nanoTime();
		accumulator += (currTime - time)/1000000000.0;
		elapsedTime += (currTime - time)/1000000000.0;
		while(accumulator >= 1/60.0){
			world.update(1/60.0);
			accumulator -= 1 / 60.0;
		}
		time = currTime;
	}

	public boolean gridlines = true;	

	@Override
	public void draw(Graphics2D gr, World world) {
		if(gridlines){
			drawGridlines(gr);
		}
		for(Body b : world.getBodies()){
			renderer.render(b, gr);
		}
		
		if(selectedBody != null){
			((PhysicsRenderer)renderer).renderSelected(selectedBody, gr);
		}
		if(bodyToAdd != null){
			((PhysicsRenderer)renderer).renderOpaque(bodyToAdd, gr);
		}
		
		gr.setColor(Color.WHITE);
		gr.fillRect(0, 2, 50, 15);
		gr.setColor(Color.BLACK);
		String s = ""+elapsedTime;
		s = s.substring(0, Math.min(s.length(), 7));
		gr.drawString(s, 2, 10);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public boolean isUpdating() {
		return updating ;
	}

	private int drawGridlines(Graphics g){
		Vector scale = ((PhysicsRenderer)renderer).getScale();
		Vector offset = ((PhysicsRenderer)renderer).getOffset();
		double xEvery = scale.x;
		int count = 0;
		
		while(xEvery <= 5){
			xEvery *= (5+Math.pow(2, count/15.0));
			count++;
		}
		double yEvery = scale.y;
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
			
			g.drawLine((int)(i*xEvery+offset.x%(xEvery*5)), 0, (int)(i*xEvery+offset.x%(xEvery*5)), 1200);
			if(i % 5 == 0){
				g.fillRect((int)((i-1)*xEvery+offset.x%(xEvery*5)), 0, 3, 1200);
				g.setColor(Color.GRAY);
			}
		}
		for(int i = 0; i <= 1200/yEvery; i++){
			g.drawLine(0, (int)(i*yEvery+offset.y%(yEvery*5)), 1600, (int)(i*yEvery+offset.y%(yEvery*5)));
			if(i % 5 == 0){
				g.fillRect(0, (int)((i-1)*yEvery+offset.y%(yEvery*5)), 1600, 3);
				g.setColor(Color.GRAY);
			}
		}
		return result;
	}

	
}
