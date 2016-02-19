package physicsday.controller;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import physicsday.model.World;
import physicsday.view.PhysicsPanel;

public class ScrollHandler implements MouseWheelListener{

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		PhysicsPanel.xScale /= Math.pow(2, e.getWheelRotation()/15.0);
		PhysicsPanel.yScale /= Math.pow(2, e.getWheelRotation()/15.0);
	}

}
