package physicsday.controller;

import java.awt.Graphics2D;

import physicsday.model.World;

public interface PhysicsLoop {
	public void onStart(PhysicsEngine engine);
	
	public boolean onLoop( PhysicsEngine engine, PhysicsInput input, Graphics2D gr, World world );

	public void toggleRunning();
}
