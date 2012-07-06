package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Bomberman;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.actions.PlayerDeadAction;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGArea;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGLeaf;
import edu.propra.bomberman.graphicengine.SGTransform;

public class Explosion extends GameObject {
	public static Area				clipArea		= null;
	public static Area				collisionArea	= null;
	public static BufferedImage[][]	image			= null;
	static {
		collisionArea = new Area(new Rectangle(0, 0, 40, 40));
		clipArea = new Area(new Rectangle(0, 0, 40, 40));
		image = new BufferedImage[4][];
		try {
			image[0]=new BufferedImage[24];
			image[0][0] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy1.png").openStream());
			image[0][1] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy2.png").openStream());
			image[0][2] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy3.png").openStream());
			image[0][3] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy4.png").openStream());
			image[0][4] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy5.png").openStream());
			image[0][5] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy6.png").openStream());
			image[0][6] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy7.png").openStream());
			image[0][7] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy8.png").openStream());
			image[0][8] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy9.png").openStream());
			image[0][9] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy10.png").openStream());
			image[0][10] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy11.png").openStream());
			image[0][11] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy12.png").openStream());
			image[0][12] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy13.png").openStream());
			image[0][13] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy14.png").openStream());
			image[0][14] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy15.png").openStream());
			image[0][15] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy16.png").openStream());
			image[0][16] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy17.png").openStream());
			image[0][17] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy18.png").openStream());
			image[0][18] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy19.png").openStream());
			image[0][19] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy20.png").openStream());
			image[0][20] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy21.png").openStream());
			image[0][21] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy22.png").openStream());
			image[0][22] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy23.png").openStream());
			image[0][23] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/newwy24.png").openStream());
			image[1]=new BufferedImage[24];
			image[1][0] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_1.png").openStream());
			image[1][1] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_2.png").openStream());
			image[1][2] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_3.png").openStream());
			image[1][3] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_4.png").openStream());
			image[1][4] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_5.png").openStream());
			image[1][5] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_6.png").openStream());
			image[1][6] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_7.png").openStream());
			image[1][7] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_8.png").openStream());
			image[1][8] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_9.png").openStream());
			image[1][9] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_10.png").openStream());
			image[1][10] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_11.png").openStream());
			image[1][11] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_12.png").openStream());
			image[1][12] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_13.png").openStream());
			image[1][13] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_14.png").openStream());
			image[1][14] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_15.png").openStream());
			image[1][15] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_16.png").openStream());
			image[1][16] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_17.png").openStream());
			image[1][17] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_18.png").openStream());
			image[1][18] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_19.png").openStream());
			image[1][19] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_20.png").openStream());
			image[1][20] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_21.png").openStream());
			image[1][21] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_22.png").openStream());
			image[1][22] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_23.png").openStream());
			image[1][23] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_2_24.png").openStream());
			image[2]=new BufferedImage[24];
			image[2][0] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_1.png").openStream());
			image[2][1] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_2.png").openStream());
			image[2][2] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_3.png").openStream());
			image[2][3] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_4.png").openStream());
			image[2][4] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_5.png").openStream());
			image[2][5] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_6.png").openStream());
			image[2][6] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_7.png").openStream());
			image[2][7] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_8.png").openStream());
			image[2][8] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_9.png").openStream());
			image[2][9] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_10.png").openStream());
			image[2][10] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_11.png").openStream());
			image[2][11] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_12.png").openStream());
			image[2][12] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_13.png").openStream());
			image[2][13] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_14.png").openStream());
			image[2][14] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_15.png").openStream());
			image[2][15] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_16.png").openStream());
			image[2][16] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_17.png").openStream());
			image[2][17] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_18.png").openStream());
			image[2][18] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_19.png").openStream());
			image[2][19] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_20.png").openStream());
			image[2][20] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_21.png").openStream());
			image[2][21] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_22.png").openStream());
			image[2][22] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_23.png").openStream());
			image[2][23] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_3_24.png").openStream());
			image[3]=new BufferedImage[24];
			image[3][0] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_1.png").openStream());
			image[3][1] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_2.png").openStream());
			image[3][2] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_3.png").openStream());
			image[3][3] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_4.png").openStream());
			image[3][4] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_5.png").openStream());
			image[3][5] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_6.png").openStream());
			image[3][6] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_7.png").openStream());
			image[3][7] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_8.png").openStream());
			image[3][8] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_9.png").openStream());
			image[3][9] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_10.png").openStream());
			image[3][10] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_11.png").openStream());
			image[3][11] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_12.png").openStream());
			image[3][12] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_13.png").openStream());
			image[3][13] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_14.png").openStream());
			image[3][14] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_15.png").openStream());
			image[3][15] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_16.png").openStream());
			image[3][16] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_17.png").openStream());
			image[3][17] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_18.png").openStream());
			image[3][18] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_19.png").openStream());
			image[3][19] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_20.png").openStream());
			image[3][20] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_21.png").openStream());
			image[3][21] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_22.png").openStream());
			image[3][22] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_23.png").openStream());
			image[3][23] = ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/explosion_size_4_24.png").openStream());
			} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private SGArea					sDebug;

	private int						size;

	public Explosion(int x, int y, int size, String oid) {
		this.setOid(oid);
		if (size > 9) size = 9;
		this.size = size;

		AffineTransform trans = new AffineTransform();
		trans.setToTranslation(x - (size - 1) * clipArea.getBounds2D().getWidth() / 2, y - (size - 1) * clipArea.getBounds2D().getHeight() / 2);
		// Construct Graphics Subgraph for Player Object
		this.go = new SGTransform();
		int imageIndex = ((size - 1) / 2) - 1;
		((SGTransform) this.go).getTransform().setTransform(trans);

		SGAnimation leaf = new SGAnimation(image[imageIndex],500);
		leaf.setClipArea(this.calculateClipArea());
		((SGTransform) this.go).addChild(leaf);

		// Construct Collision Object for Player Object
		this.co = new CollisionObject();
		co.setPrivot(this);
		// leaf.debugColl=this.co;

		this.absTransform = (AffineTransform) trans.clone();
	}

	private Area calculateClipArea() {
		Area exp = new Area();
		exp.add(new Area(new Rectangle((int) clipArea.getBounds().getWidth() * (size - 1) / 2, 0, (int) clipArea.getBounds().getWidth(), (int) clipArea.getBounds().getHeight() * (size))));
		exp.add(new Area(new Rectangle(0, (int) clipArea.getBounds().getHeight() * ((size - 1) / 2), (int) clipArea.getBounds().getWidth() * (size), (int) clipArea.getBounds().getHeight())));
		return exp;
	}

	@Override
	public void collisionWith(Object a) {
		if (a instanceof FixedBlock || a instanceof IceBlock ) {
			reduceCollision(a);
		} else if (a instanceof Player) {
			
			if(SGameEngine.get().getNetworkEngine().isNetworkGame()){
				if(a == SGameEngine.get().you){
					((Player)a).getData().block();
					SGameEngine.get().addAction(new PlayerDeadAction((Player) a), true);
				}
			}else {
				((Player)a).getData().block();
				SGameEngine.get().addAction(new PlayerDeadAction((Player) a), false);
			}
		} 
	}

	@Override
	public String getMessageData() {
		int x = (int) ((SGTransform) this.go).getTransform().getTranslateX();
		int y = (int) ((SGTransform) this.go).getTransform().getTranslateY();
		return "Explosion " + x + " " + y + " " + this.getOid();
	}

	@Override
	public void initializeAbsolutePositions(AffineTransform trans) {
		this.absTransform.concatenate(trans);
		this.isAbsIntialized = true;
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

	private void reduceCollision(Object a) {
		if (a instanceof Explosion || a instanceof Bomb) return;
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
		((SGLeaf) ((SGTransform) this.go).getChild()).getClipArea().subtract(toSubtract);
		// maybe abs position needs to be changed
	}
}
