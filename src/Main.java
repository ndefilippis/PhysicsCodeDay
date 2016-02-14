import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	public static Frame frame;
	public static Panel panel;
	public static JLabel label = new JLabel();
	public static Loop loop;
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		panel = new Panel();
		frame = new Frame(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.display();
		panel.add(label);
		label.setBounds(1, 0, 12*8, 24);
		label.setOpaque(true);
		World.loadWorld("default.phy");
		loop = new Loop();
		World.saveState();
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
		Loop.yield();
	}
	public static void resetLoop() {
		loop = new Loop();
		Loop.reset = true;
		Loop.isRunning = false;
		Loop.yield();
	}
}
