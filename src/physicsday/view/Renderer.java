package physicsday.view;

import java.awt.Graphics2D;
import java.awt.geom.Area;

import physicsday.model.Body;
import physicsday.model.World;
import physicsday.util.Vector;

public interface Renderer {
	public void renderBody(Body b, Camera camera, Graphics2D gr, RenderFlag... flags);
	
	public void renderWorld(World world, Camera camera, Graphics2D gr, RenderFlag... flags);
	
	public Area getScreenArea(Body b);

	public Vector getWorldCoordiantes(Vector pressedLocation);
}
