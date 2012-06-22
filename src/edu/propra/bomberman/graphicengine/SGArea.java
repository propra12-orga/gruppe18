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
public class SGArea extends SGLeaf {
	private Area	Drawarea;
	private Color	color;

	/**
	 * 
	 */

	public SGArea() {
		Drawarea = new Area();
		color = new Color(255, 0, 0);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.propra.bomberman.graphicengine.SGLeaf#paint(java.awt.geom.AffineTransform
	 * , java.awt.Graphics2D)
	 */
	@Override
	public void paint(AffineTransform transform, Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fill(Drawarea.createTransformedArea(transform));

	}

	/**
	 * @return the drawarea
	 */
	public Area getDrawarea() {
		return Drawarea;
	}

	/**
	 * @param drawarea
	 *            the drawarea to set
	 */
	public void setDrawarea(Area drawarea) {
		Drawarea = drawarea;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
