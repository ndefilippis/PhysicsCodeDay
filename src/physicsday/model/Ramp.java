package physicsday.model;
import physicsday.util.BoundingBox;

public class Ramp extends Body{
	boolean positive;
	double width, height;

	public Ramp(double width, double height, boolean positive) {
		super(PolygonShape.createRamp(width, height, positive), width, height);	
		this.width = width;
		this.height = height;
		this.positive = positive;
		setMass(Integer.MAX_VALUE);
	}

	@Override
	public Body copy() {
		Ramp r = new Ramp(width, height, this.positive);
		return r;
		
	}
	
	public BoundingBox boundingBox(){
		return new BoundingBox(getPosition(), width, height);
	}

	public void switchFacing() {
		positive = !positive;
	}
	

}
