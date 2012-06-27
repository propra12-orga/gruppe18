package edu.propra.bomberman.gameengine;

import edu.propra.bomberman.gameengine.objects.GameObject;
import java.awt.event.*;

/**
 * @author Jens Herrmann
 */

public class KartenEditor extends GameEngine implements ActionListener{

	int x, y;
	boolean mouseEntered = false;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
	
	/**
	 * 
	 * hier sollte eine Karte gezeichnet werden
	 */
	
	
	
	public static void main (String[] args) {
		
		
	
	}


		
}



