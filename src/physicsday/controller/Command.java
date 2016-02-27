package physicsday.controller;

public interface Command {
	public void execute();
	
	public boolean isReversible();
	
	public void unexecute();
}
