package edu.propra.bomberman.gameengine.objects;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import edu.propra.bomberman.graphicengine.IParent;
import edu.propra.bomberman.graphicengine.SGGroup;
import edu.propra.bomberman.graphicengine.SGNode;
import edu.propra.bomberman.graphicengine.SGTransform;

public class GameObjectGroup extends GameObject implements IParent {
	ArrayList<GameObject>	group;

	public GameObjectGroup(int x, int y,String oid) {
		this.setOid(oid);
		this.group = new ArrayList<GameObject>();
		this.go = new SGTransform();
		((SGTransform) this.go).getTransform().setToTranslation(x, y);
		absTransform = (AffineTransform) ((SGTransform) this.go).getTransform().clone();
		((SGTransform) this.go).addChild(new SGGroup());
	}

	@Override
	public void addChild(Object go) {
		if (go instanceof GameObject) {
			group.add((GameObject) go);
			((GameObject) go).setParent(this);
		}
	}

	@Override
	public void collisionWith(Object a) {
		return;
	}

	@Override
	public void doPreMoves() {
		for (GameObject child : this.group) {
			child.doPreMoves();
		}
	}

	@Override
	public void doMoves() {
		for (GameObject child : this.group) {
			child.doMoves();
		}
	}

	@Override
	public void initializeAbsolutePositions(AffineTransform trans) {
		if (!this.isAbsIntialized) absTransform.concatenate(trans);
		this.isAbsIntialized = true;
	}

	@Override
	public void initializeCollisions() {
		if (this.isAbsIntialized) {
			// for(GameObject child : this.group){
			// child.initializeCollisions();
			// }
		} else {
			System.err.println("GameObjectGroup.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}
	}

	public void removeChildRecursive(GameObject go) {
		for (GameObject child : this.group) {
			if (child == go) break;
			if (child instanceof GameObjectGroup) ((GameObjectGroup) child).removeChildRecursive(go);
		}
		this.group.remove(go);
		return;
	}

	@Override
	public void removeChild(Object obj) {
		((GameObject) obj).setParent(null);
		this.group.remove(obj);
	}

	public SGNode getGoLeaf() {
		return ((SGTransform) this.go).getChild();
	}

	public void releaseData() {
		for (GameObject child : this.group) {
			if(child instanceof GameObjectGroup)
				((GameObjectGroup)child).releaseData();
		}
		group.clear();
	}

	public GameObject getByOid(String oid) {
		if(this.getOid().equals(oid))return this;
		GameObject ret=null;
		for (GameObject child : this.group) {
				ret=child.getByOid(oid);
				if(ret!=null)return ret;
		}
		return ret;
	}

	@Override
	public String getMessageData() {
		int x= (int) ((SGTransform)this.go).getTransform().getTranslateX();
		int y= (int) ((SGTransform)this.go).getTransform().getTranslateY();
		return "GameObjectGroup "+x+" "+y+" "+this.getOid();
	}
}
