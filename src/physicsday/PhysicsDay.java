package physicsday;

import java.awt.Color;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import physicsday.controller.KeyHandler;
import physicsday.controller.MouseHandler;
import physicsday.controller.MouseMotionHandler;
import physicsday.controller.PhysicsEngine;
import physicsday.controller.PhysicsLoop;
import physicsday.controller.ScrollHandler;
import physicsday.controller.VariableLoop;
import physicsday.model.Block;
import physicsday.model.Body;
import physicsday.model.World;
import physicsday.view.MenuBar;
import physicsday.view.PhysicsFrame;
import physicsday.view.PhysicsPanel;
import physicsday.view.PhysicsScreen;

public class PhysicsDay implements PhysicsEngine{
	private static PhysicsDay physicsDay;	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		PhysicsDay physicsDay = new PhysicsDay();
		PhysicsLoop loop = new VariableLoop();
		PhysicsScreen screen = new PhysicsScreen(640, 480, loop, physicsDay);
		screen.setBackground(Color.WHITE);
		PhysicsScreen.showWindow(screen, "PhysicsDay");
	}
	
	public static PhysicsFrame getFrame() {
		return frame;
	}
	public static PhysicsPanel getPanel() {
		return panel;
	}
}
