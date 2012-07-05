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
public class SGScene extends SGNode implements IParent {
	private SGGroup	child;

	/**
	 * 
	 */
	public SGScene() {
		child = new SGGroup();
		child.setParent(this);
	}

	@Override
	public void addChild(Object child) {
		this.child.addChild(child);
	}

	public SGGroup getChild() {
		return this.child;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.propra.bomberman.graphicengine.SGNode#PaintRecursive(java.awt.geom
	 * .AffineTransform, java.awt.Graphics2D)
	 */
	@Override
	public void PaintRecursive(AffineTransform transform, Graphics2D g2d) {
		if (transform == null) transform = new AffineTransform();
		child.PaintRecursive(transform, g2d);
	}

	@Override
	public void releaseAll() {
		if (this.child != null) {
			this.child.releaseAll();
		}
	}

	@Override
	public void removeChild(Object child) {
		this.child.removeChild(child);
	}
}
