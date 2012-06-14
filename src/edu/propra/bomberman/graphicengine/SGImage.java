/**
 * 
 */
package edu.propra.bomberman.graphicengine;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * @author Nadescha
 *
 */
public class SGImage extends SGLeaf {
	

	/**
	 * 
	 */
	
	private BufferedImage image = null;
	
	
	
	
	
	
	public SGImage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 */
	public SGImage(SGNode parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}






	@Override
	public void paint(AffineTransform transform, Graphics2D g2d) {
		
		g2d.drawImage((Image)image, transform, null);
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
