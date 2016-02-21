package physicsday.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import physicsday.controller.Input;
import physicsday.model.Body;
import physicsday.model.Shape;
import physicsday.model.World;
import physicsday.util.Vector;


@SuppressWarnings("serial")
public class PhysicsPanel extends JPanel{
	public JLabel itemInfoLabel;
	public JLabel timeLabel = new JLabel();
	public final int PANEL_WIDTH = 1200;
	public final int PANEL_HEIGHT = 650;
	private boolean gridlines = true;
	public double xScale = 25;
	public double yScale = 25;
	public double yOffset = 0;
	public double xOffset = 0;
	private ArrayList<Body> bodiesToDraw = new ArrayList<Body>();
	private World world;
	
	public PhysicsPanel(World world){
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setSize(1600, 1200);
		setLayout(null);
		timeLabel = new JLabel();
		timeLabel.setBounds(1, 0, 12*8, 24);
		timeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		timeLabel.setOpaque(true);
		this.add(timeLabel);
		itemInfoLabel = new JLabel();
		itemInfoLabel.setBounds(0, PANEL_HEIGHT-30, PANEL_WIDTH, 30);
		this.add(itemInfoLabel);
		this.world = world;
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

	public void render(World w){
		if(Input.selectedItem != null){
			itemInfoLabel.setOpaque(true);
			itemInfoLabel.setBackground(Color.WHITE);
			itemInfoLabel.setText(Input.selectedItem.toString());
			
		}
		else{
			itemInfoLabel.setOpaque(false);
			itemInfoLabel.setBackground(Color.WHITE);
			itemInfoLabel.setText("");
		}
		bodiesToDraw.addAll(w.getObjectsInView(xOffset, yOffset, PANEL_WIDTH, PANEL_HEIGHT));
		repaint();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		itemInfoLabel.setBounds(0, getHeight()-30, getWidth(), 30);
		if(gridlines){
			drawGridlines(g);
		}
		g.setColor(Color.BLACK);
		for(Body body : world.getObjectsInView(0, 0, 0, 0)){
			body.draw(g, this);
		}
		if(Input.selectedItem != null){
			/*int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			Input.toAdd.setPosition(new Vector((mouseX-this.getLocationOnScreen().x-xOffset)/xScale-Input.toAdd.getWidth()/2, (mouseY-this.getLocationOnScreen().y-yOffset)/yScale-PhysicsFrame.mouseHandler.toAdd.getHeight()/2));
			Color veil = new Color(255, 255, 255, 200);
			if(!World.canAddAtLocation(Input.toAdd)){
				veil = new Color(255, 0, 0, 200);
			}
			Input.toAdd.draw(g);
			g.setColor(veil);
			g.fillPolygon(Input.toAdd.shape.getOutline());*/
		}
		/*if(Input.selectedItem != null){
			g.setColor(new Color(255, 255, 255, 100));
			g.fillPolygon(Input.selectedItem.shape.getOutline());
			g.setColor(Color.RED);
			g.drawPolygon(Input.selectedItem.shape.getOutline());
		}*///TODO: add this in the Input
	}
	
	public void resetView(){
		xScale = 25;
		yScale = 25;
		xOffset = 0;
		yOffset = 0;
	}
	public void toggleGridlines(){
		gridlines = !gridlines;
	}
	public void popupDialogMenu(Shape s) {
		/*JRadioButton delete = new JRadioButton();
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
				World.removeObject(Input.selectedItem);
				Input.selectedItem = null;
				return;
			}
			try{
				if(s instanceof Ramp && switchSlope.isSelected()){
					((Ramp)s).switchFacing();
				}
				if(s instanceof Block && !mass.getText().isEmpty()){
					try{
						Input.selectedItem.setMass(Double.parseDouble(mass.getText()));
					}
					catch(NumberFormatException e){
						
					}
				}
				double x;
				if(!xField.getText().isEmpty() && !yField.getText().isEmpty()){
					Input.selectedItem.setVelocity(new Vector(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText())));
				}				
			}
			catch(NumberFormatException e){
				
			}
		}*/
	}
	public Vector toScreenCoords(Vector pos){
		double x = pos.x*xScale + xOffset;
		double y = pos.y*yScale + yOffset;
		return new Vector(x, y);
	}
	public Vector toScreenSize(Vector size){
		double width = size.x*xScale;
		double height = size.y*yScale;
		return new Vector(width, height);
	}
	
}
