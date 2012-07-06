package edu.propra.bomberman.gameengine.actions;

public abstract class ActionObject implements Comparable<ActionObject> {
	protected Object	actor;
	String				aid;
	protected Long		time;

	public abstract void action();

	@Override
	public int compareTo(ActionObject o) {
		return (int) (this.time - o.time);
	}

	public abstract String getMessageData();

	public long getTime() {
		return time;
	}
}
