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
	private int				actIndex;
	private BufferedImage	images[];

	/**
	 * 
	 */
	public SGConditionedImage(BufferedImage images[]) {
		this.images = images;
		this.actIndex = 0;

	}

	@Override
	public void paint(AffineTransform transform, Graphics2D g2d) {

		g2d.drawImage(images[actIndex], transform, null);
	}

	public void setCondition(int actIndex) {
		this.actIndex = actIndex;

	}

}
