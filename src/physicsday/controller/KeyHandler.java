package physicsday.controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import physicsday.PhysicsDay;
import physicsday.model.World;
import physicsday.view.PhysicsFrame;
import physicsday.view.PhysicsPanel;

public class KeyHandler implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			PhysicsDay.toggleRunning();
		}
		if(e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if(PhysicsPanel.selectedItem != null){
				World.objects.remove(PhysicsPanel.selectedItem);
				PhysicsPanel.selectedItem = null;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_R){
			World.resetState();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			PhysicsFrame.mouseHandler.canAdd = false;
			PhysicsFrame.mouseHandler.toAdd = null;
		}
		if(e.getKeyCode() == KeyEvent.VK_V){
			PhysicsPanel.resetView();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
