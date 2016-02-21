package physicsday.util;
public class Vector {
	public double x, y;
	
	
	
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector(){
	}
	
	public Vector(Vector other){
		this.x = other.x;
		this.y = other.y;
	}
	
	public Vector add(Vector v){
		return new Vector(x+v.x, y+v.y);
	}
	public Vector subtract(Vector v){
		return new Vector(x-v.x, y-v.y);
	}
	
	public Vector multiply(double s){
		return new Vector(x*s, y*s);
	}
	
	public Vector divide(double s){
		return multiply(1.0/s);
	}
	
	public double dot(Vector v){
		return x*v.x + y*v.y;
	}
	
	public double lengthSquared(){
		return this.dot(this);
	}
	
	public double length(){
		return Math.sqrt(lengthSquared());
	}
	
	public Vector normalize(){
		if(this.lengthSquared() == 0.0){
			return new Vector(0, 0);
		}
		return this.divide(length());
	}
	
	public Vector project(Vector v){
		return v.multiply(dot(v)/(v.length()*length()));
	}
	
	public Vector rotate(double theta){
		double c = Math.cos(theta);
		double s = Math.sin(theta);
		double px = x*c - y*s;
		double py = x*s + y*c;
		return new Vector(px, py);
	}
	
	public double theta(){
		return Math.atan2(y, x);
	}
	
	public String toString(){
		return "<"+x+", "+y+">";
	}

	public static boolean isValid(Vector vec) {
		return !Double.isNaN(vec.x) && !Double.isNaN(vec.y);
	}
	
	public double Cross(Vector a){
		return x*a.y - y * a.x;
	}
	
	// this x s
	public Vector Cross(double s){
		return new Vector(s*y, -s*x);
	}
	
	//s x this
	public Vector PreCross(double s){
		return new Vector(-s * y, s*x);
	}
}
