package edu.propra.bomberman.gameengine.objects;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import edu.propra.bomberman.collisionengine.CollisionEngine;
import edu.propra.bomberman.graphicengine.SGGroup;
import edu.propra.bomberman.graphicengine.SGTransform;

public class GameObjectGroup extends GameObject{
	ArrayList<GameObject> group;
	
	public GameObjectGroup(int x,int y){
		this.group=new ArrayList<GameObject>();
		this.go=new SGTransform();
		((SGTransform)this.go).getTransform().setToTranslation(x, y);
		absTransform=(AffineTransform) ((SGTransform)this.go).getTransform().clone();
		((SGTransform)this.go).setChild(new SGGroup());
	}

	public GameObject addChild(GameObject go){
		group.add(go);
		return go;
	}
	
	@Override
	public void collisionWith(Object a) {
		return;
	}
	@Override
	public void doPreMoves(){
		for(GameObject child : this.group){
			child.doPreMoves();
		}
	}
	@Override
	public void doMoves(){
		for(GameObject child : this.group){
			child.doMoves();
		}
	}

	@Override
	public void addToScene(SGGroup parent){
		parent.addChild(this.go);
		SGGroup newParent =(SGGroup) ((SGTransform) this.go).getChild();
		for(GameObject child : this.group){
			child.addToScene(newParent);
		}
	}
	
	@Override
	public void addToCollisionEngine(CollisionEngine ce){
		for(GameObject child : this.group){
			child.addToCollisionEngine(ce);
		}
	}
	
	@Override 
	public void initializeAbsolutePositions(AffineTransform trans){
		absTransform.concatenate(trans);
		this.isAbsIntialized=true;
		for(GameObject child : this.group){
			child.initializeAbsolutePositions(absTransform);
		}
	}
	@Override 
	public void initializeCollisions(){
		if(this.isAbsIntialized){
			for(GameObject child : this.group){
				child.initializeCollisions();
			}
		}else{
			System.err.println("GameObjectGroup.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}
	}
	public void removeChildRecursive(GameObject go){
		for(GameObject child : this.group){
			if(child==go)this.group.remove(go);
			if(child instanceof GameObjectGroup)((GameObjectGroup) child).removeChildRecursive(go);
		}		
		return;
	}
}
