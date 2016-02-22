package physicsday.view;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import physicsday.controller.KeyHandler;
import physicsday.controller.MouseHandler;
import physicsday.controller.MouseMotionHandler;
import physicsday.controller.ScrollHandler;
import physicsday.model.World;
import physicsday.util.Vector;

@SuppressWarnings("serial")
public class PhysicsFrame extends JFrame {

	public PhysicsFrame(PhysicsPanel panel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Physics");
		getContentPane().add(panel);
	}

	public void display() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}
	

	public void popupRestitution(World world) {
		JTextField restitution = new JTextField(5);
		JPanel pan = new JPanel();
		pan.add(new JLabel("Restiution"));
		pan.add(Box.createHorizontalStrut(5));
		pan.add(restitution);
		int result = JOptionPane.showConfirmDialog(null, pan, "Enter restiution coefficient", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			try{
				double rest = Double.parseDouble(restitution.getText());
			}
			catch(NumberFormatException e){
				
			}
		}
	}

	public void popupGravity(World world) {
		JTextField gravity = new JTextField(5);
		JPanel pan = new JPanel();
		pan.add(new JLabel("Gravity"));
		pan.add(Box.createHorizontalStrut(5));
		pan.add(gravity);
		int result = JOptionPane.showConfirmDialog(null, pan, "Enter gravity", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			try{
				double grav = Double.parseDouble(gravity.getText());
				world.gravity = new Vector(0, grav);
			}
			catch(NumberFormatException e){
				
			}
		}
	}

	
	
}