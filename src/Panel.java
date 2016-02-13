import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Panel extends JPanel{
	public Panel(){
		setPreferredSize(new Dimension(300, 300));
	}

	public void paintComponent(Graphics g){
		g.fillRect(0, 0, 50, 50);
	}
}
