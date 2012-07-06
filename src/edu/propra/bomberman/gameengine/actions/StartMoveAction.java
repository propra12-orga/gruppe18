package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.Moveable;
import edu.propra.bomberman.gameengine.objects.Player;

public class StartMoveAction extends ActionObject {
	int	direction;
	int fromx;
	int fromy;

	public StartMoveAction(int direction, Object actor, long time,int fromx,int fromy) {
		super();
		this.direction = direction;
		this.actor = actor;
		this.time = time;
		this.fromx=fromx;
		this.fromy=fromy;
	}

	@Override
	public void action() {
		if(fromx!=-10000){
			if (actor instanceof GameObject) ((GameObject) actor).setPosition(fromx,fromy);
		}
		if (actor instanceof Moveable) ((Moveable) actor).getMovingData().startMoving(direction);
	}

	@Override
	public String getMessageData() {
		int x = (int) ((Player) actor).absTransform.getTranslateX();
		int y = (int) ((Player) actor).absTransform.getTranslateY();
		return " StartMoveAction " + direction + " " + ((Player) actor).getOid() + " " + time + " " + x + " " + y;
	}

}
