import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(Loop.isRunning){
				Main.pauseLoop();
			}
			else{
				Main.resumeLoop();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if(Panel.selectedItem != null){
				World.objects.remove(Panel.selectedItem);
				Panel.selectedItem = null;
				Main.panel.repaint();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_R){
			World.resetState();
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			Frame.mouseHandler.canAdd = false;
			Frame.mouseHandler.toAdd = null;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
