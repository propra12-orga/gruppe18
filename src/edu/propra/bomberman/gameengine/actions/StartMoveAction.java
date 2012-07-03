package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.objects.Moveable;
import edu.propra.bomberman.gameengine.objects.Player;

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

	@Override
	public String getMessageData() {
		int x=(int) ((Player)actor).absTransform.getTranslateX();
		int y=(int) ((Player)actor).absTransform.getTranslateY();
		return " StartMoveAction "+direction+" "+((Player)actor).getOid()+" "+time+" "+x+" "+y;
	}

}
