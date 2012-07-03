package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.Player;

public class PlayerBombGrowAction extends ActionObject {

	private GameObject	item;
	public PlayerBombGrowAction(Player actor,GameObject item) {
		this.time=System.currentTimeMillis();
		this.item=item;
		this.actor=actor;
	}
	@Override
	public void action() {
		((Player)actor).bombGrowUp();
		SGameEngine.get().removeObject(item);
	}
	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
