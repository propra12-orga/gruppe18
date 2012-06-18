package edu.propra.bomberman.graphicengine;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SGPanel extends JPanel {
	private static final long serialVersionUID = 1613832323027966341L;
	private SGScene scene;

	public SGPanel() {
		scene = new SGScene();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		scene.PaintRecursive(null, (Graphics2D) g);
	}

	public SGScene getScene() {
		return scene;
	}

	public void setScene(SGScene scene) {
		this.scene = scene;
	}

}
