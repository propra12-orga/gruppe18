package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Player;

public class PlayerWonAction extends ActionObject {

	public PlayerWonAction(Player actor) {
		this.actor = actor;
		this.time = SGameEngine.get().getTime();
	}

	@Override
	public void action() {
		System.out.println(((Player) actor).getName() + " has won the game");
		;
		SGameEngine.get().endGame();

	}

	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
