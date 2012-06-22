package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;

public class GameOverAction extends ActionObject {

	public GameOverAction() {
		this.time=System.currentTimeMillis();
	}

	@Override
	public void action() {
		System.out.println("Game Over");
		SGameEngine.get().endGame();
	}

}
