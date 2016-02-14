import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class Frame extends JFrame {
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;

	public Frame(Panel panel) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Physics");
		createMenu();
		getContentPane().add(panel);
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

		setJMenuBar(menuBar);
	}

	public void createWorldMenu() {
		// Build second menu in the menu bar.
		menu = new JMenu("World");
		menu.setMnemonic(KeyEvent.VK_N);
		menu.getAccessibleContext().setAccessibleDescription("Sets world properties");
		submenu = new JMenu("Add");
		submenu.setMnemonic(KeyEvent.VK_S);

		menuItem = new JMenuItem("Block");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panel.mouseHandler.canAdd = true;
				Panel.mouseHandler.toAdd = new Block(0, 0, 1, 1);
			}
		});
		submenu.add(menuItem);

		menuItem = new JMenuItem("Wall");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panel.mouseHandler.canAdd = true;
				Panel.mouseHandler.toAdd = new Wall(0, 0, 3, 1);
			}
		});
		submenu.add(menuItem);
		menuItem = new JMenuItem("Ramp");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panel.mouseHandler.canAdd = true;
				Panel.mouseHandler.toAdd = new Ramp(0, 0, 5, 5, true);
			}
		});
		submenu.add(menuItem);
		menu.add(submenu);
		menuBar.add(menu);
	}

	public void createFileMenu() {
		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("Toggle Gridlines", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Panel.gridlines = !Panel.gridlines;
			}
		});
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Resume/Pause", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Main.loop.isRunning){
					Main.pauseLoop();
				}
				else{
					Main.resumeLoop();
				}
			}
		});
		menu.add(menuItem);

	}
}
