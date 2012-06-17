/**
 * 
 */
package edu.propra.bomberman.graphicengine;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
/**
 * @author Nadescha
 *
 */
public class SGImage extends SGLeaf {

	/**
	 * 
	 */
	
	private BufferedImage image ;
	
	
	
	
	
	
	public SGImage() {
		super();
		image=null;
	}


	public SGImage(BufferedImage image) {
		super();
		this.image=image;
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
