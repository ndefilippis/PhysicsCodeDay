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

	@Override
	public void paintComponent(Graphics g){
		itemInfo.setBounds(0, getHeight()-30, getWidth(), 30);
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
