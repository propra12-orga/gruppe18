package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.IceBlock;

public class IceBlockDestroyedAction extends ActionObject {

	public IceBlockDestroyedAction(IceBlock actor) {
		this.actor=actor;
		this.time=System.currentTimeMillis();
	}
	
	private static double chance=10;
	@Override
	public void action(){
		SGameEngine.get().removeObject((GameObject)this.actor);
		int x =(int)(Math.random()*(chance-1)+1d);
		if(x==((int)chance)-1){
			//TODO add zufallszahl für die verschiedenen items
			SGameEngine.get().addAction(new ItemDropAction(0,(int)((IceBlock)actor).absTransform.getTranslateX(),(int)((IceBlock)actor).absTransform.getTranslateY()));
		}
	}

}
