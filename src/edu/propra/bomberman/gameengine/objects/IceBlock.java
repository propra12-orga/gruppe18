package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Bomberman;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.actions.IceBlockDestroyedAction;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class IceBlock extends GameObject {
	public static Area			clipArea		= null;
	public static Area			collisionArea	= null;
	public static BufferedImage	image			= null;
	static {
		collisionArea = new Area(new Rectangle(0, 0, 40, 40));
		clipArea = new Area(new Rectangle(0, 0, 40, 40));
		try {
			image = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/weichblock.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int					chance			= 4;
	private int					counter			= 1;

	public int					type			= -1;

	public int					types			= 3;

	public IceBlock(int x, int y, String oid, int type) {
		this.setOid(oid);

		if (type == -1) {
			int rand = (int) (Math.random() * (chance - 1) + 1d);
			if (rand == chance - 1) {
				this.type = (int) (Math.random() * (types - 1) + 1d);
			}
		}
		System.out.println("itemtype: " + this.type);

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
		if (a instanceof Explosion) {
			counter--;
			if (counter == 0) {
				SGameEngine.get().addAction(new IceBlockDestroyedAction(this, SGameEngine.get().getActionID(), type));
			}
		}
	}

	@Override
	public String getMessageData() {
		int x = (int) ((SGTransform) this.go).getTransform().getTranslateX();
		int y = (int) ((SGTransform) this.go).getTransform().getTranslateY();
		return "IceBlock " + x + " " + y + " " + this.getOid() + " " + type;
	}

	@Override
	public void initializeCollisions() {
		if (this.isAbsIntialized) {
			this.co.setCollisionArea(collisionArea.createTransformedArea(this.absTransform));
			this.collisionsInitialized = true;
		} else {
			System.err.println("IceBlock.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}

	}
}
