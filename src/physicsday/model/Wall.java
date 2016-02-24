package physicsday.model;

public class Wall extends Body{
	double width;
	double height;
	public Wall(double x, double y, double width, double height) {
		super(Polygon.createBox(width, height), x, y);
		this.width = width;
		this.height = height;
		this.setMass(Double.POSITIVE_INFINITY);
		this.setInertia(Double.POSITIVE_INFINITY);
	}

	@Override
	public Body copy() {
		return new Wall(getPosition().x, getPosition().y, width, height);
	}
	
}
