package physicsday.controller;

import java.awt.Graphics2D;
import physicsday.model.World;

public class VariableLoop implements PhysicsLoop {
	private static final double SECONDS_PER_UPDATE = 1.0 / 320.0;
	private static double startTime;
	private static double currTime;
	private static boolean isRunning = false;
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
		if(engine.isUpdating()){
			engine.update(world);
		}
		if(!engine.isRunning()){
			return false;
		}
		//engine.draw(gr, world);
		return true;
	}

	@Override
	public void toggleRunning() {
		isRunning = !isRunning;
	}

}
