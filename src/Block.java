import java.awt.Graphics;

public class Block extends Shape{

	public Block(double x, double y, double width, double height) {
		super(x, y, height, width);
	}

	public static void main(String[] args) {

	}
	
	public void draw(Graphics g){
		
	}

	@Override
	public double friction(Shape s) {
		if(s instanceof Block){
			return 0.0;
		}
		return 0.0;
	}

}
