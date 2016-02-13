import javax.swing.JFrame;

public class Main {
	public static Frame frame;
	public static Panel panel;
	
	public static void main(String[] args) {
		panel = new Panel();
		frame = new Frame(panel);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.display();
		panel.repaint();
	}
	public static void loop() {
		while(true) {
			panel.repaint();
		}
	}
}
