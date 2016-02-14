import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;


public class Block extends Shape{

	public Block(double x, double y, double width, double height) {
		super(x, y, height, width);
	}

	public void draw(Graphics g){
		g.setColor(new Color(128, 200, 128));
		g.fillRect(drawX(), drawY(), drawWidth(), drawHeight());
		g.setColor(Color.BLACK);
		g.drawRect(drawX(), drawY(), drawWidth(), drawHeight());
	}

	@Override
	public double friction(Shape s) {
		if(s instanceof Block){
			return 0.0;
		}
		return 0.0;
	}
	
	@Override
	public Area getArea() {
		Rectangle r = new Rectangle(drawX(), drawY(), drawWidth(), drawHeight()); 
		return new Area(r);
	}

}
