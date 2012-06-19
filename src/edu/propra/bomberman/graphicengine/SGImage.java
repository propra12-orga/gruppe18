/**
 * 
 */
package edu.propra.bomberman.graphicengine;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
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
		// Debug cliparea
		//g2d.setColor(new Color(255,0,0));
		//g2d.fill(this.getClipArea().createTransformedArea(transform));
	
		g2d.setClip((Area)this.getClipArea().createTransformedArea(transform));
		g2d.drawImage((Image)image, transform, null);
		g2d.setClip(null);
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
