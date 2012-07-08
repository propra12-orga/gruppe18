package edu.propra.bomberman.gameengine.actions;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.objects.IceBlock;

public class AddObjectAction extends ActionObject {

	int x,y,type;
	String object;
	public AddObjectAction(long time,String aid,int x,int y,int type,String object) {
		this.time=time;
		this.aid=aid;	
		this.type=type;
		this.x=x;
		this.y=y;
		this.object=object;
	}

	@Override
	public String getMessageData() {
		return "AddObjectAction "+time+" "+aid;
	}

	@Override
	public void action() {
		SGameEngine.get().addObject(new IceBlock(x, y, "oid"+SGameEngine.get().ObjectCounter, type), null, true);		
	}

}
