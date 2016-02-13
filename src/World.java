import java.util.ArrayList;

public class World {
	public static ArrayList<Shape> objects = new ArrayList<Shape>();
	
	public void add(Shape s){
		objects.add(s);
	}
}
