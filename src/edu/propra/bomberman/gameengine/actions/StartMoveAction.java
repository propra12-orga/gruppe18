package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.objects.Moveable;

public class StartMoveAction extends ActionObject {
	int	direction;

	public StartMoveAction(int direction, Object actor, long time) {
		super();
		this.direction = direction;
		this.actor = actor;
		this.time = time;
	}

	@Override
	public void action() {
		if (actor instanceof Moveable) ((Moveable) actor).getMovingData().startMoving(direction);
	}

}
