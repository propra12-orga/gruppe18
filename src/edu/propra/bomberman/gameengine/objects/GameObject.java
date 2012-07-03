package edu.propra.bomberman.gameengine.objects;

import java.awt.geom.AffineTransform;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGNode;

public abstract class GameObject {
	protected CollisionObject	co;
	protected SGNode			go;
	public AffineTransform		absTransform;
	private GameObject			parent;
	private String 				oid;
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

	public void doPreMoves() {
		if (this instanceof Moveable) {
			((Moveable) this).getMovingData().doStepCollision();
		}
	}

	public void doMoves() {
		if (this instanceof Moveable) {
			((Moveable) this).getMovingData().doStepGraphic(this);
		}
	}

	protected boolean	isAbsIntialized	= false;

	public void initializeAbsolutePositions(AffineTransform trans) {
		if (!this.isAbsIntialized) this.absTransform.concatenate(trans);
		this.isAbsIntialized = true;
	}

	protected boolean	collisionsInitialized	= false;

	public abstract void initializeCollisions();

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public GameObject getByOid(String oid2) {
		if(this.oid.equals(oid2))return this;
		return null;
	}
	
	public abstract String getMessageData();

}
