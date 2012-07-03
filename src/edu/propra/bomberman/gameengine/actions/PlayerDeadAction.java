package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.audio.Jukebox;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class PlayerDeadAction extends ActionObject {

	public PlayerDeadAction(Player actor) {
		this.actor = actor;
		this.time = System.currentTimeMillis();
	}

	@Override
	public void action() {
		System.out.println("Player Dead " + ((Player) this.actor).getName());
		SGImage deathNode = new SGImage();
		deathNode.setClipArea(Player.clipArea);
		deathNode.setImage(Player.deathImage);
		((SGTransform) ((Player) actor).getGo()).addChild(deathNode);
		((Player) this.actor).isDead(true);
		SGameEngine.get().removePlayer(this.actor);
		SGameEngine.get().getSoundEngine().playSound(Jukebox.Roll);

	}

	@Override
	public String getMessageData() {
		return " PlayerDeadAction "+((Player)actor).getOid()+" "+time;
	}

}
