
public class Loop extends Thread {
	public static double dt = 1 / 1000.0;
	static double time;
	static double startTime;
	static double pauseTime;
	static double currTime;
	static double lastTime;
	public static boolean isRunning;
	public static boolean reset;
	
	
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
				Main.label.setText(" time: " + s.substring(0, Math.min(6, s.length())));
				accumulator += currTime - time;
				int count = 0;
				while (accumulator >= dt && count <= 10) {
					World.update(dt);
					accumulator -= dt;
					count++;
				}
				time = currTime;
				Main.panel.repaint();
			}
			Main.panel.repaint();
		}
		
	}
	
	
}
