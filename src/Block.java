import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;


public class Block extends Shape{

	public Block(double x, double y, double width, double height) {
		super(x, y, height, width);
	}

	public void draw(Graphics g){
		g.setColor(new Color(Math.min((int)(128/mass), 255), Math.min((int)(200/mass), 255), Math.min((int)(128/mass), 255)));
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

	@Override
	public Polygon getOutline() {
		int[] x = {drawX(), drawX()+drawWidth(), drawX()+drawWidth(), drawX()};
		int[] y = {drawY(), drawY(), drawY()+drawHeight(), drawY()+drawHeight()};
		return new Polygon(x, y, 4);
	}

	@Override
	public Shape copy() {
		Block b = new Block(position.x, position.y, width, height);
		b.velocity = new Vector(velocity.x, velocity.y);
		return b;
	}

}
