package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
/**
 * Itemclass to extend explosion
 */
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Bomberman;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.actions.PlayerBombGrowAction;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class BombGrowItem extends GameObject {
	public static Area			clipArea		= null;
	public static Area			collisionArea	= null;
	public static BufferedImage	image			= null;

	static {
		collisionArea = new Area(new Rectangle(0, 0, 40, 40));
		clipArea = new Area(new Rectangle(0, 0, 40, 40));
		try {
			image = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/handschuh.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BombGrowItem(int x, int y, String oid) {
		this.setOid(oid);

		AffineTransform trans = new AffineTransform();
		trans.setToTranslation(x, y);
		// Construct Graphics Subgraph for Player Object
		this.go = new SGTransform();
		((SGTransform) this.go).getTransform().setTransform(trans);
		SGImage leaf = new SGImage(image);
		leaf.setClipArea(clipArea);
		((SGTransform) this.go).addChild(leaf);

		// Construct Collision Object for Player Object
		this.co = new CollisionObject();
		co.setPrivot(this);

		this.absTransform = (AffineTransform) trans.clone();
	}

	@Override
	public void collisionWith(Object a) {
		if (a instanceof Player) {
			if(SGameEngine.get().getNetworkEngine().isNetworkGame()){
				if(a == SGameEngine.get().you){
					SGameEngine.get().addAction(new PlayerBombGrowAction((Player) a, this),true);
				}
			}else{
				SGameEngine.get().addAction(new PlayerBombGrowAction((Player) a, this),false);
			}
		}
	}

	@Override
	public String getMessageData() {
		int x = (int) ((SGTransform) this.go).getTransform().getTranslateX();
		int y = (int) ((SGTransform) this.go).getTransform().getTranslateY();
		return "BombUpItem " + x + " " + y + " " + this.getOid();
	}

	@Override
	public void initializeCollisions() {
		if (this.isAbsIntialized) {
			this.co.setCollisionArea(collisionArea.createTransformedArea(this.absTransform));
			this.collisionsInitialized = true;
		} else {
			System.err.println("Item.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}

	}

}
