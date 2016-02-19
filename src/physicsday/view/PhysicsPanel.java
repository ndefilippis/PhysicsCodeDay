package physicsday.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MouseInfo;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import physicsday.model.Block;
import physicsday.model.Ramp;
import physicsday.model.Shape;
import physicsday.model.World;
import physicsday.util.Vector;


@SuppressWarnings("serial")
public class PhysicsPanel extends JPanel{
	
	public static JLabel itemInfo;
	public static final int width = 1200;
	public static final int height = 650;
	public static boolean gridlines = true;
	public static Shape selectedItem;
	public static JLabel label = new JLabel();
	public static double xScale = 25;
	public static double yScale = 25;
	public static double yOffset = 0;
	public static double xOffset = 0;
	public PhysicsPanel(){
		setPreferredSize(new Dimension(width, height));
		setSize(1600, 1200);
		setLayout(null);
		add(PhysicsPanel.label);
		label.setBounds(1, 0, 12*8, 24);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		label.setOpaque(true);
		itemInfo = new JLabel();
		itemInfo.setBounds(0, height-30, width, 30);
		add(itemInfo);
	}

	public int drawGridlines(Graphics g){
		double xEvery = xScale;
		int count = 0;
		
		while(xEvery <= 5){
			xEvery *= (5+Math.pow(2, count/15.0));
			count++;
		}
		double yEvery = yScale;
		count = 0;
		while(yEvery <= 5){
			yEvery *= (5+Math.pow(2, count/15.0));
			count++;
		}
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
			
			g.drawLine((int)(i*xEvery+xOffset%(xEvery*5)), 0, (int)(i*xEvery+xOffset%(xEvery*5)), getHeight());
			if(i % 5 == 0){
				g.fillRect((int)((i-1)*xEvery+xOffset%(xEvery*5)), 0, 3, getHeight());
				g.setColor(Color.GRAY);
			}
		}
		for(int i = 0; i <= getHeight()/yEvery; i++){
			g.drawLine(0, (int)(i*yEvery+yOffset%(yEvery*5)), getWidth(), (int)(i*yEvery+yOffset%(yEvery*5)));
			if(i % 5 == 0){
				g.fillRect(0, (int)((i-1)*yEvery+yOffset%(yEvery*5)), getWidth(), 3);
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
		for(Shape s : World.getObjectsInView(xOffset, yOffset, width, height)){
			s.draw(g);
		}
		if(PhysicsFrame.mouseHandler.canAdd){
			int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			PhysicsFrame.mouseHandler.toAdd.setPosition(new Vector((mouseX-this.getLocationOnScreen().x-xOffset)/xScale-PhysicsFrame.mouseHandler.toAdd.getWidth()/2, (mouseY-this.getLocationOnScreen().y-yOffset)/yScale-PhysicsFrame.mouseHandler.toAdd.getHeight()/2));
			Color veil = new Color(255, 255, 255, 200);
			if(!World.canAddAtLocation(PhysicsFrame.mouseHandler.toAdd)){
				veil = new Color(255, 0, 0, 200);
			}
			PhysicsFrame.mouseHandler.toAdd.draw(g);
			g.setColor(veil);
			g.fillPolygon(PhysicsFrame.mouseHandler.toAdd.getOutline());
		}
		if(selectedItem != null){
			PhysicsPanel.itemInfo.setOpaque(true);
			PhysicsPanel.itemInfo.setBackground(Color.WHITE);
			PhysicsPanel.itemInfo.setText(selectedItem.toString());
			g.setColor(new Color(255, 255, 255, 100));
			g.fillPolygon(selectedItem.getOutline());
			g.setColor(Color.RED);
			g.drawPolygon(selectedItem.getOutline());
		}
		else{
			PhysicsPanel.itemInfo.setOpaque(false);
			PhysicsPanel.itemInfo.setBackground(Color.WHITE);
			PhysicsPanel.itemInfo.setText("");
		}
	}
	
	public static void resetView(){
		xScale = 25;
		yScale = 25;
		xOffset = 0;
		yOffset = 0;
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
				return;
			}
			try{
				if(s instanceof Ramp && switchSlope.isSelected()){
					((Ramp)s).switchFacing();
				}
				if(s instanceof Block && !mass.getText().isEmpty()){
					try{
						selectedItem.setMass(Double.parseDouble(mass.getText()));
					}
					catch(NumberFormatException e){
						
					}
				}
				double x;
				if(!xField.getText().isEmpty() && !yField.getText().isEmpty()){
					selectedItem.setVelocity(new Vector(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText())));
				}
				if(!wField.getText().isEmpty()){
					selectedItem.setWidth(Integer.parseInt(wField.getText()));
				}
				selectedItem.setHeight(Integer.parseInt(hField.getText()));
				
			}
			catch(NumberFormatException e){
				
			}
		}
	}
}
