package edu.propra.bomberman.usercontrolengine;

import java.util.HashMap;

import edu.propra.bomberman.gameengine.actions.ActionObject;

public abstract class Listener {
	protected Object	actor;

	public abstract ActionObject keyDownEvent(HashMap<Integer, Integer> keysDown, Integer keyCode);

	public abstract ActionObject keyTypedEvent(Integer keyCode);

	public abstract ActionObject keyUpEvent(HashMap<Integer, Integer> keysDown, Integer keyRelease);
}
