package edu.propra.bomberman.data;

import java.awt.Rectangle;

public class GameObject implements Comparable<GameObject> {
	protected Rectangle drawArea;
	
	

	@Override
	public int compareTo(GameObject o) {
		return  this.drawArea.x - o.drawArea.x ;
	}
}
