import java.awt.EventQueue;

import javax.swing.JFrame;


public class Frame extends JFrame{
	
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
		
	}
}
