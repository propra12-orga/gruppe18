package edu.propra.bomberman.collisionengine;

import java.awt.geom.Area;

public class CollisionObject {
	private Area	CollisionArea;
	private Object	privot;

	/**
	 * collisionArea getter, setter
	 */
	public Area getCollisionArea() {
		return CollisionArea;
	}

	/**
	 * gibt privot zurueck
	 */
	public Object getPrivot() {
		return privot;
	}

	/**
	 * @param collisionArea
	 *            the collisionArea to set
	 */
	public void setCollisionArea(Area collisionArea) {
		CollisionArea = collisionArea;
	}

	/**
	 * setzt privot
	 */
	public void setPrivot(Object privot) {
		this.privot = privot;
	}

}
