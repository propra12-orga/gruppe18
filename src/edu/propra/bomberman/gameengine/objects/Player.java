package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.GameObject;
import edu.propra.bomberman.gameengine.Movable;
import edu.propra.bomberman.gameengine.MovingData;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class Player extends GameObject implements Movable {
	public static Area collisionArea;
	public static Area clipArea;
	public static BufferedImage[] images;

	private MovingData data ;

	
	public Player(int x,int y) {
		
		//Construct Collision Object for Player Object
		this.co=new CollisionObject();
		co.setCollisionArea((Area) Player.collisionArea.clone());
		co.setPrivot(this);
		
		//Construct Graphics Subgraph for Player Object
		this.go=new SGTransform();
		((SGTransform)this.go).getTransform().setToTranslation(x, y);
		SGImage leaf=new SGImage(images[0]);
		leaf.setClipArea(clipArea);
		((SGTransform)this.go).setChild(leaf);
		
		// Initialize Data to make Player Object moveable
		data=new MovingData(false);
		data.setActTrans(leaf.getActTrans());
		data.setSpeed(4);
		
	}

	@Override
	public void collisionWith(Object a) {
		if(a instanceof FixedBlock ){
			this.data.undoStepCollision(this.co);
			System.out.println("Movement Collision between "+this.toString()+" and FixedBlock "+ a.toString());
		}else if(a instanceof Player){
			this.data.undoStepCollision(this.co);
			System.out.println("Movement Collision between "+this.toString()+" and Player "+ a.toString());		
		}
	}
	
	@Override
	public MovingData getMovingData() {
		return this.data;
	}


	static{
		collisionArea=new Area(new Rectangle(0,0,40,40));
		clipArea=new Area(new Rectangle(0,0,40,40));
		images=new BufferedImage[4];
		try {
			images[0] = ImageIO.read(new File("src/resources/player_front.png"));
			images[1] = ImageIO.read(new File("src/resources/player_back.png"));
			images[2] = ImageIO.read(new File("src/resources/player_left.png"));
			images[3] = ImageIO.read(new File("src/resources/player_right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
