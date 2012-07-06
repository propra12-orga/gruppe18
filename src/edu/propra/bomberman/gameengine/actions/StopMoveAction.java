package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.Moveable;
import edu.propra.bomberman.gameengine.objects.Player;

public class StopMoveAction extends ActionObject {

	int atx,aty;
	public StopMoveAction(Object actor, long time,int atx,int aty) {
		super();
		this.actor = actor;
		this.time = time;
		this.atx=atx;
		this.aty=aty;
	}

	@Override
	public void action() {
		if(atx!=-10000){
			if (actor instanceof GameObject) ((GameObject) actor).setPosition(atx, aty);	
		}
		if (actor instanceof Moveable) ((Moveable) actor).getMovingData().stopMoving();
	}

	@Override
	public String getMessageData() {
		int x = (int) ((Player) actor).absTransform.getTranslateX();
		int y = (int) ((Player) actor).absTransform.getTranslateY();
		return " StopMoveAction " + ((Player) actor).getOid() + " " + time + " " + x + " " + y;
	}

}
