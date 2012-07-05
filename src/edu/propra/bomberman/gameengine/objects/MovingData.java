package edu.propra.bomberman.gameengine.objects;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.graphicengine.SGTransform;

public class MovingData {
	private AffineTransform	actTrans;
	private AffineTransform	back		= new AffineTransform();
	private boolean			blocked		= false;
	boolean					coli		= false;
	private int				direction;
	private long			dsc			= 0;
	private long			dsg			= 0;

	private Rectangle2D		lastColRect;

	private long			lastStart	= 0;

	private AffineTransform	lastStep;
	private double			lastStepSize;
	private boolean			moving;
	public boolean			now			= false;
	private GameObject		parent;
	private int				speed;

	private AffineTransform	step;

	private boolean			step1;

	private long			usc			= 0;

	public MovingData(GameObject parent) {
		this.parent = parent;
		this.step = new AffineTransform();
		moving = false;
		step1 = false;
		lastStep = new AffineTransform();
	}

	public void block() {
		this.blocked = true;
	}

	public void doStepCollision() {
		if (this.moving) {
			if (!this.step1 && !this.blocked) {
				dsc++;
				long time = SGameEngine.get().getTime();
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

	public void doStepGraphic(GameObject go) {
		if (!this.blocked) {
			if (this.step1) {
				dsg++;
				((SGTransform) go.go).getTransform().concatenate(this.lastStep);
				parent.absTransform.concatenate(this.lastStep);
				parent.co.setCollisionArea(Player.collisionArea.createTransformedArea(parent.absTransform));
				this.lastStep.setToIdentity();
				this.step1 = false;
			}
		}
	}

	public AffineTransform getActTrans() {
		return actTrans;
	}

	public int getDirection() {
		return this.direction;
	}

	public int getSpeed() {
		return speed;
	}

	public AffineTransform getStep() {
		return step;
	}

	public boolean isMoving() {
		return this.moving;
	}

	public boolean isStep1() {
		return step1;
	}

	public void setActTrans(AffineTransform actTrans) {
		this.actTrans = actTrans;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setStep(AffineTransform step) {
		this.step = step;
	}

	public void setStep1(boolean step1) {
		this.step1 = step1;
	}

	public void startMoving(int direction) {
		if (this.direction != direction || !this.moving) {
			this.direction = direction;
			// double tx = this.speed * Math.sin(Math.toRadians(direction));
			// double ty = this.speed * Math.cos(Math.toRadians(direction));
			double tx = Math.sin(Math.toRadians(direction));
			double ty = Math.cos(Math.toRadians(direction));
			lastStart = SGameEngine.get().getTime();
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

	

}