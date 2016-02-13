import java.awt.Graphics;

public class Ramp extends Shape{

	public Ramp(double x, double y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g){
		int [] x = {0,300,300};
		int [] y = {1600, 1200};
		g.drawPolygon(x,y,3);
	}
	
	@Override
	public double friction(Shape s) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
