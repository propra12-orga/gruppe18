/**
 * 
 */
package edu.propra.bomberman.graphicengine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import edu.propra.bomberman.collisionengine.CollisionObject;

/**
 * @author Nadescha
 * 
 */
public class SGImage extends SGLeaf {

	public CollisionObject	debugColl	= null;

	/**
	 * 
	 */

	private BufferedImage	image;

	public SGImage() {
		super();
		image = null;
	}

	public SGImage(BufferedImage image) {
		super();
		this.image = image;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public void paint(AffineTransform transform, Graphics2D g2d) {
		// Debug cliparea
		if (debugColl != null) {
			g2d.setColor(new Color(255, 0, 0));
			g2d.fill(debugColl.getCollisionArea());
		}

		g2d.setClip(this.getClipArea().createTransformedArea(transform));
		g2d.drawImage(image, transform, null);
		g2d.setClip(null);
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
