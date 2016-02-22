package physicsday.controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import physicsday.PhysicsDay;
import physicsday.model.World;
import physicsday.view.PhysicsFrame;
import physicsday.view.PhysicsPanel;

public class KeyHandler extends PhysicsInput implements KeyListener{

	public KeyHandler(PhysicsPanel p, World world) {
		super(p, world);
		view.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			PhysicsDay.toggleRunning();
		}
		if(e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if(PhysicsInput.selectedItem != null){
				world.removeObject(PhysicsInput.selectedItem);
				PhysicsInput.selectedItem = null;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_R){
			world.resetState();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			PhysicsInput.toAdd = null;
		}
		if(e.getKeyCode() == KeyEvent.VK_V){
			view.resetView();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}