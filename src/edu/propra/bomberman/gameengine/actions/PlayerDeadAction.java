package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Player;

public class PlayerDeadAction extends ActionObject {

	
	public PlayerDeadAction(Player actor) {
		this.actor=actor;
		this.time=System.currentTimeMillis();
	}
	
	@Override
	public void action() {
		System.out.println("Player Dead "+((Player)this.actor).getName());
		((Player)this.actor).isDead(true);
		SGameEngine.get().removePlayer(this.actor);
	}

}
