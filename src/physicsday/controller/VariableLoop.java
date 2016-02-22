package physicsday.controller;

import java.awt.Graphics2D;
import physicsday.model.World;

public class VariableLoop implements PhysicsLoop {
	private static final double SECONDS_PER_UPDATE = 1.0 / 60.0;
	private static double time;
	private static double startTime;
	private static double pauseTime;
	private static double currTime;
	private static double lastTime;
	private static boolean isRunning = false;
	private static boolean reset;
	@Override
	public void onStart(PhysicsEngine engine) {
	}

	@Override
	public boolean onLoop(PhysicsEngine engine, PhysicsInput input, Graphics2D gr, World world) {
		engine.input(input);
		input.clear();
		
		if(!engine.isPlaying()){
			return false;
		}
		world.update(SECONDS_PER_UPDATE);
		engine.update(world);
		if(!engine.isPlaying()){
			return false;
		}
		engine.draw(gr, world);
		return true;
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
				panel.timeLabel.setText(" time: " + s.substring(0, Math.min(6, s.length()))+" s");
				accumulator += currTime - time;
				accumulator = Math.min(0.2,  accumulator);
				while (accumulator >= SECONDS_PER_UPDATE) {
					world.update(SECONDS_PER_UPDATE);
					accumulator -= SECONDS_PER_UPDATE;
				}
				time = currTime;
				panel.render(world);
			}
			panel.render(world);
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

}
