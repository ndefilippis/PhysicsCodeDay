package physicsday.model;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import physicsday.PhysicsDay;
import physicsday.util.QuadTree;
import physicsday.util.Vector;

public class World {
	public static ArrayList<Shape> objects = new ArrayList<Shape>();
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
	
	public static boolean isColliding(Shape s){
		for(int i = 0; i < objects.size(); i++){
			if(objects.get(i).equals(s)) continue;
			Area a1 = objects.get(i).getArea();
			a1.intersect(s.getArea());
			if(!a1.isEmpty()) return true;
		}
		return false;
	}
	
	public static void update(double dt){
		for(int i = objects.size()-1; i >= 0; i--){
			objects.get(i).update(dt);
		}
		ArrayList<Shape> nearMe = new ArrayList<Shape>();
		for(int i = 0; i < objects.size(); i++){
			for(int j = i+1; j < objects.size(); j++){
				Shape s1 = objects.get(i);
				Shape s2 = objects.get(j);
				Area a1 = s1.getArea();
				a1.intersect(s2.getArea());
				if (!a1.isEmpty()){
					if(s1 instanceof AABB && s2 instanceof AABB){
						Collision c = Collision.AABBtoAABB((AABB)s1, (AABB)s2);
						if(c != null)
							Shape.collide(c);
						continue;
					}
				}
			}
		}
		
	}

	public static void saveWorld() throws FileNotFoundException {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(PhysicsDay.getFrame()) == JFileChooser.APPROVE_OPTION) {
		  File file = fileChooser.getSelectedFile();
		  PrintWriter out = new PrintWriter(new FileOutputStream(file));
		  out.println(".PHY");
		  out.println(objects.size());
		  for(Shape s : objects){
			  if(s instanceof Wall){
				  out.println("w:"+s.currState.position.x+":"+s.currState.position.y+":"+s.getWidth()+":"+s.getHeight());
			  }
			  if(s instanceof Block){
				  out.println("b:"+s.currState.position.x+":"+s.currState.position.y+":"+s.getWidth()+":"+s.getHeight()+":"+s.currState.velocity.x+":"+s.currState.velocity.y);
			  }
			  if(s instanceof Ramp){
				  out.println("r:"+s.currState.position.x+":"+s.currState.position.y+":"+s.getWidth()+":"+s.getHeight()+":"+((Ramp)s).positive);
			  }
		  }
		  out.close();
		}
	}
	
	public static void loadWorld(File file) throws NumberFormatException, IOException{
		BufferedReader in = new BufferedReader(new FileReader(file));
		  if(in.readLine().startsWith(".PHY")){
			  objects = new ArrayList<Shape>();
			  int n = Integer.parseInt(in.readLine());
			  for(int i = 0; i < n; i++){
				  String[] s = in.readLine().split(":");
				  double x = Double.parseDouble(s[1]);
				  double y = Double.parseDouble(s[2]);
				  double width = Double.parseDouble(s[3]);
				  double height = Double.parseDouble(s[4]);
				  if(s[0].equals("w")){
					  objects.add(new Wall(x, y, width, height));
				  }
				  if(s[0].equals("r")){
					  boolean positive = Boolean.parseBoolean(s[5]);
					  objects.add(new Ramp(x, y, width, height, positive));
				  }
				  if(s[0].equals("b")){
					  double velocityx = Double.parseDouble(s[5]);
					  double velocityy = Double.parseDouble(s[6]);
					  Block b = new Block(x, y, width, height);
					  b.currState.velocity = new Vector(velocityx, velocityy);
					  objects.add(b);
					  
				  }
			  }
			  in.close();
		  }
		  else{
			  JOptionPane.showMessageDialog(PhysicsDay.getFrame(), "Unable to open: not a .phy file");
		  }
		}

	public static void loadWorld() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(PhysicsDay.getFrame()) == JFileChooser.APPROVE_OPTION) {
		  File file = fileChooser.getSelectedFile();
		  loadWorld(file);
		}
		saveState();
	}

	public static void loadWorld(String string) throws NumberFormatException, IOException {
		loadWorld(new File(string));
		saveState();
	}
	
	public static void resetState(){
		objects = new ArrayList<Shape>();
		for(Shape s : initState){
			objects.add(s.copy());
		}
		PhysicsDay.resetLoop();
	}
	
	public static ArrayList<Shape> initState;
	
	public static void saveState() {
		initState = new ArrayList<Shape>();
		for(Shape s : objects){
			initState.add(s.copy());
		}
	}

	public static boolean canAddAtLocation(Shape toAdd) {
		for(Shape s : objects){
			Area a1 = s.getArea();
			a1.intersect(toAdd.getArea());
			if(!a1.isEmpty()){
				return false;
			}
		}
		return true;
	}

	public static ArrayList<Shape> getObjectsInView(double xOffset, double yOffset, double width, double height) {
		return objects;
	}
}
