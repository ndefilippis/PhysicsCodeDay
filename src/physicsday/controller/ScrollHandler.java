package physicsday.controller;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import physicsday.model.World;
import physicsday.view.PhysicsPanel;

public class ScrollHandler extends PhysicsInput implements MouseWheelListener{

	public ScrollHandler(PhysicsPanel p, World world) {
		//super(p, world);
		view.addMouseWheelListener(this);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		view.xScale /= Math.pow(2, e.getWheelRotation()/15.0);
		view.yScale /= Math.pow(2, e.getWheelRotation()/15.0);
	}

}
