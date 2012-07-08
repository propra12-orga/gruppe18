package edu.propra.bomberman.gameengine.actions;

import java.awt.geom.Area;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Player;

public class CheckBombLeaveAction extends ActionObject {

	public CheckBombLeaveAction(Bomb newBomb, long time) {
		this.actor = newBomb;
		this.time = time;
	}

	@Override
	public void action() {
		if (((Bomb) actor).getAction().time < this.time) return;
		Area bArea = (Area) ((Bomb) this.actor).getCo().getCollisionArea().clone();
		if(((Bomb)this.actor).owner!=null){
			Area pArea = ((Bomb) this.actor).owner.getCo().getCollisionArea();
			bArea.intersect(pArea);
			if (bArea.isEmpty()) {
				((Bomb) this.actor).playerOut = true;
			} else {
				SGameEngine.get().addAction(new CheckBombLeaveAction((Bomb) this.actor, SGameEngine.get().getTime() + 50));
			}
		}else{
			boolean out=true;
			for(Player player : SGameEngine.get().playerList){
				Area pArea = player.getCo().getCollisionArea();
				bArea.intersect(pArea);
				if (!bArea.isEmpty()) {
					out=false;
					SGameEngine.get().addAction(new CheckBombLeaveAction((Bomb) this.actor, SGameEngine.get().getTime() + 50));
				}
			}
			((Bomb) this.actor).playerOut = out;
		}

	}

	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
