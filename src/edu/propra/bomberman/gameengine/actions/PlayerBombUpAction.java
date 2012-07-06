package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.Player;

public class PlayerBombUpAction extends ActionObject {

	private GameObject	item;

	public PlayerBombUpAction(Player actor, GameObject item) {
		this.time = SGameEngine.get().getTime();
		this.item = item;
		this.actor = actor;
	}

	@Override
	public void action() {
		((Player) actor).bombCountUp();
		SGameEngine.get().removeObject(item);
	}

	@Override
	public String getMessageData() {
		return " PlayerBombUpAction "+((Player)actor).getOid()+" "+((GameObject)item).getOid()+" "+time;
	}

}
