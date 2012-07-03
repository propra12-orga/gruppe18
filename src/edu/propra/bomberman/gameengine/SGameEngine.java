package edu.propra.bomberman.gameengine;

import edu.propra.bomberman.gameengine.objects.Player;

public class SGameEngine {
	static GameEngine	instance;

	public static GameEngine get() {
		if (instance == null) instance = new GameEngine();
		return SGameEngine.instance;
	}




}
