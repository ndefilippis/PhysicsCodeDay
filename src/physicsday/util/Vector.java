package physicsday.util;
public class Vector {
	public double x, y;
	
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Vector(){
		this.x = 0;
		this.y = 0;
	}
	public Vector(Vector other){
		this.x = other.x;
		this.y = other.y;
	}
	
	public void set(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public Vector add(Vector add, Vector out){
		out.x = x + add.x;
		out.y = y + add.y;
		return out;
	}
	public Vector add(Vector v){
		return add(v, new Vector());
	}
	public Vector addi(Vector v){
		return add(v, this);
	}
	
	public Vector adds(Vector v, double s, Vector out){
		out.x = x + s * v.x;
		out.y = y + s * v.y;
		return out;
	}
	public Vector adds(Vector v, double s){
		return adds(v, s, new Vector());
	}
	public Vector addsi(Vector v, double s){
		return adds(v, s, this);
	}
	
	public Vector subtract(Vector v, Vector out){
		out.x = x - v.x;
		out.y = y - v.y;
		return out;
	}
	public Vector subtract(Vector v){
		return subtract(v, new Vector());
	}
	public Vector subtracti(Vector v){
		return subtract(v, this);
	}

	public Vector multiply(double s, Vector out){
		out.x = x*s;
		out.y = y*s;
		return out;
	}	
	public Vector multiply(double s){
		return multiply(s, new Vector());
	}
	public Vector multiplyi(double s){
		return multiply(s, this);
	}

	public Vector divide(double s, Vector out){
		double invS = 1.0 / s;
		out.x = x / invS;
		out.y = y / invS;
		return out;
	}	
	public Vector divide(double s){
		return divide(s, new Vector());
	}	
	public Vector dividei(double s){
		return divide(s, this);
	}

	public Vector neg(Vector out){
		out.x = -x;
		out.y = -y;
		return out;
	}
	public Vector neg(){
		return neg(new Vector());
	}
	public Vector negi(){
		return neg(this);
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
	
	public Vector normalizei(){
		this.set(normalize());
		return this;
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
