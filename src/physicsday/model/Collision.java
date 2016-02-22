package physicsday.model;
import physicsday.util.BoundingBox;
import physicsday.util.Mat22;
import physicsday.util.Vector;

public class Collision {	
	
	public static void BBoxtoBBox(Manifold m, Body a, Body b){
		BoundingBox aRect = a.shape.boundingBox();
		BoundingBox bRect = b.shape.boundingBox();
		double nx = a.getPosition().x - b.getPosition().x;
		double ny = a.getPosition().y - b.getPosition().x;
				
		double aex = aRect.width()/2;
		double bex = bRect.width()/2;
		double xoverlap = aex + bex - Math.abs(nx);
		if(xoverlap > 0){
			double aey = aRect.height()/2;
			double bey = bRect.height()/2;
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
				m.contacts[0] = new Vector(0, 0);
			}
			m.contactCount = 1;
		}
	}
	
	private static void findIncidentFace( Vector[] v, Polygon RefPoly, Polygon IncPoly, int referenceIndex )
	{
	  Vector referenceNormal = RefPoly.normals[referenceIndex];

	  referenceNormal = RefPoly.u.multiply(referenceNormal);
	  referenceNormal = IncPoly.u.transpose().multiply(referenceNormal);

	  int incidentFace = 0;
	  double minDot = Double.MAX_VALUE;
	  for(int i = 0; i < IncPoly.numVerticies; i++)
	  {
	    double dot = referenceNormal.dot(IncPoly.normals[i]);
	    if(dot < minDot)
	    {
	      minDot = dot;
	      incidentFace = i;
	    }
	  }

	  // Assign face vertices for incidentFace
	  v[0] = IncPoly.u.multiply(IncPoly.verticies[incidentFace].add(IncPoly.body.getPosition()));
	  incidentFace = incidentFace + 1 >= (int)IncPoly.numVerticies ? 0 : incidentFace + 1;
	  v[1] = IncPoly.u.multiply(IncPoly.verticies[incidentFace].add(IncPoly.body.getPosition()));
	}
	
	private static double axisOfLeastPenetrationIndex(int[] faceIndex, Polygon a, Polygon b){
		double bestDistance = -Double.MAX_VALUE;
		int bestIndex = -1;
		for(int i = 0; i < a.numVerticies; i++){
			Vector n = a.normals[i];
			Vector nw = a.u.multiply(n);
			Mat22 buT = b.u.transpose();
			n = buT.multiply(nw);
			
			Vector s = b.getSupport(n.multiply(-1));
			
			Vector v = a.verticies[i];
			v = a.u.multiply(v).add(a.body.getPosition());
			v = v.subtract(b.body.getPosition());
			v = buT.multiply(v);

			double d = n.dot(s.subtract(v));
			if(d > bestDistance){
				bestDistance = d;
				bestIndex = i;
			}
		}
		
		faceIndex[0] = bestIndex;
		return bestDistance;
	}
	
	private static int Clip( Vector n, double c, Vector[] face )
	{
	  int sp = 0;
	  Vector[] out = {
	    face[0],
	    face[1]
	  };

	  double d1 =  n.dot(face[0]) - c;
	  double d2 = n.dot(face[1]) - c;

	  if(d1 <= 0.0f) out[sp++] = face[0];
	  if(d2 <= 0.0f) out[sp++] = face[1];
	  
	  if(d1 * d2 < 0.0f) // less than to ignore -0.0f
	  {
	    // Push interesection point
	    double alpha = d1 / (d1 - d2);
	    out[sp] = face[1].subtract(face[0]).multiply(alpha).add(face[0]);
	    ++sp;
	  }
	  
	  face[0] = out[0];
	  face[1] = out[1];

	  assert( sp != 3 );

	  return sp;
	}
	
	public static void PolygonToPolygon(Manifold m, Body a, Body b) {
		Polygon a1 = (Polygon)a.shape;
		Polygon b1 = (Polygon)b.shape;
		m.contactCount = 0;

		int[] faceA = {0};
		double penetrationA = axisOfLeastPenetrationIndex(faceA, a1, b1);
		if(penetrationA >= 0) return;
		
		int[] faceB = {0};
		double penetrationB = axisOfLeastPenetrationIndex(faceB, b1, a1);
		if(penetrationB >= 0) return;
		
		int refIndex;
		boolean flip;
		
		Polygon ref;
		Polygon inc;
		if(penetrationA >= 0.95*penetrationB + penetrationA*0.01){
			ref = a1;
			inc = b1;
			refIndex = faceA[0];
			flip = false;
		}
		else{
			ref = b1;
			inc = a1;
			refIndex = faceB[0];
			flip = true;
		}
		Vector[] incidentFace = new Vector[2];
		findIncidentFace(incidentFace, ref, inc, refIndex);
		
		
		Vector v1 = ref.verticies[refIndex];
		refIndex = refIndex + 1 == ref.numVerticies ? 0 : refIndex + 1;
		Vector v2 = ref.verticies[refIndex];
		v1 = ref.u.multiply(v1).add(ref.body.getPosition());
		v2 = ref.u.multiply(v2).add(ref.body.getPosition());
		
		Vector sidePlaneNormal = v2.subtract(v1).normalize();
		
		Vector refFaceNormal = new Vector(sidePlaneNormal.y, -sidePlaneNormal.x);
		double refC = refFaceNormal.dot(v1);
		double negSide = -sidePlaneNormal.dot(v1);
		double posSide = sidePlaneNormal.dot(v2);
		if(Clip(sidePlaneNormal.neg(), negSide, incidentFace) < 2){
			return;
		}
		if(Clip(sidePlaneNormal, posSide, incidentFace) < 2){
			return;
		}
		m.normal.set(refFaceNormal);
		if(flip){
			m.normal.negi();
		}
		int cp = 0;
		double separation = refFaceNormal.dot(incidentFace[0]) - refC;
		if(separation <= 0.0){
			m.contacts[cp].set(incidentFace[0]);
			m.penetrationDepth = -separation;
			cp++;
		}
		else{
			m.penetrationDepth = 0;
		}
		separation = refFaceNormal.dot(incidentFace[1]) - refC;
		if(separation <= 0.0){
			m.contacts[cp].set(incidentFace[1]);
			m.penetrationDepth += -separation;
			cp++;
			m.penetrationDepth /= cp;
		}
		
		m.contactCount = cp;
	}
}
