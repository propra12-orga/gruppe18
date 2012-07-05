package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.BombGrowItem;
import edu.propra.bomberman.gameengine.objects.BombUpItem;
import edu.propra.bomberman.gameengine.objects.GameObject;

public class ItemDropAction extends ActionObject {

	public ItemDropAction(int type, int x, int y) {
		this.time = SGameEngine.get().getTime();
		switch (type) {
			case 1:
				this.actor = new BombUpItem(x, y, "oid" + SGameEngine.get().ObjectCounter);
				break;
			case 2:
				this.actor = new BombGrowItem(x, y, "oid" + SGameEngine.get().ObjectCounter);
				break;
			default:
				break;
		}
	}

	@Override
	public void action() {

		SGameEngine.get().addObject((GameObject) this.actor, null, true);
		System.out.println("Item dropped");
	}

	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
