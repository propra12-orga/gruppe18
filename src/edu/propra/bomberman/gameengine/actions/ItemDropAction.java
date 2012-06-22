package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.BombUpItem;

public class ItemDropAction extends ActionObject {

	public ItemDropAction(int type,int x,int y) {
		this.time=System.currentTimeMillis();
		switch (type) {
			case 0:
					this.actor=new BombUpItem(x,y);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void action() {
		SGameEngine.get().addObject((BombUpItem)this.actor, null);
		System.out.println("BombUpItem dropped");
	}

}
