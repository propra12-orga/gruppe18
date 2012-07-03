package edu.propra.bomberman.gameengine.actions;

public abstract class ActionObject implements Comparable<ActionObject> {
	protected Long		time;
	protected Object	actor;
	String	aid;

	public abstract void action();

	@Override
	public int compareTo(ActionObject o) {
		return (int) (this.time - o.time);
	}

	public long getTime() {
		return time;
	}

	public abstract String getMessageData();
}
