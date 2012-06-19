/**
 * 
 */
package edu.propra.bomberman.graphicengine;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

/**
 * @author Nadescha
 *
 */
public class SGLeaf extends SGNode {
	private Area clipArea;
	private AffineTransform actTrans;
	/**
	 * 
	 */
	public SGLeaf() {
		clipArea=new Area();
		actTrans=new AffineTransform();
	}

	/* (non-Javadoc)
	 * @see edu.propra.bomberman.graphicengine.SGNode#PaintRecursive(java.awt.geom.AffineTransform)
	 */
	@Override
	public void PaintRecursive(AffineTransform transform, Graphics2D g2d) {
		setActTrans(transform);
		this.paint(transform,g2d);

	}
	
	public void paint(AffineTransform transform, Graphics2D g2d) {
		g2d.setColor(new Color(255,0,0));
		g2d.fill(clipArea.createTransformedArea(transform));
	}

	public Area getClipArea() {
		return clipArea;
	}

	public void setClipArea(Area clipArea) {
		this.clipArea = clipArea;
	}

	public AffineTransform getActTrans() {
		return actTrans;
	}

	public void setActTrans(AffineTransform actTrans) {
		this.actTrans.setTransform(actTrans);
	}

}
