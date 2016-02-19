package physicsday.controller;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import physicsday.PhysicsDay;
import physicsday.model.Shape;
import physicsday.model.World;
import physicsday.util.Vector;
import physicsday.view.PhysicsFrame;
import physicsday.view.PhysicsPanel;

public class MouseHandler implements MouseListener{
	public boolean canAdd;
	public Shape toAdd;
	public boolean down;
	public Vector startPosition;
	public static Shape draggedItem;
	public static Shape resizeItem;
	
	@Override
	public void mouseClicked(MouseEvent e) {
			Shape s;
			if((s = World.getShapeAt(e.getX()-PhysicsDay.getPanel().getLocationOnScreen().x, e.getY()-PhysicsDay.getPanel().getLocationOnScreen().y)) != null){
				if(s.equals(PhysicsPanel.selectedItem)){
					PhysicsPanel.popupDialogMenu(s);
				}
				PhysicsPanel.selectedItem = s;
				return;
			}

		PhysicsPanel.selectedItem = null;
		PhysicsPanel.itemInfo.setOpaque(false);
		PhysicsPanel.itemInfo.setText("");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		startPosition = new Vector(e.getX()/PhysicsPanel.xScale,e.getY()/PhysicsPanel.yScale);
		if(PhysicsFrame.mouseHandler.canAdd){
			resizeItem = PhysicsFrame.mouseHandler.toAdd;
		}
		MouseMotionHandler.prevPosition = new Vector(e.getX()/PhysicsPanel.xScale,e.getY()/PhysicsPanel.yScale);
		Shape s;
		if((s = World.getShapeAt(e.getX()-PhysicsDay.getPanel().getLocationOnScreen().x, e.getY()-PhysicsDay.getPanel().getLocationOnScreen().y)) != null){
			draggedItem = s;
		}
		down = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(PhysicsFrame.mouseHandler.canAdd && World.canAddAtLocation(PhysicsFrame.mouseHandler.toAdd)){
			World.objects.add(PhysicsFrame.mouseHandler.toAdd);
			PhysicsPanel.selectedItem = PhysicsFrame.mouseHandler.toAdd;
			PhysicsFrame.mouseHandler.canAdd = false;
			return;
		}
		down = false;
		draggedItem = null;
		resizeItem = null;
	}
	@Override
	public void mouseEntered(MouseEvent e) {	
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
