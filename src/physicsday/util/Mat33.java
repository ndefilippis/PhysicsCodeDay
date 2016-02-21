package physicsday.util;

public class Mat33 {
	private double[][] m = new double[3][3];
	
	public Mat33(Mat22 rotation, Vector translation){
		m[0][0] = rotation.getMatrix()[0][0];
		m[0][1] = rotation.getMatrix()[0][1];
		m[1][0] = rotation.getMatrix()[1][0];
		m[1][1] = rotation.getMatrix()[1][1];
		m[2][0] = translation.x;
		m[2][1] = translation.y;
		m[2][2] = 1;
	}
	
	public Mat33(double theta, Vector translation){
		this(new Mat22(theta), translation);		
	}
}
