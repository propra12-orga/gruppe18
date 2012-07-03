package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.BombUpItem;

public class ItemDropAction extends ActionObject {

	public ItemDropAction(int type,int x,int y) {
		this.time=System.currentTimeMillis();
		switch (type) {
			case 1:
				this.actor=new BombUpItem(x,y,"oid"+SGameEngine.get().ObjectCounter);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void action() {
		SGameEngine.get().addObject((BombUpItem)this.actor, null,true);
		System.out.println("BombUpItem dropped");
	}

	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
