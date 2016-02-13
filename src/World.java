import java.awt.geom.Area;
import java.util.ArrayList;

public class World {
	public static ArrayList<Shape> objects = new ArrayList<Shape>();
	
	public static void add(Shape s){
		objects.add(s);
	}
	
	public static void update(double dt){
		for(Shape s : World.objects){
			s.update(dt);
		}
		for(Shape s1 : objects){
			for(Shape s2 : objects){
				if(s1.equals(s2)) continue;
				Area a1 = s1.getArea();
				a1.intersect(s2.getArea());
				if (!a1.isEmpty()){
					
				}
			}
		}
	}
}
