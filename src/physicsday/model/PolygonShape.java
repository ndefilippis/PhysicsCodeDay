package physicsday.model;

import java.awt.Graphics;
import java.awt.geom.Area;

import physicsday.util.BoundingBox;
import physicsday.util.Mat22;
import physicsday.util.Vector;

public class PolygonShape extends Shape{
	public int numVerticies;
	public Vector[] verticies;
	public Vector[] normals;

	public PolygonShape(){
		numVerticies = 0;
		verticies = new Vector[0];
		normals = new Vector[0];
	}

	public PolygonShape(Vector[] verts, int count){
		verticies = new Vector[count];
		normals = new Vector[count];
		assert(count > 2);
		int right = 0;
		double highestX = verts[0].x;
		for(int i = 1; i < count; i++){
			if(verts[i].x > highestX){
				right = i;
				highestX = verts[i].x;
			}
			else if(verts[i].x == highestX){
				if(verts[i].y < verts[right].y){
					right = i;
				}
			}
		}
		int[] hull = new int[1000];
		int outCount = 0;
		int hullIndex = right;
		while(true){
			hull[outCount] = hullIndex;
			int nextHull = 0;
			for(int i = 1; i < count; i++){
				if(nextHull == hullIndex){
					nextHull = i;
					continue;
				}
				Vector e1 = verts[nextHull].subtract(verts[hull[outCount]]);
	        	Vector e2 = verts[i].subtract(verts[hull[outCount]]);
	        	double c = e1.Cross(e2);
	        	if(c < 0.0f){
	          		nextHull = i;
	        	}
	        	if(c == 0.0f && e2.lengthSquared() > e1.lengthSquared()){
	          		nextHull = i;
	        	}
	      	}
	     	 ++outCount;
	      	hullIndex = nextHull;
	     	if(nextHull == right){
	       		numVerticies = outCount;
	        	break;
	      	}
		}
		for(int i = 0; i < numVerticies; i++){
			this.verticies[i] = verts[hull[i]];
		}
		for(int i = 0; i < numVerticies; i++){
			int i2 = i + 1 < numVerticies ? i + 1 : 0;
		     Vector face = this.verticies[i2].subtract(this.verticies[i]);
		     this.normals[i] = new Vector( face.y, -face.x );
		     this.normals[i] = this.normals[i].normalize( );
		}
	}
	
	public void init(){
		Vector center = new Vector(0, 0);
		double area = 0;
		double I = 0;
		for(int i = 0; i < numVerticies; i++){
			Vector p1 = verticies[i];
			Vector p2 = verticies[(i+1) % numVerticies];
			double d = p1.Cross(p2);
			double tArea = 0.5*d;
			area += tArea;
			double weight = tArea / 3.0;
			center.addsi(p1, weight);
			center.addsi(p2, weight);
			
			double x2 = p1.x * p1.x + p2.x * p1.x + p2.x * p2.x;
			double y2 = p1.y * p1.y + p2.y * p1.y + p2.y * p2.y;
			
			I += (0.25f / 3 * d) * (x2 + y2);
		}
		
		center.multiplyi(1.0/area);
		for(int i = 0; i < numVerticies; i++){
			verticies[i].subtracti(center);
		}
		body.setMass(area);
		body.setInertia(I);
	}
	
	public static PolygonShape createBox(double width, double height){
		PolygonShape p = new PolygonShape();
		p.numVerticies = 4;
		p.verticies = new Vector[4];
		p.normals = new Vector[4];
		double hw = width/2;
		double hh = height/2;
		p.verticies[0] = new Vector(-hw, -hh);
		p.verticies[1] = new Vector( hw, -hh);
		p.verticies[2] = new Vector( hw,  hh);
		p.verticies[3] = new Vector(-hw,  hh);
		p.normals[0] = new Vector(0, -1);
		p.normals[1] = new Vector(1,  0);
		p.normals[2] = new Vector(0,  1);
		p.normals[3] = new Vector(-1, 0);
		return p;
	} 

	@Override
	public Shape copy() {
		PolygonShape p = new PolygonShape();
		p.numVerticies = numVerticies;
		p.verticies = new Vector[numVerticies];
		p.normals = new Vector[numVerticies];
		p.u = u;
		for(int i = 0; i < numVerticies; i++){
			p.verticies[i] = verticies[i];
			p.normals[i] = normals[i];	
		}
		return p;
	}

	@Override
	public BoundingBox boundingBox() {
		Vector min, max;
		min = new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		max = new Vector(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
		for(int i = 0; i < numVerticies; i++){
			Vector vertex = verticies[i];
			if(vertex.x < min.x) min.x = vertex.x;
			if(vertex.y < min.y) min.y = vertex.y;
			if(vertex.x > max.x) max.x = vertex.x;
			if(vertex.y > max.y) max.y = vertex.y;
		}
		return new BoundingBox(min, max);
	}

	public Vector getSupport(Vector direction){
		double bestDistance = -Double.MAX_VALUE;
		Vector bestVector = null;
		for(int i = 0; i < numVerticies; i++){
			Vector v = verticies[i];
			double project = v.dot(direction);
			if(project > bestDistance){
				bestDistance = project;
				bestVector = v;
			}
		}
		return bestVector;
	}

	@Override
	public void resize(Vector vec) {
		Vector rv = vec.subtract(body.getPosition());
		for(int i = 0; i < numVerticies; i++){
			verticies[i].set(verticies[i].normalize().multiply(Math.min(Math.abs(rv.x)+1, Math.abs(rv.y)+1)));
		}
		if(body.getInvMass() != 0)
		init();
	}	
}
