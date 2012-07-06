// Author Nadescha

package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *Basicclass which yields properties any SGClass shares 
 */  

public abstract class SGNode {
	/**gives fatherknot of this knot 
	 * 
	 */
	private SGNode	parent;

	/**
	 * Standard Konstruktor
	 */
	public SGNode() {
	}

	public SGNode getParent() {
		return parent;
	}

	public abstract void PaintRecursive(AffineTransform transform, Graphics2D g2d);

	public abstract void releaseAll();

	public void setParent(SGNode parent) {
		this.parent = parent;
	}
}
