import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;


public class Ramp extends Shape{
	boolean positive;

	public Ramp(double x, double y, double width, double height, boolean positive) {
		super(x, y, width, height);
		anchored = true;
		this.positive = positive;
	}
	
	public void draw(Graphics g){
		int [] x;
		int[] y = new int[]{drawY()+drawHeight(), drawY()+drawHeight(), drawY()};
		if(positive)
			x = new int[]{drawX(),drawX()+drawWidth(),drawX()+drawWidth()};
		else{
			x = new int[]{drawX(),drawX()+drawWidth(),drawX()};
		}
		g.setColor(new Color(255, 255, 0));
		g.fillPolygon(x,y,3);
		g.setColor(Color.BLACK);
		g.drawPolygon(x, y, 3);
	}
	
	@Override
	public double friction(Shape s) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Area getArea() {
		int [] x;
		int[] y = new int[]{drawY()+drawHeight(), drawY()+drawHeight(), drawY()};
		if(positive)
			x = new int[]{drawX(),drawX()+drawWidth(),drawX()+drawWidth()};
		else{
			x = new int[]{drawX(),drawX()+drawWidth(),drawX()};
		}
		Polygon p = new Polygon(x, y, 3);
		return new Area(p);
	}
	

}
