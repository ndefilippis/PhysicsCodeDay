import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
			if((s = World.getShapeAt(e.getX()-Main.panel.getLocationOnScreen().x, e.getY()-Main.panel.getLocationOnScreen().y)) != null){
				if(s.equals(Panel.selectedItem)){
					Panel.popupDialogMenu(s);
				}
				Panel.selectedItem = s;
				return;
			}

		Panel.selectedItem = null;
		Panel.itemInfo.setOpaque(false);
		Panel.itemInfo.setText("");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		startPosition = new Vector(e.getX()/World.xScale,e.getY()/World.yScale);
		resizeItem = Frame.mouseHandler.toAdd;
		MouseMotionHandler.prevPosition = new Vector(e.getX()/World.xScale,e.getY()/World.yScale);
		Shape s;
		if((s = World.getShapeAt(e.getX()-Main.panel.getLocationOnScreen().x, e.getY()-Main.panel.getLocationOnScreen().y)) != null){
			draggedItem = s;
		}
		down = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(Frame.mouseHandler.canAdd && World.canAddAtLocation(Frame.mouseHandler.toAdd)){
			World.objects.add(Frame.mouseHandler.toAdd);
			Panel.selectedItem = Frame.mouseHandler.toAdd;
			Frame.mouseHandler.canAdd = false;
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
