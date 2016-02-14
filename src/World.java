import java.awt.Point;
import java.awt.geom.Area;
import java.util.ArrayList;

public class World {
	public static ArrayList<Shape> objects = new ArrayList<Shape>();
	public static double xScale = 25;
	public static double yScale = 25;
	public static Vector gravity = new Vector(0, 9.81);
	public static double energyConserved = .5;
	
	public static void add(Shape s){
		objects.add(s);
	}
	
	public static Shape getShapeAt(int x, int y){
		for(int i = objects.size()-1; i >= 0; i--){
			if(objects.get(i).getOutline().contains(x, y) || objects.get(i).getOutline().contains(new Point(x, y))){
				return objects.get(i);
			}
		}
		return null;
	}
	
	public static void update(double dt){
		for(int i = objects.size()-1; i >= 0; i--){
			objects.get(i).update(dt);
		}
		for(int i = objects.size()-1; i >= 0; i--){
			for(int j = objects.size()-1; j >= 0; j--){
				Shape s1 = objects.get(i);
				Shape s2 = objects.get(j);
				if(s1.equals(s2)) continue;
				Area a1 = s1.getArea();
				a1.intersect(s2.getArea());
				
				if (!a1.isEmpty()){
					Shape.collide(s1, s2);
				}
			}
		}
	}
}
