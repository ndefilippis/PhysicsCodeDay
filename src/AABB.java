import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;

public class AABB extends Shape{

	public AABB(double x, double y, double width, double height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics g){
		g.setColor(new Color(Math.min((int)(128*inv_mass), 255), Math.min((int)(200*inv_mass), 255), Math.min((int)(128*inv_mass), 255)));
		g.fillRect(drawX()-drawWidth()/2, drawY()-drawHeight()/2, drawWidth(), drawHeight());
		g.setColor(Color.BLACK);
		g.drawRect(drawX()-drawWidth()/2, drawY()-drawHeight()/2, drawWidth(), drawHeight());
	}

	@Override
	public Polygon getOutline() {
		int[] x = {drawX()-drawWidth()/2, drawX()+drawWidth()/2, drawX()+drawWidth()/2, drawX()-drawWidth()/2};
		int[] y = {drawY()-drawHeight()/2, drawY()-drawHeight()/2, drawY()+drawHeight()/2, drawY()+drawHeight()/2};
		return new Polygon(x, y, 4);
	}

	@Override
	public Area getArea() {
		Rectangle r = new Rectangle(drawX()-drawWidth()/2, drawY()-drawHeight()/2, drawWidth(), drawHeight()); 
		return new Area(r);
	}

	@Override
	public Shape copy() {
		AABB aabb = new AABB(position.x, position.y, width, height);
		aabb.velocity = new Vector(velocity.x, velocity.y);
		return aabb;
	}

	@Override
	public double friction(Shape s) {
		return 0;
	}

}
