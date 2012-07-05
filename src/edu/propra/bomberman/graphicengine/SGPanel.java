package edu.propra.bomberman.graphicengine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class SGPanel extends JPanel {
	private static final long	serialVersionUID	= 1613832323027966341L;
	private BufferedImage		cache;

	public boolean				ended				= false;

	private SGScene				scene;
	public boolean				updateCache			= true;

	public SGPanel() {
		scene = new SGScene();

		cache = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
	}

	public SGScene getScene() {
		return scene;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (updateCache && !ended) {
			Graphics2D g2d = cache.createGraphics();
			scene.PaintRecursive(null, g2d);
		}
		((Graphics2D) g).drawImage(cache, new AffineTransform(), null);
	}

	public void setScene(SGScene scene) {
		this.scene = scene;
	}

}
