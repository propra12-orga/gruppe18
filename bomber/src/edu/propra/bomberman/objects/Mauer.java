package edu.propra.bomberman.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import edu.propra.bomberman.data.GameObject;
import edu.propra.bomberman.ui.IDrawable;

public class Mauer extends GameObject implements IDrawable {
	
	public Mauer(int x, int y) {
		this.drawArea=new Rectangle(x,y,50,50);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(50, 50, 50));
		g.fill3DRect(drawArea.x, drawArea.y, drawArea.width, drawArea.height, true);
	}

	@Override
	public Rectangle getDrawArea() {
		return this.drawArea;
	}

	@Override
	public boolean intersectsDraw(Rectangle rect) {
		return rect.intersects(this.drawArea);
	}
	

}
