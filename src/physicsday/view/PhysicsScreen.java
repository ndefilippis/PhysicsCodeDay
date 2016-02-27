package physicsday.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import physicsday.controller.PhysicsEngine;
import physicsday.controller.PhysicsInput;
import physicsday.controller.PhysicsLoop;
import physicsday.model.Body;
import physicsday.model.World;

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
		HashMap<String, Double> props = selectedBody.getBodyProps();
		JTextField[] propsInput = new JTextField[props.size()];
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(props.size(),3));
		int i = 0;
		for(String s : props.keySet()){
			propsInput[i] = new JTextField(10);
			pan.add(Box.createHorizontalStrut(5));
			pan.add(new JLabel(s));
			pan.add(propsInput[i]);
			propsInput[i].setText(props.get(s).toString());
			pan.add(Box.createHorizontalStrut(5));
			i++;
		}

		
		int result = JOptionPane.showConfirmDialog(null, pan, selectedBody.getClass()+"", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			double val;
			i = 0;
			for(String s : props.keySet()){
				try{
					val = Double.parseDouble(propsInput[i].getText());
					props.put(s, val);
				}
				catch(NumberFormatException e){
					
				}
				i++;
			}
		}
		selectedBody.setBodyProps(props);
	}
	
	
}
