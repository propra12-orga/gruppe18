package edu.propra.bomberman.gameengine;

import java.awt.event.*;

/**
 * @author Jens Herrmann
 */

public class KartenEditor extends GameEngine implements ActionListener {

	/**
	 * 
	 * hier sollte eine Karte gezeichnet werden
	 */

	public static void main(String[] args) {

	}

	boolean	mouseEntered	= false;

	int		x, y;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
