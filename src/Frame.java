import java.awt.EventQueue;

import javax.swing.JFrame;


public class Frame extends JFrame{
	public void display() {
		EventQueue.invokeLater(new Runnable() {
		public void run() {
		setVisible(true);
		}
		} );
		}
}
