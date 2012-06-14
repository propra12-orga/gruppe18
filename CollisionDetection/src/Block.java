import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Block {

	private int x;
	private int y;
	private int width;
	private int height;
	private boolean visible;
	private Image image;
	private final int BOARD_WIDTH = 390;

	public Block(int x, int y) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource("block.png"));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		visible = true;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Image getImage() {
		return image;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}