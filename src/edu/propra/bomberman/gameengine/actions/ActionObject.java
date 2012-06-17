package edu.propra.bomberman.gameengine.actions;

public abstract class ActionObject implements Comparable<ActionObject>{
	protected Long time;
	protected Object actor;
	
	public abstract void action();

	@Override
	public int compareTo(ActionObject o) {
		return (int)(o.time-this.time);
	}

	public long getTime() {
		return time;
	}
	
	
}
