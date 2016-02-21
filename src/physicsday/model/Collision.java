package physicsday.model;
import physicsday.util.Vector;

public class Collision {	
	
	private double axisOfLeastPenetration(int faceIndex, AABB a, AABB b){
		double bestDistance = Double.MIN_VALUE;
		int bestIndex = -1;
		for(int i = 0; i < 4; i++){
			Vector n = null;
		}
		return bestDistance;
	}
	
	public static void AABBtoAABB(Manifold m, Body a, Body b){
		AABB aRect = (AABB)a.shape;
		AABB bRect = (AABB)b.shape;
		double nx = a.getPosition().x - b.getPosition().x;
		double ny = a.getPosition().y - b.getPosition().x;
				
		double aex = aRect.getWidth()/2;
		double bex = bRect.getWidth()/2;
		double xoverlap = aex + bex - Math.abs(nx);
		if(xoverlap > 0){
			double aey = aRect.getHeight()/2;
			double bey = bRect.getHeight()/2;
			double yoverlap = aey + bey - Math.abs(ny);
			if(xoverlap < yoverlap){
				m.normal.x = nx < 0 ? 1 : -1;
				m.normal.y = 0;
				m.penetrationDepth = xoverlap;
			}
			else{
				m.normal.y = ny < 0 ? 1 : -1;
				m.normal.x = 0;
				m.penetrationDepth = yoverlap;

			}
		}
	}
	public static void PolygonToPolygon(Manifold manifold, Body a, Body b) {
		// TODO Auto-generated method stub
		
	}
}
