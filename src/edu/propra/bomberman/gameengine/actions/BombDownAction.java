package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.GameEngine;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGTransform;

public class BombDownAction extends ActionObject {
	Bomb	bomb;
	String	oid;
	int		x	= -10000, y = -10000;

	public BombDownAction(Object actor, long time, String aid) {
		this.actor = actor;
		this.time = time;
		this.aid = aid;
		bomb = ((Player) actor).bombDown();
		if (bomb != null) {
			x = (int) ((SGTransform) bomb.getGo()).getTransform().getTranslateX();
			y = (int) ((SGTransform) bomb.getGo()).getTransform().getTranslateY();
		}
	}

	public BombDownAction(Object actor, long time, String aid, int x, int y) {
		this.actor = actor;
		this.time = time;
		this.aid = aid;
		this.x = x;
		this.y = y;
	}

	@Override
	public void action() {
		if (bomb == null && x > -9999) bomb = new Bomb(((Player) actor), x, y, oid);
		if (bomb != null) {
			//System.out.println("Bomb at: " + x + " , " + y);
			GameEngine ge = SGameEngine.get();
			ge.addObject(bomb, null, false);
			bomb.setAction(new BombUpAction(bomb, this.getTime() + 2000, SGameEngine.get().getActionID(), ((Player) actor).bombSize));
			((SGAnimation) ((SGTransform) bomb.getGo()).getChild()).start();
			ge.addAction(bomb.getAction());
			ge.addAction(new CheckBombLeaveAction(bomb, this.getTime() + 50));
		}
	}

	@Override
	public String getMessageData() {
		return " BombDownAction " + aid + " " + x + " " + y + " " + ((Player) actor).getOid() + " " + time;
	}

}
