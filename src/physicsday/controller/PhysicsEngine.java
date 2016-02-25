package physicsday.controller;

import java.awt.Graphics2D;
import physicsday.model.World;

public interface PhysicsEngine {
	
	public void start(World world);
	
	public void input(PhysicsInput input);
	
	public void update(World world);
	
	public void draw(Graphics2D gr, World world);
	
	public void destroy();
	
	public boolean isRunning();
	
	public boolean isUpdating();
}
