package physicsday.model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Area;


public class Ramp extends Shape{
	boolean positive;

	public Ramp(double x, double y, double width, double height, boolean positive) {
		super(x, y, width, height);
		this.positive = positive;
		setMass(Integer.MAX_VALUE);
		anchored = true;
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(255, 255, 0));
		g.fillPolygon(getOutline());
		g.setColor(Color.BLACK);
		g.drawPolygon(getOutline());
	}
	
	@Override
	public double friction(Shape s) {
		return 0;
	}
	
	@Override
	public Area getArea() {
		return new Area(getOutline());
	}

	@Override
	public Polygon getOutline() {
		int [] x;
		int[] y = new int[]{drawY()+drawHeight(), drawY()+drawHeight(), drawY()};
		if(positive)
			x = new int[]{drawX(),drawX()+drawWidth(),drawX()+drawWidth()};
		else{
			x = new int[]{drawX(),drawX()+drawWidth(),drawX()};
		}
		return new Polygon(x, y, 3);
	}

	@Override
	public Shape copy() {
		Ramp r = new Ramp(currState.position.x, currState.position.y, getWidth(), getHeight(), this.positive);
		return r;
		
	}

	public void switchFacing() {
		positive = !positive;
	}
	

}
