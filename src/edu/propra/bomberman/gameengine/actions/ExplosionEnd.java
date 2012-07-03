package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Explosion;

public class ExplosionEnd extends ActionObject {

	public ExplosionEnd(Explosion actor, long time) {
		super();
		this.time = time;
		this.actor = actor;
	}

	@Override
	public void action() {
		SGameEngine.get().removeExplosion((Explosion) actor);
	}

	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
