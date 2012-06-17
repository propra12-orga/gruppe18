package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class Player extends GameObject implements Moveable {
	public static Area collisionArea;
	public static Area clipArea;
	public static BufferedImage[] images;
	private static BufferedImage deathImage;
	private boolean death=false;

	private MovingData data ;

	
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
		((SGTransform)this.go).setChild(leaf);
		
		//Construct Collision Object for Player Object
		this.co=new CollisionObject();
		co.setPrivot(this);
		
		this.absTransform=(AffineTransform) trans.clone();
		// Initialize Data to make Player Object moveable
		data=new MovingData();
		data.setActTrans(leaf.getActTrans());
		data.setSpeed(1);
		
	
	
	}

	@Override
	public void collisionWith(Object a) {
		if(a instanceof FixedBlock ){
			this.data.undoStepCollision(this.co,((FixedBlock) a).co);
			//System.out.println("Movement Collision between "+this.toString()+" and FixedBlock "+ a.toString());
		}else if(a instanceof Player){
			this.data.undoStepCollision(this.co,((Player) a).co);
			//System.out.println("Movement Collision between "+this.toString()+" and Player "+ a.toString());		
		}else if(a instanceof Wall){
			this.data.undoStepCollision(this.co,((Wall) a).co);
			//System.out.println("Movement Collision between "+this.toString()+" and Wall "+ a.toString());		
		}else if(a instanceof Bomb){
			this.data.undoStepCollision(this.co,((Bomb) a).co);
			//System.out.println("Movement Collision between "+this.toString()+" and Wall "+ a.toString());		
		}else if(a instanceof Explosion){
			death=true;
			SGImage deathNode=new SGImage();
			deathNode.setClipArea(clipArea);
			deathNode.setImage(deathImage);
			((SGTransform)this.go).removeChild();
			((SGTransform)this.go).setChild(deathNode);
			System.out.println("AHHHHH im Dead");
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
		collisionArea=new Area(new Rectangle(13,20,13,18));
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


	public void bomb() {
		System.out.println("boom");
		
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

	@Override
	public void ConditionChanged(int cond) {
		
	}
}
