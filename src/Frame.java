import java.awt.EventQueue;
import java.awt.event.ActionEvent;
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


public class Frame extends JFrame{
	
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	
	public Frame(Panel panel){
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
	
	public void createMenu(){
		menuBar = new JMenuBar();
		createFileMenu();
		createWorldMenu();

		setJMenuBar(menuBar);
	}
	public void createWorldMenu(){
		//Build second menu in the menu bar.
			menu = new JMenu("World");
			menu.setMnemonic(KeyEvent.VK_N);
			menu.getAccessibleContext().setAccessibleDescription(
				        "Sets world properties");
			submenu = new JMenu("Add");
			submenu.setMnemonic(KeyEvent.VK_S);

			menuItem = new JMenuItem("Block");
			menuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_B, ActionEvent.ALT_MASK));
			submenu.add(menuItem);

			menuItem = new JMenuItem("Wall");
			submenu.add(menuItem);
			menu.add(submenu);
			menuBar.add(menu);
	}
	public void createFileMenu(){
		//Build the first menu.
				menu = new JMenu("File");
				menu.setMnemonic(KeyEvent.VK_A);
				menu.getAccessibleContext().setAccessibleDescription(
				        "The only menu in this program that has menu items");
				menuBar.add(menu);

				//a group of JMenuItems
				menuItem = new JMenuItem("A text-only menu item",
				                         KeyEvent.VK_T);
				menuItem.setAccelerator(KeyStroke.getKeyStroke(
				        KeyEvent.VK_1, ActionEvent.ALT_MASK));
				menuItem.getAccessibleContext().setAccessibleDescription(
				        "This doesn't really do anything");
				menu.add(menuItem);

				//a group of radio button menu items
				menu.addSeparator();
				ButtonGroup group = new ButtonGroup();
				rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
				rbMenuItem.setSelected(true);
				rbMenuItem.setMnemonic(KeyEvent.VK_R);
				group.add(rbMenuItem);
				menu.add(rbMenuItem);

				rbMenuItem = new JRadioButtonMenuItem("World");
				rbMenuItem.setMnemonic(KeyEvent.VK_W);
				group.add(rbMenuItem);
				menu.add(rbMenuItem);

				//a group of check box menu items
				menu.addSeparator();
				cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
				cbMenuItem.setMnemonic(KeyEvent.VK_C);
				menu.add(cbMenuItem);

				cbMenuItem = new JCheckBoxMenuItem("Another one");
				cbMenuItem.setMnemonic(KeyEvent.VK_H);
				menu.add(cbMenuItem);

				//a submenu
				menu.addSeparator();
				submenu = new JMenu("A submenu");
				submenu.setMnemonic(KeyEvent.VK_S);

				menuItem = new JMenuItem("An item in the submenu");
				menuItem.setAccelerator(KeyStroke.getKeyStroke(
				        KeyEvent.VK_2, ActionEvent.ALT_MASK));
				submenu.add(menuItem);

				menuItem = new JMenuItem("Another item");
				submenu.add(menuItem);
				menu.add(submenu);

	}
}
