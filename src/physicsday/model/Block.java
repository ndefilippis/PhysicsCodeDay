package physicsday.model;

public class Block extends Body{
	double width, height;
	
	public Block(double x, double y, double width, double height) {
		super(PolygonShape.createBox(width, height), x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public Body copy() {
		super.copy();
		Block b = new Block(getPosition().x, getPosition().y, width, height);
		return b;
	}


}
