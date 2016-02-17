import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ScrollHandler implements MouseWheelListener{

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		World.xScale /= Math.pow(2, e.getWheelRotation()/15.0);
		World.yScale /= Math.pow(2, e.getWheelRotation()/15.0);
	}

}
