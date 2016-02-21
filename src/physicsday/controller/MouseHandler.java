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

public class MouseHandler extends Input implements MouseListener{
	
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
			if(b.shape.equals(Input.selectedItem)){
				view.popupDialogMenu(b.shape);
			}
			Input.selectedItem = b;
			return;
		}
		Input.startPosition = new Vector(e.getX()/view.xScale,e.getY()/view.yScale);
		if(Input.toAdd != null){
			Input.resizeItem = Input.toAdd;
		}
		Input.prevPosition = new Vector(e.getX()/view.xScale,e.getY()/view.yScale);
		if((b = world.getShapeAt(e.getX()-view.getLocationOnScreen().x, e.getY()-view.getLocationOnScreen().y, view)) != null){
			Input.draggedItem = b;
		}
		Input.down = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(Input.toAdd != null && world.canAddAtLocation(Input.toAdd, view)){
			world.add(Input.toAdd);
			Input.selectedItem = Input.toAdd;
			return;
		}
		Input.down = false;
		Input.draggedItem = null;
		Input.resizeItem = null;
	}
	@Override
	public void mouseEntered(MouseEvent e) {	
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
