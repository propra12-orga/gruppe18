package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.GameEngine;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGTransform;

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
			((SGAnimation)((SGTransform)newBomb.getGo()).getChild()).start();
			ge.addAction(newBomb.getAction());
			ge.addAction(new CheckBombLeaveAction(newBomb, System.currentTimeMillis() + 50));
		}
	}

}
