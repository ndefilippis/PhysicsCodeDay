package physicsday.view;

import java.awt.Graphics2D;
import java.awt.geom.Area;

import physicsday.model.Body;
import physicsday.util.Vector;

public interface Renderer {
	public void render(Body b, Graphics2D gr);
	
	public Area getScreenArea(Body b);

	public Vector getWorldCoordiantes(Vector pressedLocation);
}
