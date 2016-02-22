package physicsday.util;

public class Mat22 {
	private double [][] m = new double[2][2];
	
	public Mat22(double m00, double m01, double m10, double m11){
		this.m[0][0] = m00;
		this.m[0][1] = m01;
		this.m[1][0] = m10;
		this.m[1][1] = m11;
	}
	
	public Mat22(Vector xCol, Vector yCol){
		this.m[0][0] = xCol.x;
		this.m[0][1] = yCol.x;
		this.m[1][0] = xCol.y;
		this.m[1][1]= yCol.y;
	}
	
	public Mat22(double radians){
		double c = Math.cos(radians);
		double s = Math.sin(radians);
		m[0][0] = c;
		m[0][1] = -s;
		m[1][0] = s;
		m[1][1] = c;
	}
	
	public Mat22() {
		m[0][0] = 1;
		m[1][1] = 1;
	}

	public Vector multiply(Vector v){
		return new Vector(m[0][0] * v.x+m[0][1]*v.y, m[1][0]*v.x+m[1][1]*v.y);
	}
	
	public double[][] getMatrix(){
		return m;
	}

	public Mat22 transpose() {
		return new Mat22(m[0][0], m[1][0], m[0][1], m[1][1]);
	}
}
