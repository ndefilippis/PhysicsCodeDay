package physicsday.view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import physicsday.PhysicsDay;
import physicsday.model.Block;
import physicsday.model.Body;
import physicsday.model.CircleShape;
import physicsday.model.Wall;
import physicsday.model.World;

public class MenuBar {
	private static JMenuBar menuBar;
	private static JMenu menu, submenu;
	private static JMenuItem menuItem;
	private static JRadioButtonMenuItem rbMenuItem;
	private static JCheckBoxMenuItem cbMenuItem;
	private static JFrame frame;
	private static World world;
	private static PhysicsScreen view;
	
	public static void createMenuBar(JFrame window, World world, PhysicsScreen screen){
		menuBar = new JMenuBar();
		createFileMenu();
		createWorldMenu();
		createSimMenu();
		MenuBar.frame = window;
		MenuBar.world = world;
		MenuBar.view = screen;
		window.setJMenuBar(menuBar);
	}
	private static JMenuItem addMenuItem(JMenu menu, String name, int alt, KeyStroke accelerator, String context){
		menuItem = new JMenuItem(name, alt);
		menuItem.setAccelerator(accelerator);
		menuItem.getAccessibleContext().setAccessibleDescription(context);
		menu.add(menuItem);
		return menuItem;
	}
	private static void createFileMenu() {
		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("A File menu");
		menuBar.add(menu);
		
		menuItem = addMenuItem(menu, "New", KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK), "Clears the project");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				world.clear();
			}
		});
		
		menuItem = addMenuItem(menu, "Toggle Gridlines", KeyEvent.VK_G, KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK), "Toggles girlines");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//view.toggleGridlines();
			}
		});
		
		menuItem = addMenuItem(menu, "Save", KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK), "Saves the world");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PhysicsFileHandler.saveWorld(world);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		menuItem = addMenuItem(menu, "Open", KeyEvent.VK_L, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK), "Loads a world" );
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//try {
				//world.loadWorld();
				//} catch (IOException e1) {
				//	e1.printStackTrace();
				//}
			}
		});
	}
	private static void createSimMenu(){
		
		menu = new JMenu("Simulation");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("Sets world properties");
		
		menuItem = addMenuItem(menu, "Reset", KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK), "Resets the world");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					world.resetState();
			}
		});
		
		menuItem = addMenuItem(menu, "Resume/Pause", KeyEvent.VK_SPACE, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.CTRL_MASK), "Stops and starts the simulation");	
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//PhysicsDay.toggleRunning();
			}
		});

		menuBar.add(menu);
	}
	private static void createWorldMenu() {
		// Build second menu in the menu bar.
		menu = new JMenu("World");
		menu.setMnemonic(KeyEvent.VK_W);
		menu.getAccessibleContext().setAccessibleDescription("Sets world properties");
		submenu = new JMenu("Add");
		submenu.setMnemonic(KeyEvent.VK_S);
		
		menuItem = addMenuItem(submenu, "Block", KeyEvent.VK_B, KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.SHIFT_MASK), "Adds block");;
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PhysicsDay.bodyToAdd = new Block(0, 0, 1, 1);
			}
		});

		menuItem = addMenuItem(submenu, "Wall", KeyEvent.VK_W, KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.SHIFT_MASK), "Adds Wall");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PhysicsDay.bodyToAdd = new Wall(0, 0, 2, 1);
			}
		});

		menuItem = addMenuItem(submenu, "Circle", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.SHIFT_MASK), "Adds Circle");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PhysicsDay.bodyToAdd = new Body(new CircleShape(1), 0, 0);
			}
		});

		menu.add(submenu);
		
		menuItem = addMenuItem(menu, "Gravity", KeyEvent.VK_G, KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK), "Changes Gravity");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//frame.popupGravity(world);
			}
		});
		menuBar.add(menu);
	}
}
