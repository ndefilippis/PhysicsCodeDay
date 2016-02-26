package physicsday.util;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import physicsday.model.Body;

public class QuadTree{
	private Node overallRoot;
	private int MAX_OBJECTS = 3;
	
	public QuadTree(Rectangle2D bounds){
		overallRoot = new Node(bounds);
	}
	
	private class Node {
		private Node[] nodes;
		private Rectangle2D bounds;
		private ArrayList<Body> objects;
		
		Node(Rectangle2D bounds){
			this.bounds = bounds;
			objects = new ArrayList<Body>();
			nodes = new Node[4];
		}
		private int getIndex(Body body){
			int index = -1;
			double xMid = bounds.getX() + bounds.getWidth()/2;
			double yMid = bounds.getY() + bounds.getHeight()/2;
			BoundingBox rect = body.shape.boundingBox();
			boolean top = (rect.y() < yMid && rect.y() + rect.height() < yMid);
			boolean bottom = rect.y() > yMid;
			
			if(rect.x() < xMid && rect.x() + rect.width() < xMid){
				if(top){
					index = 1;
				} else if (bottom){
					index = 2;
				}
			}
			else if(rect.x() > xMid){
				if(top){
					index = 0;
				} else if (bottom){
					index = 3;
				}
			}
			return index;
		}
		private void split(){
			double hWidth = bounds.getWidth()/2;
			double hHeight = bounds.getHeight()/2;
			double x = bounds.getX();
			double y = bounds.getY();
			
			nodes[0] = new Node(new Rectangle2D.Double(x+hWidth, y, hWidth, hHeight));
			nodes[1] = new Node(new Rectangle2D.Double(x, y, hWidth, hHeight));
			nodes[2] = new Node(new Rectangle2D.Double(x, y+hHeight, hWidth, hHeight));
			nodes[3] = new Node(new Rectangle2D.Double(x+hWidth, y+hHeight, hWidth, hHeight));
		}
		private void clear(){
			objects.clear();
			for(int i = 0; i < 4; i++){
				if(nodes[i] != null){
					nodes[i].clear();
				}
			}
		}
		private void insert(Body body){
			if(nodes[0] != null){
				int index = getIndex(body);
				
				if(index != -1){
					nodes[index].insert(body);
					return;
				}
			}
			
			objects.add(body);
			if(objects.size() > MAX_OBJECTS){
				if(nodes[0] == null){
					split();
				}
				int i = 0;
				while(i < objects.size()){
					int index = getIndex(objects.get(i));
					if(index != -1){
						nodes[index].insert(objects.get(i));
						objects.remove(i);
					}
					else{
						i++;
					}
				}
			}
		}
		private ArrayList<Body> retrieve(Body b, ArrayList<Body> list) {
			int index = getIndex(b);
			if(nodes[0] != null){
				if(index != -1){
					nodes[index].retrieve(b, list);
				}
				else{
					for(int i = 0; i < 4; i++){
						list.addAll(nodes[i].retrieve(b, list));
					}
				}
			}
			list.addAll(objects);
			
			return list;
		}
		public void remove(Body b) {
			for(int i = objects.size()-1; i >= 0; i--){
				if(objects.get(i).equals(b)){
					objects.remove(i);
					return;
				}
			}
			for(Node n : nodes){
				if(n != null){
					n.remove(b);
				}
			}
		}
	}
	
	public void remove(Body b){
		this.overallRoot.remove(b);
	}
	
	public void clear(){
		this.overallRoot.clear();
	}
	public void insert(Body body){
		overallRoot.insert(body);
	}	
	public ArrayList<Body> retreive(Body b){
		return overallRoot.retrieve(b, new ArrayList<Body>());
	}
	
	
}
