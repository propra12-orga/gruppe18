package edu.propra.bomberman.gameengine;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.graphicengine.SGNode;
import edu.propra.bomberman.graphicengine.SGTransform;

public class MovingData {
	private boolean moving;
	private int speed;
	private AffineTransform step;
	private AffineTransform actTrans;
	private boolean step1;

	public void doStepCollision(CollisionObject co) {
		if (this.isMoving() && !this.isStep1()) {
			AffineTransform trans = ((AffineTransform) this.getActTrans().clone());
			trans.concatenate(this.getStep());
			co.setCollisionArea(new Area(trans.createTransformedShape(Player.collisionArea)));
			this.step1=true;
		}
	}

	public void doStepGraphic(SGNode node) {
		if (this.isStep1()) {
			((SGTransform) node).getTransform().concatenate(this.getStep());
			this.step1=false;
		}
	}

	public void undoStepCollision(CollisionObject co) {
		co.setCollisionArea(new Area(this.getActTrans().createTransformedShape(Player.collisionArea)));
		this.step1=false;
	}

	public boolean isMoving() {
		return this.moving;
	}

	public void startMoving(int direction) {
		if (direction <= 90) {
			this.getStep().setToTranslation(this.getSpeed() * Math.cos(Math.toRadians(direction)), this.getSpeed() * Math.sin(Math.toRadians(direction)));
		} else if (direction <= 180) {
			this.getStep().setToTranslation(-this.getSpeed() * Math.cos(Math.toRadians(direction)), this.getSpeed() * Math.sin(Math.toRadians(direction)));
		} else if (direction <= 270) {
			this.getStep().setToTranslation(-this.getSpeed() * Math.cos(Math.toRadians(direction)), -this.getSpeed() * Math.sin(Math.toRadians(direction)));
		} else {
			this.getStep().setToTranslation(this.getSpeed() * Math.cos(Math.toRadians(direction)), -this.getSpeed() * Math.sin(Math.toRadians(direction)));
		}
		this.setMoving(true);
	}

	public void stopMoving() {
		this.setMoving(false);
		this.getStep().setToIdentity();
	}

	public MovingData(boolean step1) {
		this.step1 = step1;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public AffineTransform getStep() {
		return step;
	}

	public void setStep(AffineTransform step) {
		this.step = step;
	}

	public AffineTransform getActTrans() {
		return actTrans;
	}

	public void setActTrans(AffineTransform actTrans) {
		this.actTrans = actTrans;
	}

	public boolean isStep1() {
		return step1;
	}

	public void setStep1(boolean step1) {
		this.step1 = step1;
	}
}