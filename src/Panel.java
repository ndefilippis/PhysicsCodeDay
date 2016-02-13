import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Panel extends JPanel{
	
	public Panel(){
		setPreferredSize(new Dimension(1600, 1200));
		setSize(1600, 1200);
		setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(Shape s : World.objects){
			s.draw(g);
		}
	}
}
