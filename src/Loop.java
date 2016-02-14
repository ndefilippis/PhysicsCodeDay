
public class Loop extends Thread {
	public static double dt = 1 / 320.0;
	static double time;
	static double startTime;
	static double pauseTime;
	static double currTime;
	static double lastTime;
	public static boolean isRunning;
	
	
	public void run() {
		time = System.nanoTime()/1000000000.0;
		startTime = time - lastTime;
		pauseTime = 0;
		double accumulator = 0.0;
		while(true){
			while (isRunning) {
				currTime = System.nanoTime() / 1000000000.0;
				String s = time - startTime + "";
				Main.label.setText(" time: " + s.substring(0, Math.min(6, s.length())));
				accumulator += currTime - time;
				while (accumulator >= dt) {
					World.update(dt);
					accumulator -= dt;
				}
				time = currTime;
				Main.panel.repaint();
			}
			Main.panel.repaint();
		}
		
	}
	
	
}
