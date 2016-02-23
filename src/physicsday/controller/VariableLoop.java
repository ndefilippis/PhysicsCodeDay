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
		
		if(!engine.isRunning()){
			return false;
		}
		world.update(SECONDS_PER_UPDATE);
		engine.update(world);
		if(!engine.isRunning()){
			return false;
		}
		engine.draw(gr, world);
		return true;
	}
	public static void resumeLoop(){
		isRunning = true;
		//new Thread(physicsDay).start();
	}
	
	public static void pauseLoop(){
		lastTime = currTime - startTime;
		isRunning = false;
	}
	public static void resetLoop() {
		reset = true;
		isRunning = false;
	}
	
	public void toggleRunning() {
		if(isRunning){
			pauseLoop();
		}
		else{
			resumeLoop();
		}
		
	}

}
