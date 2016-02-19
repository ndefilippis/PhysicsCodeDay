package physicsday;

import java.awt.Color;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import physicsday.model.World;
import physicsday.view.PhysicsFrame;
import physicsday.view.PhysicsPanel;

public class PhysicsDay implements Runnable{
	private static PhysicsFrame frame;
	private static PhysicsPanel panel;
	static World world;
	private static final double SECONDS_PER_UPDATE = 1.0 / 320.0;
	private static double time;
	private static double startTime;
	private static double pauseTime;
	private static double currTime;
	private static double lastTime;
	private static boolean isRunning;
	private static boolean reset;
	private static PhysicsDay physicsDay;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		physicsDay = new PhysicsDay();
		panel = new PhysicsPanel();
		frame = new PhysicsFrame(panel);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().pack();
		getFrame().display();
		World.loadWorld("magic_test.phy");
		World.saveState();
		new Thread(physicsDay).start();
	}
	public static void resumeLoop(){
		isRunning = true;
		new Thread(physicsDay).start();
	}
	
	public static void pauseLoop(){
		lastTime = currTime - startTime;
		isRunning = false;
	}
	public static void resetLoop() {
		reset = true;
		isRunning = false;
	}
	
	public void run() {
		time = System.nanoTime()/1000000000.0;
		if(reset){
			startTime = time;
			reset = false;
		}
		else{
			startTime = time - lastTime;
		}
		pauseTime = 0;
		double accumulator = 0.0;
		while(true){
			time = System.nanoTime()/1000000000.0;
			while (isRunning) {
				currTime = System.nanoTime() / 1000000000.0;
				String s = time - startTime + "";
				PhysicsPanel.label.setText(" time: " + s.substring(0, Math.min(6, s.length()))+" s");
				accumulator += currTime - time;
				int count = 0;
				while (accumulator >= SECONDS_PER_UPDATE && count <= 10) {
					World.update(SECONDS_PER_UPDATE);
					accumulator -= SECONDS_PER_UPDATE;
					count++;
				}
				time = currTime;
				PhysicsDay.getPanel().repaint();
			}
			PhysicsDay.getPanel().repaint();
		}
		
	}
	public static void toggleRunning() {
		if(isRunning){
			pauseLoop();
		}
		else{
			resumeLoop();
		}
		
	}
	public static PhysicsFrame getFrame() {
		return frame;
	}
	public static PhysicsPanel getPanel() {
		return panel;
	}
}
