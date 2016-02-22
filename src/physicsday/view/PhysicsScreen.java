package physicsday.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import physicsday.controller.PhysicsEngine;
import physicsday.controller.PhysicsInput;
import physicsday.controller.PhysicsLoop;
import physicsday.model.World;

public class PhysicsScreen extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage buffer;
	private PhysicsLoop loop;
	private PhysicsEngine engine;
	private PhysicsInput input;
	private World world;
	
	public PhysicsScreen(int width, int height, PhysicsLoop loop, PhysicsEngine engine){
		Dimension d = new Dimension(width, height);
		this.setSize(d);
		this.setPreferredSize(d);
		this.setFocusable(true);	
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.loop = loop;
		this.engine = engine;
		this.input = new PhysicsInput();
		this.world = new World();
	}
	
	public void start(){
		input.mouseInside = this.getParent().contains(MouseInfo.getPointerInfo().getLocation());
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		addMouseWheelListener(input);
		
		resetGraphics();
		
		engine.start(world);
		
		loop.onStart(engine);
		
		while(engine.isRunning()){
			if(loop.onLoop(engine, input, this.getGraphics(), world)){
				renderGraphics(getGraphics());
				resetGraphics();
			}
		}
		engine.destroy();
		System.exit(0);
	}

	public final void paint(Graphics g){
		if(g == null) return;
		
		resetGraphics();
		renderGraphics( g );
	}
	
	private void renderGraphics( Graphics gr )
	{
		gr.drawImage( buffer, 0, 0, this );
		gr.dispose();
	}
	
	private void resetGraphics()
	{
		// Get the graphics of the buffer
		graphics = (Graphics2D)buffer.getGraphics();

		// Clear the buffer with the background color
		graphics.setColor( getBackground() );
		graphics.fillRect( 0, 0, getWidth(), getHeight() );

		// If antialiasing is turned on enable it.
		if (antialising)
		{
			graphics.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		}
	}
	
	/*public boolean canAddAtLocation(Body toAdd, PhysicsPanel panel) {
		for (Body b : objects) {
			Area a1 = b.shape.getScreenArea(panel);
			a1.intersect(toAdd.shape.getScreenArea(panel));
			if (!a1.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public Body getShapeAt(int x, int y, PhysicsPanel p) {
		for (int i = objects.size() - 1; i >= 0; i--) {
			if (objects.get(i).shape.getScreenArea(p).contains(x, y)) {
				return objects.get(i);
			}
		}
		return null;
	}*/
	
	public static void showWindow(PhysicsScreen screen, String title){
		if(screen != null){
			JFrame window = new JFrame(title);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//MenuBar.createMenuBar(window, screen.world, screen);
			window.add(screen);
			window.pack();
			window.setVisible(true);
			
			screen.start();
		}
	}
}
