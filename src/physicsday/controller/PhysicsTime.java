package physicsday.controller;

public class PhysicsTime {
	public float seconds;
	public long nanos;
	public long startTime;
	public long lastTime;
	public long currentTime;

	public PhysicsTime()
	{
	}

	public long reset()
	{
		long resetTime = System.nanoTime();

		startTime = resetTime;
		lastTime = resetTime;
		currentTime = resetTime;
		return resetTime;
	}

	public long tick()
	{
		lastTime = currentTime;
		currentTime = System.nanoTime();
		return (currentTime - lastTime);
	}

	public long getElapsedSinceTick()
	{
		return (System.nanoTime() - currentTime);
	}

	public void setElapsed( long nanosElapsed )
	{
		nanos = nanosElapsed;
		seconds = (float)(nanosElapsed * 0.000000001);
	}

	public void update()
	{
	}

	public void draw()
	{
	}
}
