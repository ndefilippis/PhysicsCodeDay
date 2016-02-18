
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
		double nx = s1.position.x - s2.position.x;
		double ny = s1.position.y - s2.position.y;
		
		double aex = s1.width/2;
		double bex = s2.width/2;
		double xoverlap = aex + bex - Math.abs(nx);
		if(xoverlap > 0){
			double aey = s1.height/2;
			double bey = s2.height/2;
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
