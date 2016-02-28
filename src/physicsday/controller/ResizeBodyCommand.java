package physicsday.controller;

import physicsday.model.Body;
import physicsday.util.Vector;

public class ResizeBodyCommand  implements Command{
	Body body;
	Vector start, end;
	public ResizeBodyCommand(Body b, Vector startPosition, Vector endPosition	){
		body = b;
		start = startPosition;
		end = endPosition;
	}

	@Override
	public void execute() {
		body.shape.resize(end);
	}

	@Override
	public boolean isReversible() {
		return true;
	}

	@Override
	public void unexecute() {
		body.shape.resize(start);
	}
	
}
