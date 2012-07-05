package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;

public class GameOverAction extends ActionObject {

	public GameOverAction(String aid, long time) {
		this.time = time;
		this.aid = aid;
	}

	@Override
	public void action() {
		System.out.println("Game Over");
		SGameEngine.get().endGame();
	}

	@Override
	public String getMessageData() {
		return " GameOverAction " + aid + " " + time;
	}

}
