package physicsday.view;

import java.awt.Color;
import java.awt.Graphics2D;

import physicsday.model.Body;
import physicsday.model.CircleShape;
import physicsday.model.PolygonShape;
import physicsday.model.World;

public class PhysicsRenderer implements Renderer{
	private Camera camera;
	private static Color polygonColor = new Color(128, 200, 128);
	private static Color staticColor = Color.BLACK;
	private static Color circleColor = Color.GREEN;
	private static Color outlineColor = Color.BLUE;
	private static Color selectedColor = new Color(144, 144, 144, 128);
	private static Color selectedOutlineColor = Color.RED;
	private static Color opaqueColor = new Color(56, 56, 56, 128);
	
	public PhysicsRenderer(Camera camera){
		this.camera = camera;
	}
	
	@Override
	public void renderBody(Body b, Graphics2D gr, RenderFlag... flags) {
		Color background = Color.WHITE;
		Color outline = outlineColor;
		if(b.shape instanceof CircleShape){
			background = circleColor;
		}
		else if(b.shape instanceof PolygonShape){
			background = polygonColor;
		}
		if(b.getInvMass() == 0){
			background = staticColor;
		}
		if(RenderFlag.contains(RenderFlag.SELECTED, flags)){
			background = selectedColor;
			outline = selectedOutlineColor;
		}
		if(RenderFlag.contains(RenderFlag.OPAQUE, flags)){
			background = opaqueColor;
		}
		gr.setColor(background);
		gr.fill(camera.getScreenArea(b));
		gr.setColor(outline);
		gr.draw(camera.getScreenArea(b));
	}

	@Override
	public void renderWorld(World world, Graphics2D gr, RenderFlag... flags) {
		// TODO Auto-generated method stub
		
	}

}
