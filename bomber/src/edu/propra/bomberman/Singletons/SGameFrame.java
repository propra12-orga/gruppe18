package edu.propra.bomberman.Singletons;

import edu.propra.bomberman.ui.GameFrame;

public class SGameFrame {
	private static GameFrame instance=null;
	public static GameFrame get(){
		if(instance==null)instance=new GameFrame();
		return instance;
	}
}
