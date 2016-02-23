import physicsday.util.Vector;

public class VectorTest {

	public static void main(String[] args) {
		Vector ra = new Vector(-12.5, 4.3);
		Vector rb = new Vector(15.1, -1.2);
		Vector velocityB = new Vector(16,10);
		Vector velocityA = new Vector(06, -19.6);
		double angularA = 0.1;
		double angularB = 4.0;
		
		Vector rv = velocityB.add(rb.PreCross(angularB).subtracti(velocityA).subtracti((ra.PreCross(angularA))));
		System.out.println(rv);
		
		ra = new Vector(-12.5, 4.3);
		rb = new Vector(15.1, -1.2);
		velocityB = new Vector(16,10);
		velocityA = new Vector(06, -19.6);
		angularA = 0.1;
		angularB = 4.0;
		// vB + aB x rb - vA - aA x ra
		rv = velocityB.add(rb.PreCross(angularB)).subtract(velocityA.add(ra.PreCross(angularA)));
		System.out.println(rv);
	}

}
