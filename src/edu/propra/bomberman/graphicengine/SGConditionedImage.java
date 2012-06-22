/**
 * 
 */
package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author Nadescha
 * 
 */
public class SGConditionedImage extends SGLeaf {
	private BufferedImage	images[];
	private int				actIndex;

	/**
	 * 
	 */
	public SGConditionedImage(BufferedImage images[]) {
		this.images = images;
		this.actIndex = 0;

	}

	public void setCondition(int actIndex) {
		this.actIndex = actIndex;

	}

	@Override
	public void paint(AffineTransform transform, Graphics2D g2d) {

		g2d.drawImage(images[actIndex], transform, null);
	}

}
