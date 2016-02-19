package physicsday.controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import physicsday.model.World;
import physicsday.util.Vector;
import physicsday.view.PhysicsFrame;
import physicsday.view.PhysicsPanel;

public class MouseMotionHandler implements MouseMotionListener {
	public static Vector prevPosition;

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector myPosition = new Vector(e.getX() / PhysicsPanel.xScale, e.getY() / PhysicsPanel.yScale);
		if (MouseHandler.resizeItem != null) {
			MouseHandler.resizeItem.setWidth(MouseHandler.resizeItem.getWidth()
					+ myPosition.subtract(PhysicsFrame.mouseHandler.startPosition).x);
			MouseHandler.resizeItem.setHeight(MouseHandler.resizeItem.getHeight()
					+ myPosition.subtract(PhysicsFrame.mouseHandler.startPosition).y);
			PhysicsFrame.mouseHandler.startPosition = myPosition;
		} else if (MouseHandler.draggedItem != null) {
			MouseHandler.draggedItem.setPosition(MouseHandler.draggedItem.getPosition()
					.add(myPosition.subtract(PhysicsFrame.mouseHandler.startPosition)));
			PhysicsFrame.mouseHandler.startPosition = myPosition;
		} else {
			PhysicsPanel.xOffset += (myPosition.subtract(prevPosition)).x * PhysicsPanel.xScale;
			PhysicsPanel.yOffset += (myPosition.subtract(prevPosition)).y * PhysicsPanel.yScale;
			prevPosition = myPosition;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
