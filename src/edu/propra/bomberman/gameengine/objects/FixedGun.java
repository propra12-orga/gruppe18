package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class FixedGun extends GameObject {
	public static Area collisionArea=null;
	public static Area clipArea=null;
	public static BufferedImage image=null;

	public FixedGun(int x,int y) {
		
		AffineTransform trans=new AffineTransform();
		trans.setToTranslation(x, y);
		//Construct Graphics Subgraph for Player Object
		this.go=new SGTransform();
		((SGTransform)this.go).getTransform().setTransform(trans);
		SGImage leaf=new SGImage(image);
		leaf.setClipArea(clipArea);
		((SGTransform)this.go).setChild(leaf);
	
		//Construct Collision Object for Player Object
		this.co=new CollisionObject();
		co.setPrivot(this);
		
		this.absTransform=(AffineTransform) trans.clone();
	}

/* Laserkanone Pseudocode zielt auf player Koordinaten
	
/* w h feste breite

	private static void fixedshooter() {
		double x = Player.x;
		double y = Player.y;

	

		counter++;
		double ang = Math.atan2(y, x) * 180 / Math.PI + 90;
		double speed = 12;
		double angRad = Math.toRadians(ang - 90);
		double movx = Math.cos(angRad) * speed;

		double movy = Math.sin(angRad) * speed;
		StdDraw.text(0, h, String.valueOf(ang));



		movx += movx / -counter;
		movy += movy / -counter;

		StdDraw.circle(movx, movy, Math.abs(1 - counter * 0.2));
		StdDraw.picture(0, 0, "kanone.png", 5, 5, ang);
		

		StdDraw.setPenColor(color);

		if (counter >= 10) {
			counter = 1;

		}	
	
*/
	
	
	@Override
	public void collisionWith(Object a) {
		if(a instanceof FixedGun ){
			//System.out.println("Movement Collision between "+this.toString()+" and FixedBlock "+ a.toString());
		}else if(a instanceof Player){
			//System.out.println("Movement Collision between "+this.toString()+" and Player "+ a.toString());		
		}else if(a instanceof Bomb){
			//System.out.println("Movement Collision between "+this.toString()+" and Wall "+ a.toString());		
		}else{
			//System.out.println("Collision between "+this.toString()+" and "+ a.toString());			
		}
	}

	static{
		collisionArea=new Area(new Rectangle(0,0,40,40));
		clipArea=new Area(new Rectangle(0,0,40,40));
		try {
			image = ImageIO.read(new File("src/resources/kanone.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initializeCollisions() {
		if(this.isAbsIntialized){
			this.co.setCollisionArea(collisionArea.createTransformedArea(this.absTransform));
			this.collisionsInitialized=true;
		}else{
			System.err.println("FixedBlock.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}
		
	}
}
