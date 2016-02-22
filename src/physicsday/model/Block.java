package physicsday.model;

public class Block extends Body{
	double width, height;
	
	public Block(double x, double y, double width, double height) {
		super(Polygon.createBox(width, height), x, y);
		this.width = width;
		this.height = height;
		inertia = width*height*height*height/12;
		invInertia = 1/inertia;
	}

	@Override
	public Body copy() {
		super.copy();
		Block b = new Block(getPosition().x, getPosition().y, width, height);
		return b;
	}


}
