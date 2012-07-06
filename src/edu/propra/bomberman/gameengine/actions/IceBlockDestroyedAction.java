package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.IceBlock;

public class IceBlockDestroyedAction extends ActionObject {

	private static double	chance	= 10;
	public int				type	= -1;

	public IceBlockDestroyedAction(IceBlock actor, String aid, int type) {
		this.actor = actor;
		this.time = SGameEngine.get().getTime();
		this.type = type;
	}

	@Override
	public void action() {
		SGameEngine.get().removeObject((GameObject) this.actor);
		if (type != -1 && type!=0) {
			SGameEngine.get().addAction(new ItemDropAction(type, (int) ((IceBlock) actor).absTransform.getTranslateX(), (int) ((IceBlock) actor).absTransform.getTranslateY()), false);
		}
	}

	@Override
	public String getMessageData() {
		return null;
	}

}
