import java.awt.Graphics;
import java.awt.geom.Area;


public class Block extends Shape{

	public Block(double x, double y, double width, double height) {
		super(x, y, height, width);
	}

	
	public void draw(Graphics g){
		g.fillRect((int)position.x*World.xScale, (int)position.y, (int)(width), (int)(height));
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
		// TODO Auto-generated method stub
		return null;
	}

}
