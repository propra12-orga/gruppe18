package edu.propra.bomberman.gameengine;

public class SGameEngine {
	static GameEngine	instance;

	public static GameEngine get() {
		if (instance == null) instance = new GameEngine();
		return SGameEngine.instance;
	}

}
