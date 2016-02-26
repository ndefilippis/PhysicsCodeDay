package physicsday.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import physicsday.controller.PhysicsEngine;
import physicsday.controller.PhysicsInput;
import physicsday.controller.PhysicsLoop;
import physicsday.model.Body;
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

	public static void showWindow(PhysicsScreen screen, String title){
		if(screen != null){
			JFrame window = new JFrame(title);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	public void popupBodyDialog(Body selectedBody) {
		JTextField mass = new JTextField(10);
		JTextField inertia = new JTextField(10);
		JTextField x = new JTextField(10);
		JTextField y = new JTextField(10);
		JTextField r = new JTextField(10);
		JTextField vx = new JTextField(10);
		JTextField vy = new JTextField(10);
		JTextField vr = new JTextField(10);
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(8,3));

		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Mass"));
		pan.add(mass);
		mass.setText(1.0/selectedBody.getInvMass()+"");
		pan.add(Box.createHorizontalStrut(5));
		
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Inertia"));
		pan.add(inertia);
		inertia.setText(1.0/selectedBody.getInvInertia()+"");
		pan.add(Box.createHorizontalStrut(5));
		
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("x"));
		pan.add(x);
		x.setText(selectedBody.getPosition().x+"");
		pan.add(Box.createHorizontalStrut(5));
		
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("y"));
		pan.add(y);
		y.setText(selectedBody.getPosition().y+"");
		pan.add(Box.createHorizontalStrut(5));
		
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("r"));
		pan.add(r);
		r.setText(selectedBody.getOrientation()+"");
		pan.add(Box.createHorizontalStrut(5));
		
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Vx"));
		pan.add(vx);
		vx.setText(selectedBody.getVelocity().x+"");
		pan.add(Box.createHorizontalStrut(5));
		
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Vy"));
		pan.add(vy);
		vy.setText(selectedBody.getVelocity().y+"");
		pan.add(Box.createHorizontalStrut(5));
		
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Vr"));
		pan.add(vr);
		vr.setText(selectedBody.getAngularVelocity()+"");
		pan.add(Box.createHorizontalStrut(5));
		
		int result = JOptionPane.showConfirmDialog(null, pan, selectedBody.getClass()+"", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			double val;
			
			try{
				val = Double.parseDouble(mass.getText());
				selectedBody.setMass(val);			
			}
			catch(NumberFormatException e){
				
			}
			
			try{
				val = Double.parseDouble(vx.getText());
				selectedBody.setVelocity(val, selectedBody.getVelocity().y);			
			}
			catch(NumberFormatException e){
				
			}
			
			try{
				val = Double.parseDouble(vy.getText());
				selectedBody.setVelocity(selectedBody.getVelocity().x, val);			
			}
			catch(NumberFormatException e){
				
			}
			try{
				val = Double.parseDouble(x.getText());
				Vector pos = new Vector(val, selectedBody.getPosition().y);
				selectedBody.setPosition(pos);			
			}
			catch(NumberFormatException e){
				
			}
			try{
				val = Double.parseDouble(y.getText());
				Vector pos = new Vector(selectedBody.getPosition().x, val);
				selectedBody.setPosition(pos);		
			}
			catch(NumberFormatException e){
				
			}
			try{
				val = Double.parseDouble(vr.getText());
				selectedBody.setAngularVelocity(val);			
			}
			catch(NumberFormatException e){
				
			}
			try{
				val = Double.parseDouble(r.getText());
				selectedBody.setOrientation(val);			
			}
			catch(NumberFormatException e){
				
			}
			
		}
	}
	
	
}
