import javax.swing.JFrame;

public class Main {
	public static Frame frame;
	public static Panel panel;
	static double time;
	public static Block b = new Block(0, 0, 30, 30);
	
	public static void main(String[] args) {
		panel = new Panel();
		frame = new Frame(panel);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.display();
		panel.repaint();
		b.velocity = new Vector(10, 2);
		World.add(b);
		loop();
	}
	public static void loop() {
		time = System.nanoTime()/1000000000.0;
		while(true) {
			double currTime = System.nanoTime()/1000000000.0;
			for(Shape s : World.objects){
				s.update((currTime - time));
			}
			panel.repaint();
			time = currTime;
		}
	}
}
