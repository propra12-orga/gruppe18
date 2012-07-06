package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.BombUpGingerbread;
import edu.propra.bomberman.gameengine.objects.BombUpItem;

public class ItemDropAction extends ActionObject {

	private int type = 0;
	
	public ItemDropAction(int type,int x,int y) {
		this.time=System.currentTimeMillis();
		switch (type) {
			case 0:
				this.type=0;
				this.actor=new BombUpItem(x,y);
				break;
			case 1:
				this.type=1;
				this.actor=new BombUpGingerbread(x,y);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void action() {
		System.out.println("BombUpItem dropped");
		// SGameEngine.get().addObject((BombUpGingerbread)this.actor, null);
		// SGameEngine.get().addObject((BombUpItem)this.actor, null);
		if(this.type==0) {
			SGameEngine.get().addObject((BombUpItem)this.actor, null);
		} else if(this.type==1) {
			SGameEngine.get().addObject((BombUpGingerbread)this.actor, null);
		}
	}

}
