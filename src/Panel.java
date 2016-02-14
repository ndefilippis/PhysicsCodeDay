import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Panel extends JPanel{
	
	public static JLabel itemInfo;
	public static final int width = 1200;
	public static final int height = 650;
	public static boolean gridlines = true;
	public static Shape selectedItem;
	public Panel(){
		setPreferredSize(new Dimension(width, height));
		setSize(1600, 1200);
		setLayout(null);
		
		itemInfo = new JLabel();
		itemInfo.setBounds(0, height-30, width, 30);
		add(itemInfo);
		
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(gridlines){
			g.setColor(Color.GRAY);
			for(int i = 0; i < getWidth(); i+=World.xScale){
				g.drawLine(i, 0, i, getHeight());
			}
			for(int i = 0; i < getHeight(); i+=World.yScale){
				g.drawLine(0, i, getWidth(), i);
			}	
		}
		g.setColor(Color.BLACK);
		for(Shape s : World.objects){
			s.draw(g);
		}
		if(Frame.mouseHandler.canAdd){
			int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			Frame.mouseHandler.toAdd.position = new Vector((mouseX)/World.xScale-Frame.mouseHandler.toAdd.width/2, (mouseY-65)/World.yScale-Frame.mouseHandler.toAdd.height/2);
			g.setColor(new Color(0, 0, 0, 128));
			Frame.mouseHandler.toAdd.draw(g);
			
		}
		if(selectedItem != null){
			Panel.itemInfo.setOpaque(true);
			Panel.itemInfo.setBackground(Color.WHITE);
			Panel.itemInfo.setText(selectedItem.toString());
			g.setColor(new Color(255, 255, 255, 100));
			g.fillPolygon(selectedItem.getOutline());
			g.setColor(Color.RED);
			g.drawPolygon(selectedItem.getOutline());
		}
	}

	public static void popupDialogMenu() {
		JTextField xField = new JTextField(5);
		JTextField yField = new JTextField(5);
		JPanel pan = new JPanel();
		pan.add(new JLabel("X Velocity:"));
		pan.add(xField);
		pan.add(Box.createHorizontalStrut(15));
		pan.add(new JLabel("Y Velocity:"));
		pan.add(yField);
		int result = JOptionPane.showConfirmDialog(null, pan, "Enter X and Y values", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			selectedItem.velocity = new Vector(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
		}
	}
}
