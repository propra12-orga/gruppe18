package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Bomberman;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class FixedBlock extends GameObject {
	public static Area			collisionArea	= null;
	public static Area			clipArea		= null;
	public static BufferedImage	image			= null;

	public FixedBlock(int x, int y, String oid) {
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
	public void collisionWith(Object a) {}

	static {
		collisionArea = new Area(new Rectangle(0, 0, 40, 40));
		clipArea = new Area(new Rectangle(0, 0, 40, 40));
		try {
			image = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/festerblock.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initializeCollisions() {
		if (this.isAbsIntialized) {
			this.co.setCollisionArea(collisionArea.createTransformedArea(this.absTransform));
			this.collisionsInitialized = true;
		} else {
			System.err.println("FixedBlock.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}

	}


	@Override
	public String getMessageData() {
		int x= (int) ((SGTransform)this.go).getTransform().getTranslateX();
		int y= (int) ((SGTransform)this.go).getTransform().getTranslateY();
		return "FixedBlock "+x+" "+y+" "+this.getOid();
	}
}
