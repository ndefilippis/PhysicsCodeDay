package physicsday.util;

public class BoundingBox {
	private Vector top;
	private Vector bottom;
	
	public BoundingBox(Vector center, double width, double height){
		top = new Vector(center.x - width/2, center.y - height/2);
		bottom = new Vector(center.x + width/2, center.y + height/2);
	}
	
	public BoundingBox(Vector top, Vector bottom){
		this.top = top;
		this.bottom = bottom;
	}
	
	public double x(){
		return top.x;
	}
	public double y(){
		return top.y;
	}
	public double width(){
		return Math.abs(bottom.x - top.x);
	}	
	public double height(){
		return Math.abs(bottom.y - top.y);
	}
}
