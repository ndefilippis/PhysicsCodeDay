package physicsday.model;

import java.awt.Graphics;
import java.awt.geom.Area;

import physicsday.util.BoundingBox;
import physicsday.util.Mat22;
import physicsday.util.Vector;
import physicsday.view.PhysicsPanel;

public class Polygon extends Shape{
	int numVerticies;
	Vector[] verticies;
	Vector[] normals;

	public Polygon(){
		numVerticies = 0;
		verticies = new Vector[0];
		normals = new Vector[0];
	}

	public Polygon(Vector[] verticies, int count){
		assert(count > 2);
		int right = 0;
		double highestX = verticies[0].x;
		for(int i = 0; i < count; i++){
			if(verticies[i].x > highestX){
				right = i;
				highestX = verticies[i].x;
			}
			else if(verticies[i].x == highestX){
				if(verticies[i].y < verticies[right].y){
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
				Vector e1 = verticies[nextHull].subtract(verticies[hull[outCount]]);
	        	Vector e2 = verticies[i].subtract(verticies[hull[outCount]]);
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
	      if(nextHull == right)
	      {
	        numVerticies = outCount;
	        break;
	      }
		}
		verticies = new Vector[numVerticies];
		for(int i = 0; i < numVerticies; i++){
			this.verticies[i] = verticies[hull[i]];
		}
		for(int i = 0; i < numVerticies; i++){
			int i2 = i + 1 < numVerticies ? i + 1 : 0;
		     Vector face = this.verticies[i2].subtract(this.verticies[i]);
		     this.normals[i] = new Vector( face.y, -face.x );
		     this.normals[i] = this.normals[i].normalize( );
		}
	}
	
	public static Polygon createBox(double width, double height){
		Polygon p = new Polygon();
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
		Polygon p = new Polygon();
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
		double bestDistance = Double.MIN_VALUE;
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
}
