/**
 * 
 */
package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * @author Nadescha
 *
 */
public class SGScene extends SGNode {
	private SGGroup child;
	
	

	/**
	 * 
	 */
	public SGScene() {
		child = new SGGroup(this);
		
	}
	public void addChild(SGNode child){
		this.child.addChild(child);
	}


	/* (non-Javadoc)
	 * @see edu.propra.bomberman.graphicengine.SGNode#PaintRecursive(java.awt.geom.AffineTransform, java.awt.Graphics2D)
	 */
	@Override
	public void PaintRecursive(AffineTransform transform, Graphics2D g2d) {
		if(transform==null)transform=new AffineTransform();
		child.PaintRecursive(transform, g2d);
	}

}
