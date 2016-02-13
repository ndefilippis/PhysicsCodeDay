import java.awt.geom.Area;
import java.util.ArrayList;

public class World {
	public static ArrayList<Shape> objects = new ArrayList<Shape>();
	public static int xScale = 10;
	public static int yScale = -10;
	
	public static void add(Shape s){
		objects.add(s);
	}
	
	public static void update(double dt){
		for(Shape s : objects){
			s.update(dt);
		}
		for(Shape s1 : objects){
			for(Shape s2 : objects){
				if(s1.equals(s2)) continue;
				Area a = s1.getArea();
			}
		}
	}
}
