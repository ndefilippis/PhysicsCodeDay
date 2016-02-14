import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
	public boolean canAdd;
	public Shape toAdd;
	@Override
	public void mouseClicked(MouseEvent e) {
		if(Frame.mouseHandler.canAdd){
			World.objects.add(Frame.mouseHandler.toAdd);
			Frame.mouseHandler.canAdd = false;
			return;
		}
		else{
			Shape s;
			if((s = World.getShapeAt(e.getX(), e.getY()-45)) != null){
				if(s.equals(Panel.selectedItem)){
					Panel.popupDialogMenu();
				}
				Panel.selectedItem = s;
				return;
			}
			
		}
		Panel.selectedItem = null;
		Panel.itemInfo.setOpaque(false);
		Panel.itemInfo.setText("");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
