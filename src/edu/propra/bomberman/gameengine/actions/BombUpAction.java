package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Explosion;

public class BombUpAction extends ActionObject {

	public BombUpAction(Bomb actor,long time) {
		super();
		this.time=time;
		this.actor=actor;
	}
	@Override
	public void action() {
		if(((Bomb)actor).owner!=null)((Bomb)actor).owner.bombUp();
		SGameEngine.get().removeObject((Bomb)actor);
		Explosion boom=new Explosion((int)((Bomb)actor).absTransform.getTranslateX(), (int)((Bomb)actor).absTransform.getTranslateY(), 3);
		SGameEngine.get().addObject(boom,null);
		SGameEngine.get().addAction(new ExplosionEnd(boom,System.currentTimeMillis()+1000));
	
	}

}
