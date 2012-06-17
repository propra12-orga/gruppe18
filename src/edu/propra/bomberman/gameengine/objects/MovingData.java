package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGNode;
import edu.propra.bomberman.graphicengine.SGTransform;

public class MovingData {
	private boolean moving;
	private int speed;
	private AffineTransform step;
	private AffineTransform actTrans;
	private boolean step1;
	private int direction;

	public MovingData() {
		this.step = new AffineTransform();
		moving = false;
		step1 = false;
		lastStep=new AffineTransform();
	}

	private long dsc = 0;

	private long lastStart=0;
	private AffineTransform lastStep;
	private double lastStepSize;
	public void doStepCollision(CollisionObject co) {
		if (this.isMoving() && !this.step1) {
			dsc++;
			long time=System.currentTimeMillis();
			lastStepSize=((time-lastStart)/10)*speed;
			AffineTransform strech=new AffineTransform();
			if(direction==0){//y<0
				strech.setToScale(1, ((lastStepSize+(double)co.getCollisionArea().getBounds2D().getHeight())/(double)co.getCollisionArea().getBounds2D().getHeight()));
				strech.translate(0, -lastStepSize);
				lastStep.setToTranslation(0, -lastStepSize);
			}
			if(direction==90){//x>0
				strech.setToScale(((lastStepSize+(double)co.getCollisionArea().getBounds2D().getWidth())/(double)co.getCollisionArea().getBounds2D().getWidth()),1);
				strech.translate(0, 0);
				lastStep.setToTranslation(lastStepSize,0);
			}
			if(direction==180){//y>0
				strech.setToScale(1,((lastStepSize+(double)co.getCollisionArea().getBounds2D().getHeight())/(double)co.getCollisionArea().getBounds2D().getHeight()));
				strech.translate(0, 0);
				lastStep.setToTranslation(0, lastStepSize);
			}
			if(direction==270){//x<0
				strech.setToScale(((lastStepSize+(double)co.getCollisionArea().getBounds2D().getWidth())/(double)co.getCollisionArea().getBounds2D().getWidth()),1);
				strech.translate(-lastStepSize, 0);
				lastStep.setToTranslation(-lastStepSize,0);
			}
			co.setCollisionArea(new Area(strech.createTransformedShape(co.getCollisionArea())));
			step1=true;
			lastStart=time;
			
			/* Buggy Version for moving in all 360 directions
			AffineTransform trans = ((AffineTransform) this.getActTrans().clone());
			this.lastStep = (AffineTransform) step.clone();
			long dur= (System.currentTimeMillis()-lastStart)/10;
			this.lastStep.setToTranslation(step.getTranslateX()*dur*speed,step.getTranslateY()*dur*speed);
			trans.concatenate(this.lastStep);
			co.setCollisionArea(new Area(trans.createTransformedShape(Player.collisionArea)));
			this.step1 = true;
			lastStart=lastStart+dur*10;
			*/
		}
	}

	private long dsg = 0;

	public void doStepGraphic(GameObject go) {
		if (this.step1) {
			dsg++;
			((SGTransform) go.go).getTransform().concatenate(this.lastStep);
			AffineTransform unstretch=new AffineTransform();
			
			if(direction==0){
				unstretch.setToScale(1, 1-(lastStepSize/go.co.getCollisionArea().getBounds2D().getHeight()));
				unstretch.translate(0, -lastStepSize-lastStep.getTranslateY());
			}
			if(direction==90){
				unstretch.setToScale(1-(lastStepSize/go.co.getCollisionArea().getBounds2D().getWidth()), 1);
				unstretch.translate(-lastStepSize-lastStep.getTranslateX(),0 );
			}
			if(direction==180){
				unstretch.setToScale(1, 1-(lastStepSize/go.co.getCollisionArea().getBounds2D().getHeight()));
				unstretch.translate(0, -lastStepSize-lastStep.getTranslateY());
			}
			if(direction==270){
				unstretch.setToScale(1-(lastStepSize/go.co.getCollisionArea().getBounds2D().getWidth()), 1);
				unstretch.translate(-lastStepSize-lastStep.getTranslateX(),0 );
			}

			go.co.setCollisionArea(new Area(unstretch.createTransformedShape(go.co.getCollisionArea())));
			this.lastStep.setTransform(step);
			this.step1 = false;
		}
	}

	private long usc = 0;
	private AffineTransform back = new AffineTransform();

	public void undoStepCollision(CollisionObject cothis, CollisionObject other) {
		
		//Easy Version for up down left right navigation;
		Area intersection = (Area) cothis.getCollisionArea().clone();
		intersection.intersect(other.getCollisionArea());
		
		if(direction==0){
			lastStep.translate(0, intersection.getBounds2D().getHeight());
		}
		if(direction==90){
			lastStep.translate(0, -intersection.getBounds2D().getWidth());
		}
		if(direction==180){
			lastStep.translate(0, -intersection.getBounds2D().getHeight());
		}
		if(direction==270){
			lastStep.translate(0, intersection.getBounds2D().getWidth());
		}
		
		//TODO solve big jumps 
		
		/* Buggy Version for all 360 directions
		usc++;
		Area intersection = (Area) cothis.getCollisionArea().clone();
		intersection.intersect(other.getCollisionArea());

		double tx = lastStep.getTranslateX();
		double ty = lastStep.getTranslateY();
		double ntx = 0;
		double nty = 0;
		// correct rounding issue before using more than 8 directions
		Rectangle b = intersection.getBounds();
		
		//horizontaler aufprall auf eine Ecke
			if(Math.abs(tx)*2>b.height && ty==0){
				//Obere oder untere Ecken?
				Rectangle b2=(Rectangle) b.clone();
				b2.y=b2.y-b2.height;
				if(other.getCollisionArea().intersects(b2)){
					nty=b.height;
				}else{
					nty=-b.height;
				}
				ntx=tx;
			}else
		//Vertikaler Aufprall auf eine Ecke
			if(Math.abs(ty)*2>b.width && tx==0){
				Rectangle b2=(Rectangle) b.clone();
				b2.x=b2.x-b2.width;
				if(other.getCollisionArea().intersects(b2)){
					ntx=b.width;
				}else{
					ntx=-b.width;
				}
				nty=ty;
			}else
			if(b.width==cothis.getCollisionArea().getBounds().width && b.height==cothis.getCollisionArea().getBounds().height){
				System.out.println("ecke");
			}else
		//horizontaler Aufprall auf Wand
			if(Math.abs(tx)*2<b.height && ty==0){
				if(tx>0)
					ntx=-b.width;
				else 
					ntx=b.width;
			}else
		//vertikaler aufprall auf Wand
			if(Math.abs(ty)*2<b.width && tx==0){
				if(ty>0)
					nty=-b.height;
				else 
					nty=b.height;
			}else
		//diagonaler Aufprall auf Wand
			if(Math.abs(ty)*2<b.width || Math.abs(tx)*2<b.height){
				if(b.width<b.height){
					ntx=b.width;
				}else{
					nty=b.height;
				}
				if (ty > 0)
					nty = -nty;
				if (tx > 0)
					ntx = -ntx;
			}else
			if(true){
				//diagonaler Aufprall auf Ecke
				Rectangle b2=(Rectangle) b.clone();
				b2.y=b2.y-b2.height;
				if(other.getCollisionArea().intersects(b2)){
					nty=b.height;
				}else{
					nty=-b.height;
				}
				b2=(Rectangle) b.clone();
				b2.x=b2.x-b2.width;
				if(other.getCollisionArea().intersects(b2)){
					ntx=b.width;
				}else{
					ntx=-b.width;
				}
			}	
		back.setToTranslation(ntx, nty);

		this.lastStep.setToTranslation(tx + ntx, ty + nty);
		cothis.getCollisionArea().transform(back);
		*/
	}

	public boolean isMoving() {
		return this.moving;
	}

	public void startMoving(int direction) {
		if (this.direction != direction || !this.moving) {
			this.direction = direction;
			//double tx = this.speed * Math.sin(Math.toRadians(direction));
			//double ty = this.speed * Math.cos(Math.toRadians(direction));
			double tx = Math.sin(Math.toRadians(direction));
			double ty = Math.cos(Math.toRadians(direction));
			lastStart=System.currentTimeMillis();
			if ((tx < 0.0000001 && tx > -0.0000001))
				tx = 0;
			if ((ty < 0.0000001 && ty > -0.0000001))
				ty = 0;
			this.getStep().setToTranslation(tx, ty);
			this.moving = true;
		}
	}

	public void stopMoving() {
		direction = 0;
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

}