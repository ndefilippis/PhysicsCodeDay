package physicsday.controller;

public class NullCommand implements Command {

	@Override
	public void execute() {
		return;
	}

	@Override
	public boolean isReversible() {
		return false;
	}

	@Override
	public void unexecute() {
		return;
	}

}
