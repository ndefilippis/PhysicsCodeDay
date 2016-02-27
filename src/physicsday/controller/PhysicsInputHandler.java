package physicsday.controller;

import java.util.LinkedList;

import physicsday.model.World;
import physicsday.view.Renderer;

public class PhysicsInputHandler{
	PhysicsInput input;
	World world;
	Renderer renderer;
	LinkedList<Command> commands = new LinkedList<Command>();
	Command head = new NullCommand();
	public PhysicsInputHandler(PhysicsInput input, World world, Renderer renderer){
		this.input = input;
		this.world = world;
		this.renderer = renderer;
		commands.add(head);
	}
}

