import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	public static Frame frame;
	public static Panel panel;
	public static JLabel label = new JLabel();
	static double time;
	static double startTime;
	public static Block b = new Block(0, 0, 1, 1);
	public static Wall w = new Wall(20.62, 0, 10, 400);
	public static Wall w1 = new Wall(0, 20.62, 400, 400);
	public static double dt = 1/1000.0;
	
	public static void main(String[] args) {
		panel = new Panel();
		frame = new Frame(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.display();
		panel.add(label);
		label.setBounds(1, 0, 12*8, 24);
		label.setOpaque(true);
		b.velocity = new Vector(0, 0);
		World.add(b);
		World.add(w);
		World.add(w1);
		loop();
	}
	public static void loop() {
		time = System.nanoTime()/1000000000.0;
		startTime = time;
		double accumulator = 0.0;
		while(true) {
			double currTime = System.nanoTime()/1000000000.0;
			String s = time - startTime+"";
			label.setText(" time: " + s.substring(0, Math.min(6, s.length())));
			accumulator += currTime - time;
			while(accumulator >= dt){
				World.update(dt);
				accumulator -= dt;
			}
			time = currTime;
			panel.repaint();
		}
	}
}
