import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionHandler implements MouseMotionListener {
	public static Vector prevPosition;

	@Override
	public void mouseDragged(MouseEvent e) {
		Vector myPosition = new Vector(e.getX() / World.xScale, e.getY() / World.yScale);
		if (MouseHandler.resizeItem != null) {
			MouseHandler.resizeItem.width = MouseHandler.resizeItem.width
					+ myPosition.subtract(Frame.mouseHandler.startPosition).x;
			MouseHandler.resizeItem.height = MouseHandler.resizeItem.height
					+ myPosition.subtract(Frame.mouseHandler.startPosition).y;
			Frame.mouseHandler.startPosition = myPosition;
		} else if (MouseHandler.draggedItem != null) {
			MouseHandler.draggedItem.position = MouseHandler.draggedItem.position
					.add(myPosition.subtract(Frame.mouseHandler.startPosition));
			Frame.mouseHandler.startPosition = myPosition;
		} else {
			World.xOffset += (myPosition.subtract(prevPosition)).x * World.xScale;
			World.yOffset += (myPosition.subtract(prevPosition)).y * World.yScale;
			prevPosition = myPosition;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
