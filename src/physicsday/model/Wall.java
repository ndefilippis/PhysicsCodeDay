package physicsday.model;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;


public class Wall extends AABB{

	public Wall(double x, double y, double width, double height) {
		super(x, y, width, height);
		setMass(Double.POSITIVE_INFINITY);
		anchored = true;
		inv_mass = 0;
	}

	@Override
	public double friction(Shape s) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Shape copy() {
		return new Wall(currState.position.x, currState.position.y, getWidth(), getHeight());
	}
	
}
