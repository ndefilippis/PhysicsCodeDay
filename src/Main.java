import javax.swing.JFrame;

public class Main {
	public static Frame frame;
	public static Panel panel;
	
	public static void main(String[] args) {
		frame = new Frame();
		panel = new Panel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.display();
		panel.repaint();
	}

}
