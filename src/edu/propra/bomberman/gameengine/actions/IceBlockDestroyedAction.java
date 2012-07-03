package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.IceBlock;

public class IceBlockDestroyedAction extends ActionObject {

	public int type=-1;
	public IceBlockDestroyedAction(IceBlock actor, String aid,int type) {
		this.actor=actor;
		this.time=System.currentTimeMillis();
		this.type=type;
	}
	
	private static double chance=10;
	@Override
	public void action(){
		SGameEngine.get().removeObject((GameObject)this.actor);
		if(type!=-1){
			SGameEngine.get().addAction(new ItemDropAction(type,(int)((IceBlock)actor).absTransform.getTranslateX(),(int)((IceBlock)actor).absTransform.getTranslateY()),false);
		}
	}
	@Override
	public String getMessageData() {
		return null;
	}

}
