package edu.propra.bomberman.ui;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface IDrawable {
	public void draw(Graphics g);
	public Rectangle getDrawArea();
	public boolean intersectsDraw(Rectangle rect);
}
