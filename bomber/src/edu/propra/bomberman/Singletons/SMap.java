package edu.propra.bomberman.Singletons;

import edu.propra.bomberman.data.Map;

public class SMap {
	private static Map instance=null;
	public static Map get(){
		if(instance==null)instance=new Map();
		return instance;
	}
}
