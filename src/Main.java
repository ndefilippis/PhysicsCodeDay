import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	public static Frame frame;
	public static Panel panel;
	public static JLabel label = new JLabel();
	public static Loop loop;
	
	public static Block b = new Block(0, 0, 1, 1);
	//public static Wall w = new Wall(20.62, 0, 10, 400);
	public static Wall w1 = new Wall(0, 20.62, 400, 400);
	public static Ramp r = new Ramp(30, 10.62, 10, 20, false);
	
	
	public static void main(String[] args) {
		panel = new Panel();
		frame = new Frame(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.display();
		panel.add(label);
		label.setBounds(1, 0, 12*8, 24);
		label.setOpaque(true);
		b.velocity = new Vector(1, 0);
		World.add(b);
		//World.add(w);
		World.add(w1);
		World.add(r);
		loop = new Loop();
		loop.start();
	}
	public static void resumeLoop(){
		loop = new Loop();
		Loop.isRunning = true;
		loop.start();
	}
	
	public static void pauseLoop(){
		Loop.lastTime = Loop.currTime - Loop.startTime;
		Loop.isRunning = false;
		Thread.yield();
	}
}
