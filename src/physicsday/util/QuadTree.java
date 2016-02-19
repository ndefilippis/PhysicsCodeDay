package physicsday.util;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import physicsday.model.Shape;

public class QuadTree{
	private Node overallRoot;
	private int MAX_OBJECTS = 3;
	
	public QuadTree(Rectangle2D bounds){
		overallRoot = new Node(bounds);
	}
	
	private class Node {
		private Node[] nodes;
		private Rectangle2D bounds;
		private ArrayList<Shape> objects;
		
		Node(Rectangle2D bounds){
			this.bounds = bounds;
			objects = new ArrayList<Shape>();
			nodes = new Node[4];
		}
		private int getIndex(Shape shape){
			int index = -1;
			double xMid = bounds.getX() + bounds.getWidth()/2;
			double yMid = bounds.getY() + bounds.getHeight()/2;
			
			boolean top = (shape.drawY() < yMid && shape.drawY() + shape.drawHeight() < yMid);
			boolean bottom = shape.drawY() > yMid;
			
			if(shape.drawX() < xMid && shape.drawX() + shape.drawWidth() < xMid){
				if(top){
					index = 1;
				} else if (bottom){
					index = 2;
				}
			}
			else if(shape.drawX() > xMid){
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
		private void insert(Shape shape){
			if(nodes[0] != null){
				int index = getIndex(shape);
				
				if(index != -1){
					nodes[index].insert(shape);
					return;
				}
			}
			
			objects.add(shape);
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
		private ArrayList<Shape> retrieve(Shape s, ArrayList<Shape> list) {
			ArrayList<Shape> _ret = new ArrayList<Shape>();
			int index = getIndex(s);
			if(nodes[0] != null){
				if(index != -1){
					nodes[index].retrieve(s, list);
				}
				else{
					for(int i = 0; i < 4; i++){
						list.addAll(nodes[i].retrieve(s, list));
					}
				}
			}
			list.addAll(objects);
			
			return list;
		}
	}
	
	public void clear(){
		this.overallRoot.clear();
	}
	public void insert(Shape shape){
		overallRoot.insert(shape);
	}	
	public ArrayList<Shape> retreive(Shape s){
		return overallRoot.retrieve(s, new ArrayList<Shape>());
	}
	
	
}
