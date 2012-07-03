package edu.propra.bomberman.usercontrolengine;

import java.util.HashMap;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.actions.ActionObject;
import edu.propra.bomberman.gameengine.actions.BombDownAction;
import edu.propra.bomberman.gameengine.actions.StartMoveAction;
import edu.propra.bomberman.gameengine.actions.StopMoveAction;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGTransform;

public class PlayerListener extends Listener {
	private int		keyUp;
	private int		keyLeft;
	private int		keyRight;
	private int		keyDown;
	private int		keyBomb;

	private boolean	spacedown	= false;

	public PlayerListener(Object player, int keyUp, int keyDown, int keyLeft, int keyRight, int keyBomb) {
		this.actor = player;
		this.keyUp = keyUp;
		this.keyDown = keyDown;
		this.keyRight = keyRight;
		this.keyLeft = keyLeft;
		this.keyBomb = keyBomb;
	}

	public void login(UserControlEngine ucE) {
		ucE.addListener(keyDown, this);
		ucE.addListener(keyUp, this);
		ucE.addListener(keyRight, this);
		ucE.addListener(keyLeft, this);
		ucE.addListener(keyBomb, this);
	}

	@Override
	public ActionObject keyDownEvent(HashMap<Integer, Integer> keysDown, Integer keyCode) {
		boolean up, left, down, right, upleft, upright, downleft, downright;
		//down = keysDown.containsKey(keyUp);
		//up = keysDown.containsKey(keyDown);
		//left = keysDown.containsKey(keyLeft);
		//right = keysDown.containsKey(keyRight);
		down = keyCode==keyUp;
		up = keyCode==keyDown;
		left = keyCode==keyLeft;
		right = keyCode==keyRight;
				
		
		//if (up && down) {
		//	up = false;
		//	down = false;
		//}
		//if (left && right) {
		//	left = false;
		//	right = false;
		//}

		upleft = (up && left);
		upright = (up && right);
		downleft = (down && left);
		downright = (down && right);

		if (keyCode != keyBomb) {
		//	if (upleft) {
		//		return new StartMoveAction(315, this.actor, System.currentTimeMillis());
		//	} else if (upright) {
		//		return new StartMoveAction(45, this.actor, System.currentTimeMillis());
		//	} else if (downleft) {
		//		return new StartMoveAction(225, this.actor, System.currentTimeMillis());
		//	} else if (downright) {
		//		return new StartMoveAction(135, this.actor, System.currentTimeMillis());
		//	} else if (down) {
			if(down){
				return new StartMoveAction(180, this.actor, System.currentTimeMillis());
			} else if (up) {
				return new StartMoveAction(0, this.actor, System.currentTimeMillis());
			} else if (right) {
				return new StartMoveAction(90, this.actor, System.currentTimeMillis());
			} else if (left) {
				return new StartMoveAction(270, this.actor, System.currentTimeMillis());
			} else {
				return new StopMoveAction(this.actor, System.currentTimeMillis());
			}
		} else {
			if (!spacedown) {
				spacedown = true;
				return new BombDownAction(this.actor, System.currentTimeMillis(),SGameEngine.get().getActionID());
			}
		}
		return null;
	}

	@Override
	public ActionObject keyUpEvent(HashMap<Integer, Integer> keysDown, Integer keyRelease) {
		boolean up, left, down, right, upleft, upright, downleft, downright;
		down = keysDown.containsKey(keyUp);
		up = keysDown.containsKey(keyDown);
		left = keysDown.containsKey(keyLeft);
		right = keysDown.containsKey(keyRight);

		if (up && down) {
			up = false;
			down = false;
		}
		if (left && right) {
			left = false;
			right = false;
		}

		upleft = (up && left);
		upright = (up && right);
		downleft = (down && left);
		downright = (down && right);

		if (keyRelease != keyBomb) {
			if (upleft) {
				return new StartMoveAction(315, this.actor, System.currentTimeMillis());
			} else if (upright) {
				return new StartMoveAction(45, this.actor, System.currentTimeMillis());
			} else if (downleft) {
				return new StartMoveAction(225, this.actor, System.currentTimeMillis());
			} else if (downright) {
				return new StartMoveAction(135, this.actor, System.currentTimeMillis());
			} else if (down) {
				return new StartMoveAction(180, this.actor, System.currentTimeMillis());
			} else if (up) {
				return new StartMoveAction(0, this.actor, System.currentTimeMillis());
			} else if (right) {
				return new StartMoveAction(90, this.actor, System.currentTimeMillis());
			} else if (left) {
				return new StartMoveAction(270, this.actor, System.currentTimeMillis());
			} else {
				return new StopMoveAction(this.actor, System.currentTimeMillis());
			}
		} else {
			spacedown = false;
		}
		return null;
	}

	@Override
	public ActionObject keyTypedEvent(Integer keyCode) {
		if (keyCode == keyBomb && !spacedown) {
			return new BombDownAction(this.actor, System.currentTimeMillis(),SGameEngine.get().getActionID());
		}
		return null;
	}

}
