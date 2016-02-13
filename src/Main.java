import javax.swing.JFrame;

public class Main {
	public static Frame frame;
	public static Panel panel;
	static double time;
	
	public static void main(String[] args) {
		panel = new Panel();
		frame = new Frame(panel);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.display();
		panel.repaint();
	}
	public static void loop() {
		time = System.nanoTime()/1000000000.0;
		while(true) {
			double currTime = System.nanoTime();
			for(Shape s : World.objects){
				s.update((currTime - time)/1000000000.0);
			}
			panel.repaint();
		}
	}
}
