package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.GameEngine;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Player;

public class BombDownAction extends ActionObject {

	public BombDownAction(Object actor, long time) {
		this.actor = actor;
		this.time = time;
	}

	@Override
	public void action() {
		Bomb newBomb = ((Player) actor).bombDown();
		if (newBomb != null) {
			GameEngine ge = SGameEngine.get();
			ge.addObject(newBomb, null);
			newBomb.setAction(new BombUpAction(newBomb, System.currentTimeMillis() + 2000));
			ge.addAction(newBomb.getAction());
			ge.addAction(new CheckBombLeaveAction(newBomb, System.currentTimeMillis() + 50));
		}
	}

}
