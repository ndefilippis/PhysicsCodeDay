import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionHandler implements MouseMotionListener{

	@Override
	public void mouseDragged(MouseEvent e) {
			if(Panel.selectedItem != null){
				Vector myPosition = new Vector(e.getX()/World.xScale, e.getY()/World.yScale);
				Panel.selectedItem.position = Panel.selectedItem.position.add(myPosition.subtract(Frame.mouseHandler.startPosition));
				Frame.mouseHandler.startPosition = myPosition;
			}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
