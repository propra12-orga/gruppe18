package edu.propra.bomberman.gameengine.objects;

import java.awt.geom.AffineTransform;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGNode;
import edu.propra.bomberman.graphicengine.SGTransform;

public abstract class GameObject {
	public AffineTransform		absTransform;
	protected CollisionObject	co;
	protected boolean			collisionsInitialized	= false;
	protected SGNode			go;
	protected boolean			isAbsIntialized			= false;
	private String				oid;

	private GameObject			parent;

	public abstract void collisionWith(Object a);

	public void doMoves() {
		if (this instanceof Moveable) {
			((Moveable) this).getMovingData().doStepGraphic(this);
		}
	}

	public void doPreMoves() {
		if (this instanceof Moveable) {
			((Moveable) this).getMovingData().doStepCollision();
		}
	}

	public GameObject getByOid(String oid2) {
		if (this.oid.equals(oid2)) return this;
		return null;
	}

	public CollisionObject getCo() {
		return co;
	}

	public SGNode getGo() {
		return go;
	}

	public abstract String getMessageData();

	public String getOid() {
		return oid;
	}

	public GameObject getParent() {
		return parent;
	}

	public void initializeAbsolutePositions(AffineTransform trans) {
		if (!this.isAbsIntialized) this.absTransform.concatenate(trans);
		this.isAbsIntialized = true;
	}

	public abstract void initializeCollisions();

	public void setCo(CollisionObject co) {
		this.co = co;
	}

	public void setGo(SGNode go) {
		this.go = go;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}
	public void setPosition(int fromx, int fromy) {
		AffineTransform difCoGo=new AffineTransform(absTransform);
		difCoGo.translate(-((SGTransform) go).getTransform().getTranslateX(), -((SGTransform)go).getTransform().getTranslateY());
		absTransform.setToTranslation(fromx, fromy);
		((SGTransform) go).getTransform().setToTranslation(fromx-difCoGo.getTranslateX(), fromy-difCoGo.getTranslateY());
		this.co.setCollisionArea(Player.collisionArea.createTransformedArea(absTransform));
				
	}

}
