package edu.propra.bomberman.gameengine.objects;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.graphicengine.SGArea;
import edu.propra.bomberman.graphicengine.SGGroup;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGScene;
import edu.propra.bomberman.graphicengine.SGTransform;

public class Explosion extends GameObject {
	public static Area collisionArea=null;
	public static Area clipArea=null;
	public static BufferedImage image=null;
	private int size;
	
	private SGArea sDebug;
	public Explosion(int x,int y,int size) {
		this.size=size;
		
		AffineTransform trans=new AffineTransform();
		trans.setToTranslation(x-(size-1)*clipArea.getBounds2D().getWidth()/2, y-(size-1)*clipArea.getBounds2D().getHeight()/2);
		//Construct Graphics Subgraph for Player Object
		this.go=new SGTransform();
		
		((SGTransform)this.go).getTransform().setTransform(trans);
		SGGroup group=new SGGroup();
		((SGTransform)this.go).addChild(group);
		
		sDebug=new SGArea();
		sDebug.setClipArea(new Area(new Rectangle(-1000,-1000,1000,1000)));
		sDebug.setDrawarea(this.calculateClipArea());
		sDebug.setColor(new Color(255,0,0));
		group.addChild(sDebug);
		
		SGImage leaf=new SGImage(image);
		leaf.setClipArea(this.calculateClipArea());
		group.addChild(leaf);
		
		//Construct Collision Object for Player Object
		this.co=new CollisionObject();
		co.setPrivot(this);
		
		this.absTransform=(AffineTransform) trans.clone();
	}
	
	@Override
	public void collisionWith(Object a) {
		if(a instanceof FixedBlock ){
			//System.out.println("Movement Collision between "+this.toString()+" and FixedBlock "+ a.toString());
		}else if(a instanceof Player){
			//System.out.println("Movement Collision between "+this.toString()+" and Player "+ a.toString());		
		}else{
			//System.out.println("Collision between "+this.toString()+" and "+ a.toString());			
		}
	}

	@Override
	public void initializeAbsolutePositions(AffineTransform trans) {
		this.absTransform.concatenate(trans);
		this.isAbsIntialized=true;
	}
	
	private Area calculateClipArea(){
		Area exp=new Area();
		exp.add(new Area(new Rectangle((int)clipArea.getBounds().getWidth()*(size-1)/2,0,(int)clipArea.getBounds().getWidth(),(int)clipArea.getBounds().getHeight()*(size))));
		exp.add(new Area(new Rectangle(0,(int)clipArea.getBounds().getHeight()*((size-1)/2),(int)clipArea.getBounds().getWidth()*(size),(int)clipArea.getBounds().getHeight())));
		return exp;
	}

	@Override
	public void initializeCollisions() {
		if(this.isAbsIntialized){
			Area exp=new Area();
			exp.add(new Area(new Rectangle((int)collisionArea.getBounds().getWidth()*(size-1)/2,0,(int)collisionArea.getBounds().getWidth(),(int)collisionArea.getBounds().getHeight()*(size))));
			exp.add(new Area(new Rectangle(0,(int)collisionArea.getBounds().getHeight()*((size-1)/2),(int)collisionArea.getBounds().getWidth()*(size),(int)collisionArea.getBounds().getHeight())));
			this.co.setCollisionArea(exp.createTransformedArea(this.absTransform));
			this.collisionsInitialized=true;
			
			
		}else{
			System.err.println("FixedBlock.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}
		
	}

	static{
		collisionArea=new Area(new Rectangle(0,0,40,40));
		clipArea=new Area(new Rectangle(0,0,40,40));
		try {
			image = ImageIO.read(new File("src/resources/explosion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
