/**
 * 
 */
package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author Nadescha
 * 
 */
public class SGAnimation extends SGLeaf {

	private BufferedImage[]	images;
	private long			aniTime;
	private boolean			repeat;

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	// Hilfsvariable
	private long	startTime;
	private double	imagesPerSecond;

	/**
	 * 
	 */
	public SGAnimation(BufferedImage[] images, long aniTime) {
		this.startTime = 0;

		this.images = images;
		this.aniTime = aniTime;
		this.imagesPerSecond = ((double) this.images.length) / ((double) this.aniTime);
		this.repeat = false;

	}

	public void start() {
		startTime = System.currentTimeMillis();

	}

	public void stop() {
		startTime = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * edu.propra.bomberman.graphicengine.SGLeaf#paint(java.awt.geom.AffineTransform
	 * , java.awt.Graphics2D)
	 */
	@Override
	public void paint(AffineTransform transform, Graphics2D g2d) {
		int index;

		if (startTime > 0) {
			long duration = System.currentTimeMillis() - startTime;
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
		g2d.drawImage(images[index], transform, null);

	}

}
