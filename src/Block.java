import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;


public class Block extends Shape{

	public Block(double x, double y, double width, double height) {
		super(x, y, height, width);
	}

	public void draw(Graphics g){
		g.fillRect(drawX(), drawY(), drawWidth(), drawHeight());
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
