package edu.propra.bomberman.graphicengine;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SGPanel extends JPanel {
	private SGScene scene;
	
	public SGPanel() {
		scene=new SGScene();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		synchronized (scene) {
			scene.PaintRecursive(null, (Graphics2D)g);		
		}
	}

	public SGScene getScene() {
		return scene;
	}

	public void setScene(SGScene scene) {
		this.scene = scene;
	}
	
	

}
