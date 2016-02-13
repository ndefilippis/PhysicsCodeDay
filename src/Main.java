import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	public static Frame frame;
	public static Panel panel;
	static double time;
	static double startTime;
	public static Block b = new Block(0, 0, 1, 1);
	public static Wall w = new Wall(30, 0, 10, 400);
	public static Wall w1 = new Wall(0, 20, 400, 400);
	
	public static void main(String[] args) {
		panel = new Panel();
		frame = new Frame(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.display();
		b.velocity = new Vector(0, 0);
		World.add(b);
		World.add(w);
		World.add(w1);
		loop();
	}
	public static void loop() {
		time = System.nanoTime()/1000000000.0;
		while(true) {
			double currTime = System.nanoTime()/1000000000.0;
			World.update(currTime - time);
			panel.repaint();
			time = currTime;
		}
	}
}
