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
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	public static MouseHandler mouseHandler;
	public static KeyHandler keyHandler;
	public static MouseMotionHandler motionHandler;

	public Frame(Panel panel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Physics");
		createMenu();
		getContentPane().add(panel);
		mouseHandler = new MouseHandler();
		keyHandler = new KeyHandler();
		motionHandler = new MouseMotionHandler();
		this.addMouseListener(mouseHandler);
		this.addKeyListener(keyHandler);
		this.addMouseMotionListener(motionHandler);
	}

	public void display() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}

	public void createMenu() {
		menuBar = new JMenuBar();
		createFileMenu();
		createWorldMenu();
		createSimMenu();

		setJMenuBar(menuBar);
	}
	
	public void createSimMenu(){
		menu = new JMenu("Simulation");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("Sets world properties");
		menuItem = new JMenuItem("Reset", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Resets the world");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					World.resetState();
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Resume/Pause", KeyEvent.VK_SPACE);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Stops and starts the simulation");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Loop.isRunning){
					Main.pauseLoop();
				}
				else{
					Main.resumeLoop();
				}
			}
		});
		menu.add(menuItem);
		menuBar.add(menu);
	}

	public void createWorldMenu() {
		// Build second menu in the menu bar.
		menu = new JMenu("World");
		menu.setMnemonic(KeyEvent.VK_W);
		menu.getAccessibleContext().setAccessibleDescription("Sets world properties");
		submenu = new JMenu("Add");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Block");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.SHIFT_MASK));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mouseHandler.canAdd = true;
				mouseHandler.toAdd = new Block(0, 0, 1, 1);
			}
		});
		submenu.add(menuItem);

		menuItem = new JMenuItem("Wall");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.SHIFT_MASK));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mouseHandler.canAdd = true;
				mouseHandler.toAdd = new Wall(0, 0, 3, 1);
			}
		});
		submenu.add(menuItem);
		menuItem = new JMenuItem("Ramp");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mouseHandler.canAdd = true;
				mouseHandler.toAdd = new Ramp(0, 0, 5, 5, true);
			}
		});
		submenu.add(menuItem);
		menu.add(submenu);
		
		
		menuItem = new JMenuItem("Gravity", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupGravity();
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Restitution", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupRestitution();
			}
		});
		menu.add(menuItem);
		
		menuBar.add(menu);
	}

	protected void popupRestitution() {
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

	protected void popupGravity() {
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

	public void createFileMenu() {
		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("A File menu");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("New", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Clears the project");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				World.objects = new ArrayList<Shape>();
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Toggle Gridlines", KeyEvent.VK_G);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Toggles gridlines");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panel.gridlines = !Panel.gridlines;
			}
		});
		menu.add(menuItem);
		
		
		menu.add(menuItem);
		menuItem = new JMenuItem("Save", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Saves the world"); //lol
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					World.saveWorld();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Open", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Loads a world");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					World.loadWorld();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		menu.add(menuItem);
	}
	
}
