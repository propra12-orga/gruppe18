package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public abstract class SGNode {
	/** Gibt den Vaterknoten dieses Knotens an
	 * 
	 * Eigenschaft eines Knotens 
	 */
	private SGNode parent; 

	/**
	 * Standard Konstruktor
	 */
	public SGNode() {}
	
	public abstract void PaintRecursive(AffineTransform transform, Graphics2D g2d);

	public SGNode getParent() {
		return parent;
	}

	public void setParent(SGNode parent) {
		this.parent = parent;
	}
	
	
	
}
