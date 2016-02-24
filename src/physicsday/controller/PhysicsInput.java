package physicsday.controller;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import physicsday.model.Body;
import physicsday.model.World;
import physicsday.util.Vector;
import physicsday.view.PhysicsPanel;

public class PhysicsInput implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	public int scrollWheel = 0;
	public int keys = 0;
	public Vector draggedDistance = new Vector();
	public boolean[] keyDown = new boolean[256];
	public boolean[] mouseDown = new boolean[MouseInfo.getNumberOfButtons()];
	public boolean mouseInside;
	public Vector lastLocation = new Vector();
	
	public PhysicsInput(){
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		draggedDistance.addi(new Vector( e.getX() - lastLocation.x, e.getY() - lastLocation.y));
		lastLocation.set(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastLocation.set(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!keyDown[e.getKeyCode()]){
			keyDown[e.getKeyCode()] = true;
			keys++;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyDown[e.getKeyCode()] = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scrollWheel -= e.getWheelRotation();
	}

	public void clear() {
		keys = 0;
	}
}
