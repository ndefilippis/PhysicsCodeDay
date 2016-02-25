package physicsday.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import physicsday.controller.PhysicsEngine;
import physicsday.controller.PhysicsInput;
import physicsday.controller.PhysicsLoop;
import physicsday.model.World;
import physicsday.util.Vector;

public class PhysicsScreen extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage buffer;
	private Graphics2D graphics;
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
			if(loop.onLoop(engine, input, graphics, world)){
				repaint();
			}
		}
		engine.destroy();
		System.exit(0);
	}

	public final void paintComponent(Graphics g){
		super.paintComponent(g);
		engine.draw(graphics, world);
		renderGraphics( g );
		resetGraphics();
	}
	
	private void renderGraphics( Graphics gr )
	{
		gr.drawImage( buffer, 0, 0, this );
		gr.dispose();
	}
	
	private void resetGraphics(){
		graphics = (Graphics2D)buffer.createGraphics();
		graphics.setColor(getBackground() );
		graphics.fillRect(0, 0, getWidth(), getHeight() );
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
			window.getContentPane().add(screen);
			MenuBar.createMenuBar(window, screen.world, screen);
			window.pack();
			window.setVisible(true);		
			screen.start();
		}
		
	}


	public double popup(String title) throws IllegalArgumentException{
		JTextField inputField = new JTextField(5);
		JPanel pan = new JPanel();
		pan.add(new JLabel(title));
		pan.add(Box.createHorizontalStrut(5));
		pan.add(inputField);
		int result = JOptionPane.showConfirmDialog(null, pan, "Enter gravity", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			try{
				return Double.parseDouble(inputField.getText());
			}
			catch(NumberFormatException e){
				
			}
		}
		throw new IllegalArgumentException();
	}
	/*JRadioButton delete = new JRadioButton();
	JRadioButton switchSlope = new JRadioButton();
	JTextField xField = new JTextField(5);
	JTextField yField = new JTextField(5);
	JTextField wField = new JTextField(5);
	JTextField hField = new JTextField(5);
	JTextField mass = new JTextField(5);
	JPanel pan = new JPanel();
	if(s instanceof Ramp || s instanceof Block)
		pan.setLayout(new GridLayout(4,5));
	else{
		pan.setLayout(new GridLayout(3, 5));
	}
	pan.add(Box.createHorizontalStrut(5));
	pan.add(new JLabel("Delete"));
	pan.add(Box.createHorizontalStrut(5));
	pan.add(delete);
	pan.add(Box.createHorizontalStrut(5));
	if(s instanceof Ramp){
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Switch Facing"));
		pan.add(Box.createHorizontalStrut(5));
		pan.add(switchSlope);
		pan.add(Box.createHorizontalStrut(5));
	}
	if(s instanceof Block){
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Mass"));
		pan.add(Box.createHorizontalStrut(5));
		pan.add(mass);
		pan.add(Box.createHorizontalStrut(5));
	}
	pan.add(new JLabel("X Velocity:"));
	pan.add(xField);
	pan.add(Box.createHorizontalStrut(5));
	pan.add(new JLabel("Y Velocity:"));
	pan.add(yField);
	pan.add(new JLabel("Width:"));
	pan.add(wField);
	pan.add(Box.createHorizontalStrut(5));
	pan.add(new JLabel("Height:"));
	pan.add(hField);
	int result = JOptionPane.showConfirmDialog(null, pan, "Enter X and Y values", JOptionPane.OK_CANCEL_OPTION);
	if(result == JOptionPane.OK_OPTION){
		if(delete.isSelected()){
			World.removeObject(Input.selectedItem);
			Input.selectedItem = null;
			return;
		}
		try{
			if(s instanceof Ramp && switchSlope.isSelected()){
				((Ramp)s).switchFacing();
			}
			if(s instanceof Block && !mass.getText().isEmpty()){
				try{
					Input.selectedItem.setMass(Double.parseDouble(mass.getText()));
				}
				catch(NumberFormatException e){
					
				}
			}
			double x;
			if(!xField.getText().isEmpty() && !yField.getText().isEmpty()){
				Input.selectedItem.setVelocity(new Vector(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText())));
			}				
		}
		catch(NumberFormatException e){
			
		}
	}*/
	
	
}
