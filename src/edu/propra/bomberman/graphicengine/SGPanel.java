package edu.propra.bomberman.graphicengine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class SGPanel extends JPanel {
	private static final long serialVersionUID = 1613832323027966341L;
	private SGScene scene;

	public SGPanel() {
		scene = new SGScene();
		
		cache=new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
	}

	private BufferedImage cache;
	public boolean updateCache=true;
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(updateCache){
			Graphics2D g2d=cache.createGraphics();
			scene.PaintRecursive(null,g2d);
		}
		((Graphics2D)g).drawImage((Image)cache, new AffineTransform(), null);
	}
	

	public SGScene getScene() {
		return scene;
	}

	public void setScene(SGScene scene) {
		this.scene = scene;
	}

}
