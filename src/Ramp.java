import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;

public class Ramp extends Shape{
	public Ramp(double x, double y, double width, double height) {
		super(x, y, width, height);
		anchored = true;
	}
	
	public void draw(Graphics g){
		int [] x = {drawX(),drawX()+drawWidth(),drawX()+drawWidth()};
		int [] y = {drawY()+drawHeight(), drawY()+drawHeight(), drawY()};
		g.fillPolygon(x,y,3);
	}
	
	@Override
	public double friction(Shape s) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Area getArea() {
		int[] xpoint = {drawX(),drawX()+drawWidth(),drawX()+drawWidth()};
		int[] ypoint = {drawX(),drawX()+drawWidth(),drawX()+drawWidth()};
		Polygon p = new Polygon(xpoint, ypoint, 3);
		return new Area(p);
	}
	

}
