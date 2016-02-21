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
import physicsday.view.PhysicsPanel;

public class World {
	private ArrayList<Body> objects = new ArrayList<Body>();
	private ArrayList<Body> initState;
	private ArrayList<Manifold> contacts;
	public Vector gravity = new Vector(0, 9.81);
	public double energyConserved = .5;

	public World() {
		contacts = new ArrayList<Manifold>();
	}

	public void add(Body b) {
		assert (b != null);
		objects.add(b);
	}

	public Body getShapeAt(int x, int y, PhysicsPanel p) {
		for (int i = objects.size() - 1; i >= 0; i--) {
			if (objects.get(i).shape.getScreenArea(p).contains(x, y)) {
				return objects.get(i);
			}
		}
		return null;
	}

	public void update(double dt) {
		contacts.clear();
		for (int i = 0; i < objects.size(); i++) {
			Body a = objects.get(i);
			for (int j = i + 1; j < objects.size(); j++) {
				Body b = objects.get(j);
				if (b.getInvMass() == 0 && a.getInvMass() == 0)
					continue;
				Manifold m = new Manifold(a, b);
				m.solve();
				if (m.contactCount > 0) {
					contacts.add(m);
				}
			}
		}
		for (int i = objects.size() - 1; i >= 0; i--) {
			objects.get(i).applyForce(gravity.multiply(objects.get(i).getInvMass()));
			objects.get(i).update(dt);
		}

	}

	public void saveWorld() throws FileNotFoundException {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(PhysicsDay.getFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			PrintWriter out = new PrintWriter(new FileOutputStream(file));
			out.println(".PHY");
			out.println(objects.size());
			for (Body b : objects) {
				out.println(b.saveString());
			}
			out.close();
		}
	}

	public void loadWorld(File file) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		if (in.readLine().startsWith(".PHY")) {
			objects = new ArrayList<Body>();
			int n = Integer.parseInt(in.readLine());
			for (int i = 0; i < n; i++) {
				Body bodyToAdd;
				Shape shapeToAdd = null;
				String[] s = in.readLine().split(":");
				double x = Double.parseDouble(s[1]);
				double y = Double.parseDouble(s[2]);
				double width = Double.parseDouble(s[3]);
				double height = Double.parseDouble(s[4]);
				double velocityx = 0;
				double velocityy = 0;
				
				if (s[0].equals("w")) {
					bodyToAdd = new Wall(x, y, width, height);
					add(bodyToAdd);
					continue;
				}
				if (s[0].equals("r")) {
					boolean positive = Boolean.parseBoolean(s[5]);
					shapeToAdd = new Ramp(width, height, positive);
				}
				if (s[0].equals("b")) {
					velocityx = Double.parseDouble(s[5]);
					velocityy = Double.parseDouble(s[6]);
					shapeToAdd = new Block(width, height);
				}
				bodyToAdd = new Body(shapeToAdd, x, y);
				bodyToAdd.setVelocity(new Vector(velocityx, velocityy));
				add(bodyToAdd);
			}
			in.close();
		} else {
			JOptionPane.showMessageDialog(PhysicsDay.getFrame(), "Unable to open: not a .phy file");
		}
	}

	public void loadWorld() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(PhysicsDay.getFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			loadWorld(file);
		}
		saveState();
	}

	public void loadWorld(String string) throws NumberFormatException, IOException {
		loadWorld(new File(string));
		saveState();
	}

	public void resetState() {
		objects = new ArrayList<Body>();
		for (Body b : initState) {
			objects.add(b.copy());
		}
		PhysicsDay.resetLoop();
	}

	public void saveState() {
		initState = new ArrayList<Body>();
		for (Body b : objects) {
			initState.add(b.copy());
		}
	}

	public boolean canAddAtLocation(Body toAdd, PhysicsPanel panel) {
		for (Body b : objects) {
			Area a1 = b.shape.getScreenArea(panel);
			a1.intersect(toAdd.shape.getScreenArea(panel));
			if (!a1.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Body> getObjectsInView(double xOffset, double yOffset, double width, double height) {
		return objects;
	}

	public void removeObject(Body body) {
		objects.remove(body);
	}

	public void clear() {
		objects = new ArrayList<Body>();
	}
}
