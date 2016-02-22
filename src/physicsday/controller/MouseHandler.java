package physicsday.controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import physicsday.PhysicsDay;
import physicsday.model.Body;
import physicsday.model.Shape;
import physicsday.model.World;
import physicsday.util.Vector;
import physicsday.view.PhysicsFrame;
import physicsday.view.PhysicsPanel;

public class MouseHandler extends PhysicsInput implements MouseListener{
	
	public MouseHandler(PhysicsPanel p, World world) {
		super(p, world);
		view.addMouseListener(this);
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		Body b;
		if((b = world.getShapeAt(e.getX()-view.getLocationOnScreen().x, e.getY()-view.getLocationOnScreen().y, view)) != null){
			if(b.shape.equals(PhysicsInput.selectedItem)){
				view.popupDialogMenu(b.shape);
			}
			PhysicsInput.selectedItem = b;
			return;
		}
		PhysicsInput.startPosition = new Vector(e.getX()/view.xScale,e.getY()/view.yScale);
		if(PhysicsInput.toAdd != null){
			PhysicsInput.resizeItem = PhysicsInput.toAdd;
		}
		PhysicsInput.prevPosition = new Vector(e.getX()/view.xScale,e.getY()/view.yScale);
		if((b = world.getShapeAt(e.getX()-view.getLocationOnScreen().x, e.getY()-view.getLocationOnScreen().y, view)) != null){
			PhysicsInput.draggedItem = b;
		}
		PhysicsInput.down = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(PhysicsInput.toAdd != null && world.canAddAtLocation(PhysicsInput.toAdd, view)){
			world.add(PhysicsInput.toAdd);
			PhysicsInput.selectedItem = PhysicsInput.toAdd;
			return;
		}
		PhysicsInput.down = false;
		PhysicsInput.draggedItem = null;
		PhysicsInput.resizeItem = null;
	}
	@Override
	public void mouseEntered(MouseEvent e) {	
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
