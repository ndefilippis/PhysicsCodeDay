import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class MenuBar {
	private static JMenuBar menuBar;
	private static JMenu menu, submenu;
	private static JMenuItem menuItem;
	private static JRadioButtonMenuItem rbMenuItem;
	private static JCheckBoxMenuItem cbMenuItem;
	private static Frame frame;
	
	public static void createMenuBar(Frame frame){
		menuBar = new JMenuBar();
		createFileMenu();
		createWorldMenu();
		createSimMenu();
		MenuBar.frame = frame;
		frame.setJMenuBar(menuBar);
	}
	private static void createFileMenu() {
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
	private static void createSimMenu(){
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

	private static void createWorldMenu() {
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
				Frame.mouseHandler.canAdd = true;
				Frame.mouseHandler.toAdd = new Block(0, 0, 1, 1);
			}
		});
		submenu.add(menuItem);

		menuItem = new JMenuItem("Wall");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.SHIFT_MASK));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame.mouseHandler.canAdd = true;
				Frame.mouseHandler.toAdd = new Wall(0, 0, 3, 1);
			}
		});
		submenu.add(menuItem);
		menuItem = new JMenuItem("Ramp");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame.mouseHandler.canAdd = true;
				Frame.mouseHandler.toAdd = new Ramp(0, 0, 5, 5, true);
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
				frame.popupGravity();
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("Restitution", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.popupRestitution();
			}
		});
		menu.add(menuItem);
		
		menuBar.add(menu);
	}
}
