import java.awt.Graphics;

public class Ramp extends Shape{

	public Ramp(double x, double y, double width, double height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g){
		int [] x = {(int) position.x,(int) width,(int) width};
		int [] y = {(int) position.y, (int) height, (int) position.y};
		g.fillPolygon(x,y,3);
	}
	
	@Override
	public double friction(Shape s) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
