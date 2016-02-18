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

@SuppressWarnings("serial")
public class Frame extends JFrame {
	
	public static MouseHandler mouseHandler;
	public static KeyHandler keyHandler;
	public static MouseMotionHandler motionHandler;
	public static ScrollHandler scrollHandler;

	public Frame(Panel panel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Physics");
		MenuBar.createMenuBar(this);
		getContentPane().add(panel);
		mouseHandler = new MouseHandler();
		keyHandler = new KeyHandler();
		motionHandler = new MouseMotionHandler();
		scrollHandler = new ScrollHandler();
		this.addMouseListener(mouseHandler);
		this.addKeyListener(keyHandler);
		this.addMouseMotionListener(motionHandler);
		this.addMouseWheelListener(scrollHandler);
	}

	public void display() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}
	

	public void popupRestitution() {
		JTextField restitution = new JTextField(5);
		JPanel pan = new JPanel();
		pan.add(new JLabel("Restiution"));
		pan.add(Box.createHorizontalStrut(5));
		pan.add(restitution);
		int result = JOptionPane.showConfirmDialog(null, pan, "Enter restiution coefficient", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			try{
				double rest = Double.parseDouble(restitution.getText());
				World.energyConserved = rest;
			}
			catch(NumberFormatException e){
				
			}
		}
	}

	public void popupGravity() {
		JTextField gravity = new JTextField(5);
		JPanel pan = new JPanel();
		pan.add(new JLabel("Gravity"));
		pan.add(Box.createHorizontalStrut(5));
		pan.add(gravity);
		int result = JOptionPane.showConfirmDialog(null, pan, "Enter gravity", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			try{
				double grav = Double.parseDouble(gravity.getText());
				World.gravity = new Vector(0, grav);
			}
			catch(NumberFormatException e){
				
			}
		}
	}

	
	
}
