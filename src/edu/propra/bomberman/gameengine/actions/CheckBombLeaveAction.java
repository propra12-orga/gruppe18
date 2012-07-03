package edu.propra.bomberman.gameengine.actions;

import java.awt.geom.Area;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;

public class CheckBombLeaveAction extends ActionObject {

	public CheckBombLeaveAction(Bomb newBomb, long time) {
		this.actor = newBomb;
		this.time = time;
	}

	@Override
	public void action() {
		if (((Bomb) actor).getAction().time < this.time) return;
		Area bArea = (Area) ((Bomb) this.actor).getCo().getCollisionArea().clone();
		Area pArea = ((Bomb) this.actor).owner.getCo().getCollisionArea();
		bArea.intersect(pArea);
		if (bArea.isEmpty()) {
			((Bomb) this.actor).playerOut = true;
		} else {
			SGameEngine.get().addAction(new CheckBombLeaveAction((Bomb) this.actor, System.currentTimeMillis() + 50));
		}

	}

	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
