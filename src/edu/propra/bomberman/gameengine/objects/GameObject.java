package edu.propra.bomberman.gameengine.objects;

import java.awt.geom.AffineTransform;

import edu.propra.bomberman.collisionengine.CollisionEngine;
import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGGroup;
import edu.propra.bomberman.graphicengine.SGNode;

public abstract class GameObject {
	protected CollisionObject co;
	protected SGNode go;
	protected AffineTransform absTransform;
	
	public abstract void collisionWith(Object a);

	public CollisionObject getCo() {
		return co;
	}

	public void setCo(CollisionObject co) {
		this.co = co;
	}

	public SGNode getGo() {
		return go;
	}

	public void setGo(SGNode go) {
		this.go = go;
	}
	public void doPreMoves(){
		if(this instanceof Moveable){
			((Moveable)this).getMovingData().doStepCollision(this.co);
		}
	}
	public void doMoves(){
		if(this instanceof Moveable){
			((Moveable)this).getMovingData().doStepGraphic(this);
		}
	}
	public void addToScene(SGGroup parent){
		parent.addChild(this.go);
	}

	public void addToCollisionEngine(CollisionEngine ce) {
		ce.AddObject(this.co);		
	}


	protected boolean isAbsIntialized=false;
	public void initializeAbsolutePositions(AffineTransform trans) {
		this.absTransform.concatenate(trans);
		this.isAbsIntialized=true;
	}
	
	protected boolean collisionsInitialized=false;
	public abstract void initializeCollisions();
	
}
