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

public class Enemy extends Player{
	public static Area collisionArea;
	public static Area clipArea;
	public static BufferedImage[] images;
	private static BufferedImage deathImage;

	
	public Enemy(int x,int y) {
		super(x,y);
	}

	static{
		collisionArea=new Area(new Rectangle(13,2,13,36));
		clipArea=new Area(new Rectangle(0,0,40,40));
		images=new BufferedImage[4];
		
		try {
			images[0] = ImageIO.read(new File("src/resources/enemy_front.png"));
			images[1] = ImageIO.read(new File("src/resources/enemy_back.png"));
			images[2] = ImageIO.read(new File("src/resources/enemy_left.png"));
			images[3] = ImageIO.read(new File("src/resources/enemy_right.png"));
			deathImage = ImageIO.read(new File("src/resources/asche.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
