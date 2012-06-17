package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;

public class BombUpAction extends ActionObject {

	public BombUpAction(Bomb actor,long time) {
		super();
		this.time=time;
		this.actor=actor;
	}
	@Override
	public void action() {
		if(((Bomb)actor).owner!=null)((Bomb)actor).owner.bombUp();
		SGameEngine.get().explodeBomb((Bomb)actor);
	}

}
