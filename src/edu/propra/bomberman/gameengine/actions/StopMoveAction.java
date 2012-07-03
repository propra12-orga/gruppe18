package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.objects.Moveable;
import edu.propra.bomberman.gameengine.objects.Player;

public class StopMoveAction extends ActionObject {

	public StopMoveAction(Object actor, long time) {
		super();
		this.actor = actor;
		this.time = time;
	}

	@Override
	public void action() {
		if (actor instanceof Moveable) ((Moveable) actor).getMovingData().stopMoving();
	}

	@Override
	public String getMessageData() {
		int x=(int) ((Player)actor).absTransform.getTranslateX();
		int y=(int) ((Player)actor).absTransform.getTranslateY();
		return " StopMoveAction "+((Player)actor).getOid()+" "+time+" "+x+" "+y;
	}

}
