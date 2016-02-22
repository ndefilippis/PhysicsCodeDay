package physicsday.view;

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
import physicsday.model.Block;
import physicsday.model.Body;
import physicsday.model.Ramp;
import physicsday.model.Wall;
import physicsday.util.Vector;

public class PhysicsFileHanlder {
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
				Body bodyToAdd = null;
				String[] s = in.readLine().split(":");
				double x = Double.parseDouble(s[1]);
				double y = Double.parseDouble(s[2]);
				double width = Double.parseDouble(s[3]);
				double height = Double.parseDouble(s[4]);
				double velocityx = 0;
				double velocityy = 0;
				
				if (s[0].equals("w")) {
					bodyToAdd = new Wall(x, y, width, height);
				}
				if (s[0].equals("r")) {
					boolean positive = Boolean.parseBoolean(s[5]);
					bodyToAdd = new Ramp(width, height, positive);
				}
				if (s[0].equals("b")) {
					velocityx = Double.parseDouble(s[5]);
					velocityy = Double.parseDouble(s[6]);
					bodyToAdd = new Block(x, y, width, height);
				}
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
	public void saveState() {
		initState = new ArrayList<Body>();
		for (Body b : objects) {
			initState.add(b.copy());
		}
	}
}
