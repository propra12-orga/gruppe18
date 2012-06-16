package edu.propra.bomberman.gameengine;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGNode;

public abstract class GameObject {
	protected CollisionObject co;
	protected SGNode go;

	public abstract void collisionWith(Object a);

	
}
