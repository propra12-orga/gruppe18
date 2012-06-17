package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.objects.Player;

public class BombDownAction extends ActionObject {

	public BombDownAction(Object actor, long time) {
		this.actor=actor;
		this.time=time;
	}

	@Override
	public void action() {
		((Player)actor).bomb();
	}

}
