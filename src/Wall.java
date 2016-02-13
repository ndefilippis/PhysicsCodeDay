import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Area;


public class Wall extends Shape{

	public Wall(double x, double y, double width, double height) {
		super(x, y, width, height);
		anchored = true;
	}
	
	public void update(){
		
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
	public void collide(Shape s) {
		// TODO Auto-generated method stub
		
	}
	
}
