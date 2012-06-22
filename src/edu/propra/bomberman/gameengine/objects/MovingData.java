package edu.propra.bomberman.gameengine.objects;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.graphicengine.SGTransform;

public class MovingData {
	private boolean			moving;
	private int				speed;
	private AffineTransform	step;
	private AffineTransform	actTrans;
	private boolean			step1;
	private int				direction;
	private GameObject		parent;

	public MovingData(GameObject parent) {
		this.parent = parent;
		this.step = new AffineTransform();
		moving = false;
		step1 = false;
		lastStep = new AffineTransform();
	}

	private long			dsc			= 0;

	private long			lastStart	= 0;
	private AffineTransform	lastStep;
	private double			lastStepSize;
	private Rectangle2D		lastColRect;
	public boolean			now			= false;
	private boolean			blocked		= false;

	public void doStepCollision() {
		if (this.moving) {
			if (!this.step1 && !this.blocked) {
				dsc++;
				long time = System.currentTimeMillis();
				lastStepSize = ((time - lastStart) / 10) * speed;
				AffineTransform strech = new AffineTransform();
				Area colArea = parent.co.getCollisionArea();
				Rectangle2D colBounds = colArea.getBounds2D();

				/*
				 * if (direction == 180) {// y<0 // strech.setToScale(1, //
				 * ((lastStepSize
				 * +(double)colBounds.getHeight())/(double)colBounds
				 * .getHeight())); lastStep.setToTranslation(0, -lastStepSize);
				 * } if (direction == 90) {// x>0 //
				 * strech.setToScale(((lastStepSize
				 * +(double)colBounds.getWidth())
				 * /(double)colBounds.getWidth()),1);
				 * lastStep.setToTranslation(lastStepSize, 0); } if (direction
				 * == 0) {// y>0 //
				 * strech.setToScale(1,((lastStepSize+(double)colBounds
				 * .getHeight())/(double)colBounds.getHeight()));
				 * lastStep.setToTranslation(0, lastStepSize); } if (direction
				 * == 270) {// x<0 //
				 * strech.setToScale(((lastStepSize+(double)colBounds
				 * .getWidth())/(double)colBounds.getWidth()),1);
				 * lastStep.setToTranslation(-lastStepSize, 0); }
				 */
				lastStep.setToTranslation(lastStepSize * Math.sin(Math.toRadians(direction)), lastStepSize * Math.cos(Math.toRadians(direction)));
				// strech.concatenate(lastStep);
				// strech.concatenate(parent.absTransform);
				lastStep = SGameEngine.get().getCollisionEngine().checkCollisionsDirectly(parent.co, lastStep);
				// AffineTransform res=(AffineTransform) lastStep.clone();
				// res.concatenate(lastStep);
				// parent.co.setCollisionArea(Player.collisionArea.createTransformedArea(res));
				// colArea.transform(lastStep);

				step1 = true;
				lastStart = time;
			}
		}
	}

	private long	dsg	= 0;

	public void doStepGraphic(GameObject go) {
		if (!this.blocked) {
			if (this.step1) {
				dsg++;
				((SGTransform) go.go).getTransform().concatenate(this.lastStep);
				parent.absTransform.concatenate(this.lastStep);
				// if (parent instanceof Player)
				parent.co.setCollisionArea(Player.collisionArea.createTransformedArea(parent.absTransform));
				this.lastStep.setToIdentity();
				this.step1 = false;
			}
		}
	}

	private long			usc		= 0;
	private AffineTransform	back	= new AffineTransform();
	boolean					coli	= false;

	public void undoStepCollision(CollisionObject cothis, CollisionObject other) {

		// Easy Version for up down left right navigation;
		Area intersection = (Area) cothis.getCollisionArea().clone();
		intersection.intersect(other.getCollisionArea());

		if (direction == 180) {
			lastStep.translate(0, intersection.getBounds2D().getHeight() + 1);
		}
		if (direction == 90) {
			lastStep.translate(-intersection.getBounds2D().getWidth() - 1, 0);
		}
		if (direction == 0) {
			lastStep.translate(0, -intersection.getBounds2D().getHeight() - 1);
		}
		if (direction == 270) {
			lastStep.translate(intersection.getBounds2D().getWidth(), 0);
		}
		// this.lastStep.setToIdentity();
		step1 = true;
		coli = true;

	}

	public boolean isMoving() {
		return this.moving;
	}

	public void startMoving(int direction) {
		if (this.direction != direction || !this.moving) {
			this.direction = direction;
			// double tx = this.speed * Math.sin(Math.toRadians(direction));
			// double ty = this.speed * Math.cos(Math.toRadians(direction));
			double tx = Math.sin(Math.toRadians(direction));
			double ty = Math.cos(Math.toRadians(direction));
			lastStart = System.currentTimeMillis();
			if ((tx < 0.0000001 && tx > -0.0000001)) tx = 0;
			if ((ty < 0.0000001 && ty > -0.0000001)) ty = 0;
			this.getStep().setToTranslation(tx, ty);
			this.moving = true;
		}
	}

	public void stopMoving() {
		this.moving = false;
		this.step.setToIdentity();
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

	public int getDirection() {
		return this.direction;
	}

	public void block() {
		this.blocked = true;
	}

}