package physicsday.model;
import physicsday.util.Vector;

public class Collision {
	public Shape a;
	public Shape b;
	public double penetration_depth;
	public Vector normal;
	
	private Collision() {
		normal = new Vector();
	}
	
	public static Collision AABBtoAABB(AABB s1, AABB s2){
		Collision c = new Collision();
		c.a = s1;
		c.b = s2;
		double nx = s1.currState.position.x - s2.currState.position.x;
		double ny = s1.currState.position.y - s2.currState.position.y;
		
		double aex = s1.getWidth()/2;
		double bex = s2.getWidth()/2;
		double xoverlap = aex + bex - Math.abs(nx);
		if(xoverlap > 0){
			double aey = s1.getHeight()/2;
			double bey = s2.getHeight()/2;
			double yoverlap = aey + bey - Math.abs(ny);
			if(xoverlap < yoverlap){
				c.normal.x = nx < 0 ? 1 : -1;
				c.normal.y = 0;
				c.penetration_depth = xoverlap;
				return c;
			}
			else{
				c.normal.y = ny < 0 ? 1 : -1;
				c.normal.x = 0;
				c.penetration_depth = yoverlap;
				return c;
			}
		}
		return null;
	}
	public String toString(){
		return normal.toString()+": "+penetration_depth;
	}
}
