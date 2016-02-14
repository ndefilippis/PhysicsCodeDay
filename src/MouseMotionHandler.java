import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionHandler implements MouseMotionListener{

	@Override
	public void mouseDragged(MouseEvent e) {
			if(MouseHandler.draggedItem != null){
				Vector myPosition = new Vector(e.getX()/World.xScale, e.getY()/World.yScale);
				MouseHandler.draggedItem.position = MouseHandler.draggedItem.position.add(myPosition.subtract(Frame.mouseHandler.startPosition));
				Frame.mouseHandler.startPosition = myPosition;
			}
			if(MouseHandler.resizeItem != null){
				Vector myPosition = new Vector(e.getX()/World.xScale, e.getY()/World.yScale);
				MouseHandler.resizeItem.width = MouseHandler.resizeItem.width+ myPosition.subtract(Frame.mouseHandler.startPosition).x;
				MouseHandler.resizeItem.height = MouseHandler.resizeItem.height+ myPosition.subtract(Frame.mouseHandler.startPosition).y;
				Frame.mouseHandler.startPosition = myPosition;
			}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
