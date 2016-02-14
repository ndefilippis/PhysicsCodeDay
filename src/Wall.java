import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;


public class Wall extends Shape{

	public Wall(double x, double y, double width, double height) {
		super(x, y, width, height);
		anchored = true;
		mass = Integer.MAX_VALUE;
	}

	
	public void draw(Graphics g){
		g.fillRect(drawX(), drawY(), drawWidth(), drawHeight());
	}

	@Override
	public double friction(Shape s) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Area getArea() {
		Rectangle r = new Rectangle(drawX(), drawY(), drawWidth(), drawHeight()); 
		return new Area(r);
		
	}


	@Override
	public Polygon getOutline() {
		int[] x = {drawX(), drawX()+drawWidth(), drawX()+drawWidth(), drawX()};
		int[] y = {drawY(), drawY(), drawY()+drawHeight(), drawY()+drawHeight()};
		return new Polygon(x, y, 4);
	}


	@Override
	public Shape copy() {
		return new Wall(position.x, position.y, width, height);
	}
	
}
