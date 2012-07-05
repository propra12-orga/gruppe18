/**
 * 
 */
package edu.propra.bomberman.graphicengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * @author Nadescha
 * 
 */
public class SGText extends SGLeaf {
	private Color	color;
	private String	text;

	/**
	 * 
	 */
	public SGText() {
		text = new String();
		color = new Color(0, 0, 0);

	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.propra.bomberman.graphicengine.SGLeaf#paint(java.awt.geom.AffineTransform
	 * , java.awt.Graphics2D)
	 */
	@Override
	public void paint(AffineTransform transform, Graphics2D g2d) {
		int size = 22;
		g2d.setFont(new Font("Sans", Font.BOLD, size));
		g2d.setColor(color);
		g2d.drawString(text, (int) transform.getTranslateX(), (int) transform.getTranslateY() + size);

	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

}
