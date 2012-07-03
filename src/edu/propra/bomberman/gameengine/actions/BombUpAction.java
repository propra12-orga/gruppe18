package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.audio.Jukebox;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Explosion;

public class BombUpAction extends ActionObject {

		int size;
	public BombUpAction(Bomb actor, long time,String aid,int size) {
		super();
		this.time = time;
		this.actor = actor;
		this.aid=aid;
		this.size=size;
	}

	@Override
	public void action() {
		if (((Bomb) actor).owner != null) ((Bomb) actor).owner.bombUp();
		SGameEngine.get().removeObject((Bomb) actor);
		Explosion boom = new Explosion((int) ((Bomb) actor).absTransform.getTranslateX(), (int) ((Bomb) actor).absTransform.getTranslateY(), size,"oid"+SGameEngine.get().ObjectCounter);
		SGameEngine.get().getSoundEngine().playSound(Jukebox.Explosion);
		SGameEngine.get().addObject(boom, null,false);
		SGameEngine.get().addAction(new ExplosionEnd(boom, System.currentTimeMillis() + 500));

	}

	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
