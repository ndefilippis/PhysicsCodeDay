import java.awt.Point;
import java.awt.geom.Area;
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

	public static void saveWorld() throws FileNotFoundException {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(Main.frame) == JFileChooser.APPROVE_OPTION) {
		  File file = fileChooser.getSelectedFile();
		  PrintWriter out = new PrintWriter(new FileOutputStream(file));
		  out.println(".PHY");
		  out.println(objects.size());
		  for(Shape s : objects){
			  if(s instanceof Wall){
				  out.println("w:"+s.position.x+":"+s.position.y+":"+s.width+":"+s.height);
			  }
			  if(s instanceof Block){
				  out.println("b:"+s.position.x+":"+s.position.y+":"+s.width+":"+s.height+":"+s.velocity.x+":"+s.velocity.y);
			  }
			  if(s instanceof Ramp){
				  out.println("r:"+s.position.x+":"+s.position.y+":"+s.width+":"+s.height+":"+((Ramp)s).positive);
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
					  b.velocity = new Vector(velocityx, velocityy);
					  objects.add(b);
					  
				  }
			  }
			  in.close();
		  }
		  else{
			  JOptionPane.showMessageDialog(Main.frame, "Unable to open: not a .phy file");
		  }
		}

	public static void loadWorld() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(Main.frame) == JFileChooser.APPROVE_OPTION) {
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
		Main.resetLoop();
	}
	
	public static ArrayList<Shape> initState;
	public static void saveState() {
		initState = new ArrayList<Shape>();
		for(Shape s : objects){
			initState.add(s.copy());
		}
	}
}
