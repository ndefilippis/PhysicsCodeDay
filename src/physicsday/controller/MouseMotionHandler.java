package physicsday.controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import physicsday.model.World;
import physicsday.util.Vector;
import physicsday.view.PhysicsFrame;
import physicsday.view.PhysicsPanel;

public class MouseMotionHandler extends Input implements MouseMotionListener {

	public MouseMotionHandler(PhysicsPanel p, World world) {
		super(p, world);
		view.addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector myPosition = new Vector(e.getX() / view.xScale, e.getY() / view.yScale);
		/*if(Input.resizeItem != null){
			resizeItem.resize()
		}
		if (input.resizeItem.setWidth(Input.resizeItem.getWidth()
					+ myPosition.subtract(PhysicsFrame.mouseHandler.startPosition).x);
		Input.resizeItem.setHeight(Input.resizeItem.getHeight()
					+ myPosition.subtract(PhysicsFrame.mouseHandler.startPosition).y);
		Input.startPosition = myPosition;
		} else if (MouseHandler.draggedItem != null) {
			Input.draggedItem.setPosition(Input.draggedItem.getPosition()
					.add(myPosition.subtract(Input.startPosition)));
			Input.startPosition = myPosition;
		} else {
			
		}*/ //TODO: implement resize in Shape class
		view.xOffset += (myPosition.subtract(prevPosition)).x * view.xScale;
		view.yOffset += (myPosition.subtract(prevPosition)).y * view.yScale;
		prevPosition = myPosition;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
