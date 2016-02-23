package physicsday.controller;

import physicsday.model.Shape;
import physicsday.util.Vector;

public class Input {
	public boolean canAdd;
	public Shape toAdd;
	public boolean down;
	public Vector startPosition;
	public static Shape draggedItem;
	public static Shape resizeItem;
	
	public Input(){
	}
}
