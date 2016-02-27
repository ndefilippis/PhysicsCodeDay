package physicsday.view;

import java.awt.Graphics2D;
import physicsday.model.Body;
import physicsday.model.World;

public interface Renderer {
	public void renderBody(Body b, Graphics2D gr, RenderFlag... flags);
	
	public void renderWorld(World world, Graphics2D gr, RenderFlag... flags);
}
