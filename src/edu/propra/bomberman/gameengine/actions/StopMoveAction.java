package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.objects.Moveable;

public class StopMoveAction extends ActionObject {

	public StopMoveAction(Object actor, long time) {
		super();
		this.actor=actor;
		this.time=time;
	}
	@Override
	public void action() {
		if(actor instanceof Moveable)
			((Moveable)actor).getMovingData().stopMoving();
	}

}
