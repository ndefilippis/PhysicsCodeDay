package physicsday.controller;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Observable;

import physicsday.util.Vector;

public class PhysicsInput extends Observable implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	public int scrollWheel = 0;
	public int keys = 0;
	public int mouseButtons = 0;
	public Vector draggedDistance = new Vector();
	public boolean[] keysDown = new boolean[256];
	public boolean[] isKeyDown = new boolean[256];
	public boolean[] keysUp = new boolean[256];
	public boolean[] mouseDown = new boolean[MouseInfo.getNumberOfButtons()];
	public boolean[] mouseUp = new boolean[MouseInfo.getNumberOfButtons()];
	public boolean mouseInside;
	public Vector lastUpLocation = new Vector();
	public Vector lastLocation = new Vector();
	public Vector pressedLocation = new Vector();
	
	public PhysicsInput(){
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		draggedDistance.addi(new Vector( e.getX() - lastLocation.x, e.getY() - lastLocation.y));
		lastLocation.set(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		lastUpLocation.set(e.getX(), e.getY());
		lastLocation.set(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedLocation.set(e.getX(), e.getY());
		mouseDown[e.getButton()] = true;
		mouseButtons++;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseUp[e.getButton()] = true;
		mouseDown[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!isKeyDown[e.getKeyCode()]){
			isKeyDown[e.getKeyCode()] = true;
			keysDown[e.getKeyCode()] = true;
			keys++;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysUp[e.getKeyCode()] = true;
		isKeyDown[e.getKeyCode()] = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scrollWheel += e.getWheelRotation();
	}

	public void clear() {
		keys = 0;
		mouseButtons = 0;
		draggedDistance.set(0, 0);
		for(int i = 0; i < keysUp.length; i++){
			keysUp[i] = false;
			keysDown[i] = false;
		}
		for(int i = 0; i < mouseUp.length; i++){
			mouseUp[i] = false;
		}
		scrollWheel = 0;
	}
}
