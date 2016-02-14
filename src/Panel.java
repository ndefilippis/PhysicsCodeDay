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
		g.setColor(Color.GRAY);
		for(int i = 0; i < getWidth(); i+=World.xScale){
			g.drawLine(i, 0, i, getHeight());
		}
		for(int i = 0; i < getHeight(); i+=World.yScale){
			g.drawLine(0, i, getWidth(), i);
		}
		g.setColor(Color.BLACK);
		for(Shape s : World.objects){
			s.draw(g);
		}
		
		
	}
}
