package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Bomberman;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGArea;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class Explosion extends GameObject {
	public static Area			collisionArea	= null;
	public static Area			clipArea		= null;
	public static BufferedImage[]	image			= null;
	private int					size;

	private SGArea				sDebug;

	public Explosion(int x, int y, int size,String oid) {
		this.setOid(oid);
		this.size = size;
		if(size>9)size=9;

		AffineTransform trans = new AffineTransform();
		trans.setToTranslation(x - (size - 1) * clipArea.getBounds2D().getWidth() / 2, y - (size - 1) * clipArea.getBounds2D().getHeight() / 2);
		// Construct Graphics Subgraph for Player Object
		this.go = new SGTransform();
		int imageIndex=((size-1)/2)-1;
		((SGTransform) this.go).getTransform().setTransform(trans);
		
		SGImage leaf = new SGImage(image[imageIndex]);
		leaf.setClipArea(this.calculateClipArea());
		((SGTransform) this.go).addChild(leaf);

		// Construct Collision Object for Player Object
		this.co = new CollisionObject();
		co.setPrivot(this);
		// leaf.debugColl=this.co;

		this.absTransform = (AffineTransform) trans.clone();
	}

	@Override
	public void collisionWith(Object a) {
		if (a instanceof FixedBlock || a instanceof IceBlock || a instanceof Wall) {
			reduceCollision(a);
			// System.out.println("Movement Collision between "+this.toString()+" and FixedBlock "+
			// a.toString());
		} else if (a instanceof Player) {
			// System.out.println("Movement Collision between "+this.toString()+" and Player "+
			// a.toString());
		} else {
			// System.out.println("Collision between "+this.toString()+" and "+
			// a.toString());
		}
	}

	private void reduceCollision(Object a) {
		if(a instanceof Explosion || a instanceof Bomb)return;
		Area partnerArea = ((GameObject) a).co.getCollisionArea();
		Area ownArea = this.co.getCollisionArea();
		Area intersectionArea = (Area) partnerArea.clone();
		intersectionArea.intersect(ownArea);
		Rectangle2D partnerBounds = partnerArea.getBounds2D();
		Rectangle2D ownBounds = ownArea.getBounds2D();
		Rectangle2D intersectionBounds = intersectionArea.getBounds2D();
		double partnerX = partnerBounds.getCenterX();
		double partnerY = partnerBounds.getCenterY();
		double ownX = ownBounds.getCenterX();
		double ownY = ownBounds.getCenterY();
		double intersectionX = intersectionBounds.getCenterX();
		double intersectionY = intersectionBounds.getCenterY();

		Area toSubtract = new Area();
		// works just for rectangular shapes
		// TODO solve bug : explosion is wrong reduced in corner
		Rectangle test;
		if (intersectionX < ownX) {
			// left part
			test = new Rectangle((int) (intersectionBounds.getX() + intersectionBounds.getWidth() - ownBounds.getWidth()), (int) intersectionBounds.getY(), (int) ownBounds.getWidth(), (int) intersectionBounds.getHeight());
			toSubtract.add(new Area(test));
		}
		if (intersectionX > ownX) {
			// right part
			test = new Rectangle((int) (intersectionBounds.getX()), (int) intersectionBounds.getY(), (int) ownBounds.getWidth(), (int) intersectionBounds.getHeight());
			toSubtract.add(new Area(test));
		}
		if (intersectionY < ownY) {
			// top part
			test = new Rectangle((int) (intersectionBounds.getX()), (int) (intersectionBounds.getY() + intersectionBounds.getHeight() - ownBounds.getHeight()), (int) intersectionBounds.getWidth(), (int) ownBounds.getHeight());
			toSubtract.add(new Area(test));
		}
		if (intersectionY > ownY) {
			// bottom part
			test = new Rectangle((int) (intersectionBounds.getX()), (int) (intersectionBounds.getY()), (int) intersectionBounds.getWidth(), (int) ownBounds.getHeight());
			toSubtract.add(new Area(test));
		}
		toSubtract.subtract(intersectionArea);
		this.co.getCollisionArea().subtract(toSubtract);
		toSubtract.add(intersectionArea);
		try {
			toSubtract.transform(this.absTransform.createInverse());
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((SGImage) ((SGTransform) this.go).getChild()).getClipArea().subtract(toSubtract);
		// maybe abs position needs to be changed
	}

	@Override
	public void initializeAbsolutePositions(AffineTransform trans) {
		this.absTransform.concatenate(trans);
		this.isAbsIntialized = true;
	}

	private Area calculateClipArea() {
		Area exp = new Area();
		exp.add(new Area(new Rectangle((int) clipArea.getBounds().getWidth() * (size - 1) / 2, 0, (int) clipArea.getBounds().getWidth(), (int) clipArea.getBounds().getHeight() * (size))));
		exp.add(new Area(new Rectangle(0, (int) clipArea.getBounds().getHeight() * ((size - 1) / 2), (int) clipArea.getBounds().getWidth() * (size), (int) clipArea.getBounds().getHeight())));
		return exp;
	}

	@Override
	public void initializeCollisions() {
		if (this.isAbsIntialized) {
			Area exp = new Area();
			exp.add(new Area(new Rectangle((int) collisionArea.getBounds().getWidth() * (size - 1) / 2, 0, (int) collisionArea.getBounds().getWidth(), (int) collisionArea.getBounds().getHeight() * (size))));
			exp.add(new Area(new Rectangle(0, (int) collisionArea.getBounds().getHeight() * ((size - 1) / 2), (int) collisionArea.getBounds().getWidth() * (size), (int) collisionArea.getBounds().getHeight())));
			this.co.setCollisionArea(exp.createTransformedArea(this.absTransform));
			this.collisionsInitialized = true;

		} else {
			System.err.println("FixedBlock.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}

	}

	static {
		collisionArea = new Area(new Rectangle(0, 0, 40, 40));
		clipArea = new Area(new Rectangle(0, 0, 40, 40));
		image=new BufferedImage[4];
		try {
			image[0] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion.png").openStream());
			image[1] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion2.PNG").openStream());
			image[2] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion3.PNG").openStream());
			image[3] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion4.PNG").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getMessageData() {
		int x= (int) ((SGTransform)this.go).getTransform().getTranslateX();
		int y= (int) ((SGTransform)this.go).getTransform().getTranslateY();
		return "Explosion "+x+" "+y+" "+this.getOid();
	}
}
