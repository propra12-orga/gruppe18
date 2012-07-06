/**
 * 
 */
package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import edu.propra.bomberman.gameengine.SGameEngine;

/**
 * @author Nadescha
 * Leafnode which runs animations
 */
public class SGAnimation extends SGLeaf {

	private long			aniTime;
	private BufferedImage[]	images;
	private double			imagesPerSecond;

	private boolean			repeat;

	// Hilfsvariable
	private long			startTime;

	/**
	 * Every time when Video starts , start time is shown, starttime is used too calculate the animation
	 */
	public SGAnimation(BufferedImage[] images, long aniTime) {
		this.startTime = 0;

		this.images = images;
		this.aniTime = aniTime;
		this.imagesPerSecond = ((double) this.images.length) / ((double) this.aniTime);
		this.repeat = false;

	}

	public boolean isRepeat() {
		return repeat;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.propra.bomberman.graphicengine.SGLeaf#paint(java.awt.geom.AffineTransform
	 * , java.awt.Graphics2D)
	 */
	/**
	 * Paints the pictures of animation 
	 */
	@Override
	public void paint(AffineTransform transform, Graphics2D g2d) {
		int index;

		if (startTime > 0) {
			long duration = SGameEngine.get().getTime() - startTime;
			if (duration > aniTime) {
				if (repeat) {
					duration = duration % aniTime;
				} else {
					stop();
					duration = 0;
				}
			}
			index = (int) (duration * imagesPerSecond);
			if (index + 1 > images.length) index = images.length - 1;
		} else {
			index = 0;
		}
		g2d.setClip(this.getClipArea().createTransformedArea(transform));
		g2d.drawImage(images[index], transform, null);

	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public void start() {
		startTime = SGameEngine.get().getTime();

	}

	public void stop() {
		startTime = 0;
	}

}
