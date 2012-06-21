package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.graphicengine.SGImage;

public class GameOverAction extends ActionObject {

	public GameOverAction() {
		
	}
	@Override
	public void action() {
		System.out.println("Game Over");
		//SGameEngine.get().getGraphicsEngine().getScene().add()
	}

}
