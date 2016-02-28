package physicsday.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import physicsday.model.Body;
import physicsday.model.CircleShape;
import physicsday.model.PolygonShape;
import physicsday.model.Wall;
import physicsday.model.World;
import physicsday.util.Vector;
import physicsday.view.Camera;
import physicsday.view.Renderer;

public class PhysicsInputHandler{
	PhysicsInput input;
	World world;
	Renderer renderer;
	Camera camera;
	LinkedList<Command> commands = new LinkedList<Command>();
	Command head = new NullCommand();
	
	private GameState state = GameState.DEFAULT;
	private enum GameState{
		DRAGGING, RESIZING, SELECTING, DEFAULT;
	}
	
	public PhysicsInputHandler(PhysicsInput input, World world, Renderer renderer, Camera camera){
		this.input = input;
		this.world = world;
		this.renderer = renderer;
		this.camera = camera;
		commands.add(head);
	}
	
	private Body selectedBody;
	
	public void input(PhysicsInput input) {
		if(selectedBody != null){
			//bodyToAdd.setSize();
		}
		if(input.draggedDistance.lengthSquared() != 0){
			//if()
			camera.moveOffset(input.draggedDistance);
		}
		camera.zoom(new Vector(Math.pow(2, -input.scrollWheel/15.0)));
			if(input.keysUp[KeyEvent.VK_A]){
				PolygonShape p = PolygonShape.createBox(10, 10);
				Body b = new Body(p, 50*Math.random()+5, 5*Math.random()+5);
				b.setVelocity(100*Math.random()-50, 100*Math.random()-50);
				world.add(b);
			}
			if(input.keysUp[KeyEvent.VK_R]){
				
				world.clear();
				world.add(new Wall(50, 24, 100, 10));
			}
			if(input.keysDown[KeyEvent.VK_S]){
				CircleShape c = new CircleShape(Math.random()*7+1);
				Body b = new Body(c, 50*Math.random()+5, 5*Math.random()+5);
				b.setVelocity(10*Math.random()-5, 10*Math.random()-5);
				world.add(b);
			}
			if(input.keysDown[KeyEvent.VK_SPACE]){
				updating = !updating;
				time = System.nanoTime();
			}
			if(input.keysDown[KeyEvent.VK_BACK_SPACE] || input.keysDown[KeyEvent.VK_DELETE]){
				if(selectedBody != null){
					world.removeObject(selectedBody);
					selectedBody = null;
				}
			}
		if(input.mouseDown[MouseEvent.BUTTON1]){
			if(bodyToAdd != null){
				bodyToAdd.shape.resize(camera.getWorldCoordiantes(input.lastLocation));
			}
		}
		if(input.mouseUp[MouseEvent.BUTTON1]){
			Body b;

			if((b = getSelectedItem(input.pressedLocation)) != null){
				if(b.equals(selectedBody)){
					screen.popupBodyDialog(selectedBody);
				}
				selectedBody = b;
			}
			else if(selectedBody != null){
				selectedBody = null;
			}
			else if(bodyToAdd != null){
				world.add(bodyToAdd);
				bodyToAdd = null;
			}
		}
		if(input.mouseUp[MouseEvent.BUTTON3]){
			Vector worldPos = camera.getWorldCoordiantes(input.pressedLocation);
			CircleShape c = new CircleShape(1);
			world.add(new Body(c, worldPos.x, worldPos.y));
		}
		if(bodyToAdd != null && !input.mouseDown[MouseEvent.BUTTON1]){
			Vector pos = camera.getWorldCoordiantes(input.lastLocation);
			bodyToAdd.setPosition(pos);
		}
	}
}

