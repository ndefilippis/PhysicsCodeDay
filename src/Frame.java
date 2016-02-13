import java.awt.EventQueue;

import javax.swing.JFrame;


public class Frame extends JFrame{
	
	public Frame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Physics");
	}
	
	public void display() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}
}
