package physicsday.model;

import physicsday.util.Mat22;
import physicsday.util.Vector;

public class Collision {	

	public static boolean gt(double a, double b){
		return a >= 0.95*b + 0.01*a;
	}

	public static void PolygonToPolygon( Manifold m, Body a, Body b )
	{
		Polygon A = (Polygon)a.shape;
		Polygon B = (Polygon)b.shape;
		m.contactCount = 0;

		// Check for a separating axis with A's face planes
		int[] faceA = { 0 };
		double penetrationA = findAxisLeastPenetration( faceA, A, B );
		if (penetrationA >= 0.0f)
		{
			return;
		}

		// Check for a separating axis with B's face planes
		int[] faceB = { 0 };
		double penetrationB = findAxisLeastPenetration( faceB, B, A );
		if (penetrationB >= 0.0f)
		{
			return;
		}

		int referenceIndex;
		boolean flip; // Always point from a to b

		Polygon RefPoly; // Reference
		Polygon IncPoly; // Incident

		// Determine which shape contains reference face
		if (gt( penetrationA, penetrationB ))
		{
			RefPoly = A;
			IncPoly = B;
			referenceIndex = faceA[0];
			flip = false;
		}
		else
		{
			RefPoly = B;
			IncPoly = A;
			referenceIndex = faceB[0];
			flip = true;
		}

		// World space incident face
		Vector[] incidentFace = new Vector[2];
		incidentFace[0] = new Vector();
		incidentFace[1] = new Vector();

		findIncidentFace( incidentFace, RefPoly, IncPoly, referenceIndex );

		// y
		// ^ .n ^
		// +---c ------posPlane--
		// x < | i |\
		// +---+ c-----negPlane--
		// \ v
		// r
		//
		// r : reference face
		// i : incident poly
		// c : clipped point
		// n : incident normal

		// Setup reference face vertices
		Vector v1 = RefPoly.verticies[referenceIndex];
		referenceIndex = referenceIndex + 1 == RefPoly.numVerticies ? 0 : referenceIndex + 1;
		Vector v2 = RefPoly.verticies[referenceIndex];

		// Transform vertices to world space
		// v1 = RefPoly->u * v1 + RefPoly->body->position;
		// v2 = RefPoly->u * v2 + RefPoly->body->position;
		v1 = RefPoly.u.multiply( v1 ).addi( RefPoly.body.getPosition() );
		v2 = RefPoly.u.multiply( v2 ).addi( RefPoly.body.getPosition() );

		// Calculate reference face side normal in world space
		// Vector sidePlaneNormal = (v2 - v1);
		// sidePlaneNormal.Normalize( );
		Vector sidePlaneNormal = v2.subtract( v1 );
		sidePlaneNormal.normalizei();

		// Orthogonalize
		// Vector refFaceNormal( sidePlaneNormal.y, -sidePlaneNormal.x );
		Vector refFaceNormal = new Vector( sidePlaneNormal.y, -sidePlaneNormal.x );

		// ax + by = c
		// c is distance from origin
		// real refC = Dot( refFaceNormal, v1 );
		// real negSide = -Dot( sidePlaneNormal, v1 );
		// real posSide = Dot( sidePlaneNormal, v2 );
		double refC = refFaceNormal.dot(v1);
		double negSide = -sidePlaneNormal.dot(v1);
		double posSide = sidePlaneNormal.dot(v2);

		// Clip incident face to reference face side planes
		// if(Clip( -sidePlaneNormal, negSide, incidentFace ) < 2)
		if (clip( sidePlaneNormal.neg(), negSide, incidentFace ) < 2)
		{
			return; // Due to doubleing point error, possible to not have required
						// points
		}

		// if(Clip( sidePlaneNormal, posSide, incidentFace ) < 2)
		if (clip( sidePlaneNormal, posSide, incidentFace ) < 2)
		{
			return; // Due to doubleing point error, possible to not have required
						// points
		}

		// Flip
		m.normal.set( refFaceNormal );
		if (flip)
		{
			m.normal.negi();
		}

		// Keep points behind reference face
		int cp = 0; // clipped points behind reference face
		double separation = refFaceNormal.dot(incidentFace[0]) - refC;
		if (separation <= 0.0f)
		{
			m.contacts[cp].set( incidentFace[0] );
			m.penetrationDepth = -separation;
			++cp;
		}
		else
		{
			m.penetrationDepth = 0;
		}

		separation = refFaceNormal.dot(incidentFace[1]) - refC;

		if (separation <= 0.0f)
		{
			m.contacts[cp].set( incidentFace[1] );

			m.penetrationDepth += -separation;
			++cp;

			// Average penetration
			m.penetrationDepth /= cp;
		}

		m.contactCount = cp;
	}

	public static double findAxisLeastPenetration( int[] faceIndex, Polygon A, Polygon B )
	{
		double bestDistance = -Float.MAX_VALUE;
		int bestIndex = 0;

		for (int i = 0; i < A.numVerticies; ++i)
		{
			// Retrieve a face normal from A
			// Vector n = A->m_normals[i];
			// Vector nw = A->u * n;
			Vector nw = A.u.multiply( A.normals[i] );

			// Transform face normal into B's model space
			// Mat2 buT = B->u.Transpose( );
			// n = buT * nw;
			Mat22 buT = B.u.transpose();
			Vector n = buT.multiply( nw );

			// Retrieve support point from B along -n
			// Vector s = B->GetSupport( -n );
			Vector s = B.getSupport( n.neg() );

			// Retrieve vertex on face from A, transform into
			// B's model space
			// Vector v = A->m_vertices[i];
			// v = A->u * v + A->body->position;
			// v -= B->body->position;
			// v = buT * v;
			Vector v = buT.multiplyi( A.u.multiply( A.verticies[i] ).addi( A.body.getPosition() ).subtracti( B.body.getPosition() ) );

			// Compute penetration distance (in B's model space)
			// real d = Dot( n, s - v );
			double d = n.dot(s.subtract( v ));

			// Store greatest distance
			if (d > bestDistance)
			{
				bestDistance = d;
				bestIndex = i;
			}
		}

		faceIndex[0] = bestIndex;
		return bestDistance;
	}

	public static void findIncidentFace( Vector[] v, Polygon RefPoly, Polygon IncPoly, int referenceIndex )
	{
		Vector referenceNormal = RefPoly.normals[referenceIndex];

		// Calculate normal in incident's frame of reference
		// referenceNormal = RefPoly->u * referenceNormal; // To world space
		// referenceNormal = IncPoly->u.Transpose( ) * referenceNormal; // To
		// incident's model space
		referenceNormal = RefPoly.u.multiply( referenceNormal ); // To world space
		referenceNormal = IncPoly.u.transpose().multiply( referenceNormal ); // To
																								// incident's
																								// model
																								// space

		// Find most anti-normal face on incident polygon
		int incidentFace = 0;
		double minDot = Float.MAX_VALUE;
		for (int i = 0; i < IncPoly.numVerticies; ++i)
		{
			// real dot = Dot( referenceNormal, IncPoly->m_normals[i] );
			double dot = referenceNormal.dot(IncPoly.normals[i]);

			if (dot < minDot)
			{
				minDot = dot;
				incidentFace = i;
			}
		}

		// Assign face vertices for incidentFace
		// v[0] = IncPoly->u * IncPoly->m_vertices[incidentFace] +
		// IncPoly->body->position;
		// incidentFace = incidentFace + 1 >= (int32)IncPoly->m_numVerticies ? 0 :
		// incidentFace + 1;
		// v[1] = IncPoly->u * IncPoly->m_vertices[incidentFace] +
		// IncPoly->body->position;

		v[0] = IncPoly.u.multiply( IncPoly.verticies[incidentFace] ).addi( IncPoly.body.getPosition() );
		incidentFace = incidentFace + 1 >= (int)IncPoly.numVerticies ? 0 : incidentFace + 1;
		v[1] = IncPoly.u.multiply( IncPoly.verticies[incidentFace] ).addi( IncPoly.body.getPosition() );
	}

	public static int clip( Vector n, double c, Vector[] face )
	{
		int sp = 0;
		Vector[] out = {
			new Vector( face[0] ),
			new Vector( face[1] )
		};

		// Retrieve distances from each endpoint to the line
		// d = ax + by - c
		// real d1 = Dot( n, face[0] ) - c;
		// real d2 = Dot( n, face[1] ) - c;
		double d1 = (n.dot(face[0])) - c;
		double d2 = (n.dot(face[1])) - c;

		// If negative (behind plane) clip
		// if(d1 <= 0.0f) out[sp++] = face[0];
		// if(d2 <= 0.0f) out[sp++] = face[1];
		if (d1 <= 0.0f) out[sp++].set( face[0] );
		if (d2 <= 0.0f) out[sp++].set( face[1] );

		// If the points are on different sides of the plane
		if (d1 * d2 < 0.0f) // less than to ignore -0.0f
		{
			// Push intersection point
			// real alpha = d1 / (d1 - d2);
			// out[sp] = face[0] + alpha * (face[1] - face[0]);
			// ++sp;

			double alpha = d1 / (d1 - d2);

			out[sp++].set( face[1] ).subtracti( face[0] ).multiplyi( alpha ).addi( face[0] );
		}

		// Assign our new converted values
		face[0] = out[0];
		face[1] = out[1];

		// assert( sp != 3 );

		return sp;
	}
}
