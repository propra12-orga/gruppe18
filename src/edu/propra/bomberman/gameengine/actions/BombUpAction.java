package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.audio.Jukebox;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Explosion;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGTransform;

public class BombUpAction extends ActionObject {

	int	size;

	public BombUpAction(Bomb actor, long time, String aid, int size) {
		super();
		this.time = time;
		this.actor = actor;
		this.aid = aid;
		this.size = size;
	}

	@Override
	public void action() {
		if (((Bomb) actor).owner != null) ((Bomb) actor).owner.bombUp();
		SGameEngine.get().removeObject((Bomb) actor);
		Explosion boom = new Explosion((int) ((Bomb) actor).absTransform.getTranslateX(), (int) ((Bomb) actor).absTransform.getTranslateY(), size, "oid" + SGameEngine.get().ObjectCounter);
		SGameEngine.get().getSoundEngine().playSound(Jukebox.Explosion);
		SGameEngine.get().addObject(boom, null, false);
		((SGAnimation)((SGTransform)boom.getGo()).getChild()).start();
		SGameEngine.get().addAction(new ExplosionEnd(boom, SGameEngine.get().getTime() + 500));

	}

	@Override
	public String getMessageData() {
		// TODO Auto-generated method stub
		return null;
	}

}
