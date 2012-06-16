package edu.propra.bomberman.gameengine;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import edu.propra.bomberman.collisionengine.CollisionEngine;
import edu.propra.bomberman.gameengine.objects.FixedBlock;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.graphicengine.GraphicEngine;
import edu.propra.bomberman.graphicengine.SGGroup;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.networkengine.NetworkEngine;
import edu.propra.bomberman.usercontrolengine.UserControlEngine;

public class GameEngine {
	private GraphicEngine gE;
	private CollisionEngine cE;
	private NetworkEngine nE;
	private UserControlEngine ucE;
	
	
	
	
	public GameEngine() {
		// initialize Engines
		gE=new GraphicEngine();
		cE=new CollisionEngine();
		cE.setGameEngine(this);
		nE=new NetworkEngine();
		ucE=new UserControlEngine();
	}
	
	public void loadMap(String filename){
		//TODO load from file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("scene1.xml");
			doc.getFirstChild();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	ArrayDeque<Object> collisionStack;
	public void collisionBetween(Object a,Object b){
		if(a==b)return;
		if(collisionStack==null)collisionStack=new ArrayDeque<Object>();
		collisionStack.push(a);
		collisionStack.push(b);
	}
	
	public void doCollisions(){
		Object a,b;
		if(collisionStack==null)collisionStack=new ArrayDeque<Object>();
		while(!collisionStack.isEmpty()){
			a=collisionStack.pop();
			b=collisionStack.pop();
			((GameObject)a).collisionWith(b);
			((GameObject)b).collisionWith(a);
		}
	}
	
	public static void main(String[] args) {
		GameEngine gameEngine=new GameEngine();
		
		JFrame test=new JFrame();
		test.setPreferredSize(new Dimension(800, 600));
		test.setContentPane(gameEngine.gE.getPanel());		
		test.pack();
		test.setVisible(true);
		
		SGImage background=new SGImage();
		try {
			background.setImage(ImageIO.read(new File("src/resources/background.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		background.setClipArea(new Area(new Rectangle(0,0,800,600)));
		gameEngine.gE.getScene().addChild(background);
		
		
		SGGroup blocks=new SGGroup();

		FixedBlock block1=new FixedBlock(80,80);
		gameEngine.cE.AddObject(block1.co);
		blocks.addChild(block1.go);
		FixedBlock block2=new FixedBlock(160,160);
		gameEngine.cE.AddObject(block2.co);
		blocks.addChild(block2.go);
		FixedBlock block3=new FixedBlock(80,160);
		gameEngine.cE.AddObject(block3.co);
		blocks.addChild(block3.go);
		FixedBlock block4=new FixedBlock(160,80);
		gameEngine.cE.AddObject(block4.co);
		blocks.addChild(block4.go);
		FixedBlock block5=new FixedBlock(240,80);
		gameEngine.cE.AddObject(block5.co);
		blocks.addChild(block5.go);
		
		gameEngine.gE.getScene().addChild(blocks);
		
		

		//Player player=new Player(100,100);
		//gameEngine.gE.getScene().addChild(player.go);
		//gameEngine.cE.AddObject(player.co);
		
		gameEngine.gE.startDrawing();

		gameEngine.cE.CheckCollision();
		gameEngine.doCollisions();
	}
	
}
