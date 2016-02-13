import java.awt.Dimension;

import javax.swing.JPanel;

import com.sun.prism.Graphics;

public class Panel extends JPanel{
	public Panel(){
		setPreferredSize(new Dimension(300, 300));
		setLayout(null);
	}

	public void paintComponent(Graphics g){
		g.fillRect(0, 0, 50, 50);
	}
}
