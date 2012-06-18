package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class Player extends GameObject implements Moveable {
	public static Area collisionArea;
	public static Area clipArea;
	public static BufferedImage[] images;
	private static BufferedImage deathImage;
	private boolean death=false;
	public int bombCounter=3;
	private MovingData data ;
	private int bombMax=3;

	
	public Player(int x,int y) {
		

		AffineTransform trans=new AffineTransform();
		trans.setToTranslation(x, y);
		
		//Construct Graphics Subgraph for Player Object
		this.go=new SGTransform();
		((SGTransform)this.go).getTransform().setTransform(trans);
		//SGImage leaf=new SGImage(images[0]);
		SGAnimation leaf=new SGAnimation(images, 1000);
		leaf.setRepeat(true);
		leaf.setClipArea(clipArea);
		((SGTransform)this.go).addChild(leaf);
		
		//Construct Collision Object for Player Object
		this.co=new CollisionObject();
		co.setPrivot(this);
		
		this.absTransform=(AffineTransform) trans.clone();
		// Initialize Data to make Player Object moveable
		data=new MovingData(this);
		data.setActTrans(leaf.getActTrans());
		data.setSpeed(5);
		
	
	
	}

	@Override
	public void collisionWith(Object a) {
		if(a instanceof FixedBlock ){
			this.data.undoStepCollision(this.co,((FixedBlock) a).co);
			//System.out.println("Movement Collision between "+this.toString()+" and FixedBlock "+ a.toString());
		}if(a instanceof IceBlock ){
			this.data.undoStepCollision(this.co,((IceBlock) a).co);
			//System.out.println("Movement Collision between "+this.toString()+" and FixedBlock "+ a.toString());
		}else if(a instanceof Player){
			this.data.undoStepCollision(this.co,((Player) a).co);
			//System.out.println("Movement Collision between "+this.toString()+" and Player "+ a.toString());		
		}else if(a instanceof Wall){
			this.data.undoStepCollision(this.co,((Wall) a).co);
			//System.out.println("Movement Collision between "+this.toString()+" and Wall "+ a.toString());		
		}else if(a instanceof Bomb){
			if(((Bomb)a).owner!=this || ((Bomb)a).playerOut){
				this.data.undoStepCollision(this.co,((Bomb) a).co);
			}else{
			}
			//System.out.println("Movement Collision between "+this.toString()+" and Wall "+ a.toString());		
		}else if(a instanceof Explosion){
			death=true;
			this.data.block();
			SGImage deathNode=new SGImage();
			deathNode.setClipArea(clipArea);
			deathNode.setImage(deathImage);
			((SGTransform)this.go).addChild(deathNode);
			System.out.println("AHHHHH!!!! I'm Dead");
			//System.out.println("Movement Collision between "+this.toString()+" and Wall "+ a.toString());		
		}else{
			//System.out.println("Collision between "+this.toString()+" and "+ a.toString());			
		}
	}
	
	@Override
	public MovingData getMovingData() {
		return this.data;
	}


	static{
		collisionArea=new Area(new Rectangle(13,6,13,28));
		clipArea=new Area(new Rectangle(0,0,40,40));
		images=new BufferedImage[4];
		
		try {
			images[0] = ImageIO.read(new File("src/resources/player_front.png"));
			images[1] = ImageIO.read(new File("src/resources/player_back.png"));
			images[2] = ImageIO.read(new File("src/resources/player_left.png"));
			images[3] = ImageIO.read(new File("src/resources/player_right.png"));
			deathImage = ImageIO.read(new File("src/resources/asche.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}


	public Bomb bombDown() {
		if(bombCounter>0){
			bombCounter--;
			data.now=true;
			int dir=this.data.getDirection();
			double x=0,y=0;
		//	if(dir==0)y= Player.clipArea.getBounds2D().getHeight();
		//	if(dir==90)x=Player.clipArea.getBounds2D().getWidth();
		//	if(dir==180)y=-Bomb.clipArea.getBounds2D().getHeight();
		//	if(dir==270)x=-Bomb.clipArea.getBounds2D().getWidth();
			return new Bomb(this,(int)(this.absTransform.getTranslateX()+x),(int)(this.absTransform.getTranslateY()+y));
		}
		return null;
	}
	public void bombUp(){
		if(bombCounter<=bombMax)
			bombCounter++;
	}

	@Override
	public void initializeCollisions() {
		if(this.isAbsIntialized){
			this.co.setCollisionArea(collisionArea.createTransformedArea(this.absTransform));
			this.collisionsInitialized=true;
		}else{
			System.err.println("Player.initializeCollisions()");
			System.err.println("    AbsolutePositions are not initialized");		
		}
	}
	public Area getBaseCollisionArea(){
		return Player.collisionArea;
	}

	@Override
	public void ConditionChanged(int cond) {
		
	}
}
