import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MouseInfo;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


@SuppressWarnings("serial")
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

	public int drawGridlines(Graphics g){
		double xEvery = World.xScale;
		int count = 0;
		
		while(xEvery <= 5){
			xEvery *= 5;
			count++;
		}
		double yEvery = World.yScale;
		count = 0;
		while(yEvery <= 5){
			yEvery *= 5;
			count++;
		}
		xEvery += count*Math.pow(2, count/15.0);
		yEvery += count*Math.pow(2, count/15.0);
		int result = count;
		count = 0;
		while(xEvery >= 50){
			xEvery /= 5;
			count--;
		}
		count = 0;
		while(yEvery >= 50){
			yEvery /= 5;
			count--;
		}
		if(count != 0) result = count;
		g.setColor(Color.GRAY);
		for(int i = 0; i <= getWidth()/xEvery; i++){
			
			g.drawLine((int)(i*xEvery+World.xOffset%(xEvery*5)), 0, (int)(i*xEvery+World.xOffset%(xEvery*5)), getHeight());
			if(i % 5 == 0){
				g.fillRect((int)((i-1)*xEvery+World.xOffset%(xEvery*5)), 0, 3, getHeight());
				g.setColor(Color.GRAY);
			}
		}
		for(int i = 0; i <= getHeight()/yEvery; i++){
			g.drawLine(0, (int)(i*yEvery+World.yOffset%(yEvery*5)), getWidth(), (int)(i*yEvery+World.yOffset%(yEvery*5)));
			if(i % 5 == 0){
				g.fillRect(0, (int)((i-1)*yEvery+World.yOffset%(yEvery*5)), getWidth(), 3);
				g.setColor(Color.GRAY);
			}
		}
		return result;
	}

	@Override
	public void paintComponent(Graphics g){
		itemInfo.setBounds(0, getHeight()-30, getWidth(), 30);
		super.paintComponent(g);
		if(gridlines){
			drawGridlines(g);
		}
		g.setColor(Color.BLACK);
		for(Shape s : World.objects){
			s.draw(g);
		}
		if(Frame.mouseHandler.canAdd){
			int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			Frame.mouseHandler.toAdd.position = new Vector((mouseX-this.getLocationOnScreen().x)/World.xScale-Frame.mouseHandler.toAdd.width/2, (mouseY-this.getLocationOnScreen().y)/World.yScale-Frame.mouseHandler.toAdd.height/2);
			Color veil = new Color(255, 255, 255, 200);
			if(!World.canAddAtLocation(Frame.mouseHandler.toAdd)){
				veil = new Color(255, 0, 0, 200);
			}
			Frame.mouseHandler.toAdd.draw(g);
			g.setColor(veil);
			g.fillPolygon(Frame.mouseHandler.toAdd.getOutline());
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
		else{
			Panel.itemInfo.setOpaque(false);
			Panel.itemInfo.setBackground(Color.WHITE);
			Panel.itemInfo.setText("");
		}
	}

	public static void popupDialogMenu(Shape s) {
		JRadioButton delete = new JRadioButton();
		JRadioButton switchSlope = new JRadioButton();
		JTextField xField = new JTextField(5);
		JTextField yField = new JTextField(5);
		JTextField wField = new JTextField(5);
		JTextField hField = new JTextField(5);
		JTextField mass = new JTextField(5);
		JPanel pan = new JPanel();
		if(s instanceof Ramp || s instanceof Block)
			pan.setLayout(new GridLayout(4,5));
		else{
			pan.setLayout(new GridLayout(3, 5));
		}
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Delete"));
		pan.add(Box.createHorizontalStrut(5));
		pan.add(delete);
		pan.add(Box.createHorizontalStrut(5));
		if(s instanceof Ramp){
			pan.add(Box.createHorizontalStrut(5));
			pan.add(new JLabel("Switch Facing"));
			pan.add(Box.createHorizontalStrut(5));
			pan.add(switchSlope);
			pan.add(Box.createHorizontalStrut(5));
		}
		if(s instanceof Block){
			pan.add(Box.createHorizontalStrut(5));
			pan.add(new JLabel("Mass"));
			pan.add(Box.createHorizontalStrut(5));
			pan.add(mass);
			pan.add(Box.createHorizontalStrut(5));
		}
		pan.add(new JLabel("X Velocity:"));
		pan.add(xField);
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Y Velocity:"));
		pan.add(yField);
		pan.add(new JLabel("Width:"));
		pan.add(wField);
		pan.add(Box.createHorizontalStrut(5));
		pan.add(new JLabel("Height:"));
		pan.add(hField);
		int result = JOptionPane.showConfirmDialog(null, pan, "Enter X and Y values", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION){
			if(delete.isSelected()){
				World.objects.remove(selectedItem);
				selectedItem = null;
				Main.panel.repaint();
				return;
			}
			try{
				if(s instanceof Ramp && switchSlope.isSelected()){
					((Ramp)s).positive = !((Ramp)s).positive;
				}
				if(s instanceof Block && !mass.getText().isEmpty()){
					try{
						selectedItem.mass = Double.parseDouble(mass.getText());
					}
					catch(NumberFormatException e){
						
					}
				}
				double x;
				if(!xField.getText().isEmpty() && !yField.getText().isEmpty()){
					selectedItem.velocity = new Vector(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
				}
				if(!wField.getText().isEmpty()){
					selectedItem.width = Integer.parseInt(wField.getText());
				}
				selectedItem.height = Integer.parseInt(hField.getText());
				
			}
			catch(NumberFormatException e){
				
			}
		}
	}
}
