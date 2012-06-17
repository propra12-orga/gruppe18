package edu.propra.bomberman.gameengine;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.propra.bomberman.collisionengine.CollisionEngine;
import edu.propra.bomberman.gameengine.actions.ActionObject;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Explosion;
import edu.propra.bomberman.gameengine.objects.FixedBlock;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.GameObjectGroup;
import edu.propra.bomberman.gameengine.objects.Moveable;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.gameengine.objects.Wall;
import edu.propra.bomberman.graphicengine.GraphicEngine;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGGroup;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGScene;
import edu.propra.bomberman.graphicengine.SGTransform;
import edu.propra.bomberman.networkengine.NetworkEngine;
import edu.propra.bomberman.usercontrolengine.PlayerListener;
import edu.propra.bomberman.usercontrolengine.UserControlEngine;

public class GameEngine {
	private GraphicEngine gE;
	private CollisionEngine cE;
	private NetworkEngine nE;
	private UserControlEngine ucE;

	private PriorityQueue<ActionObject> actionTimeline;
	private GameObject objectsRoot;

	public GameEngine() {
		// initialize Engines
		gE = new GraphicEngine();
		cE = new CollisionEngine();
		cE.setGameEngine(this);
		nE = new NetworkEngine();
		ucE = new UserControlEngine(this);

		actionTimeline = new PriorityQueue<ActionObject>();
		objectsRoot=null;

		gE.getPanel().addKeyListener(ucE);

	}

	public GameObject loadMap(String filename) {
		// TODO load from file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("src/scene1.xml");
			Node root=doc.getFirstChild();
			Node act=root;
			GameObjectGroup actGO=null;
			GameObjectGroup GOroot=null;
			int depth=1;
			do{
				if(act.getNodeType()==Node.ELEMENT_NODE){
					if(act.getNodeName().equals("geGroup")){
						if(actGO==null){
							actGO=(GameObjectGroup)this.getNewGO(act);
							GOroot=actGO;
						}else{
							actGO=(GameObjectGroup)actGO.addChild(this.getNewGO(act));
						}
					}else{
						GameObject temp=this.getNewGO(act);
						actGO.addChild(temp);
					}
				}
				if(act.hasChildNodes()){
					act=act.getFirstChild();
					depth++;
					continue;
				}
				if(act.getNextSibling()!=null){
					act=act.getNextSibling();
					continue;
				}else{
					act=act.getParentNode().getNextSibling();
					depth--;
					continue;
				}
			}while(act!=root && act!=null && act!=doc);
			System.out.println("done");
			return GOroot;
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
		return null;
	}
	private GameObject getNewGO(Node node){
		if(node.getNodeName().equals("geGroup")){
			int x=Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y=Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			GameObjectGroup group=new GameObjectGroup(x,y);
			return group;
		}
		if(node.getNodeName().equals("geFixedBlock")){
			int x=Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y=Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			FixedBlock fb=new FixedBlock(x, y);
			return fb;
		}
		if(node.getNodeName().equals("geWall")){
			int x=Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y=Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			Wall wall=new Wall(x, y);
			return wall;
		}

		if(node.getNodeName().equals("geBomb")){
			int x=Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y=Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			Bomb bomb=new Bomb(x, y);
			return bomb;
		}
		
		if(node.getNodeName().equals("geExplosion")){
			int x=Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y=Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			int size=Integer.parseInt(node.getAttributes().getNamedItem("size").getNodeValue());
			Explosion explosion=new Explosion(x, y,size);
			return explosion;
		}
		/*if(node.getNodeName().equals("geImage")){
			int x=Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y=Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			String url=node.getAttributes().getNamedItem("url").getNodeValue();
			GameImage fb=new GameImage(x, y,url);
			return fb;
		}*/
		System.err.println("Unknown Type of Node when parsing XML");
		return null;
		
	}
	
	public void removeGameObject(GameObject go){
		((GameObjectGroup)this.objectsRoot).removeChildRecursive(go);
		this.cE.DelObject(go.getCo());
		if(go.getGo().getParent() instanceof SGGroup){
			((SGGroup)go.getGo().getParent()).removeChild(go.getGo());
		}else if(go.getGo().getParent() instanceof SGScene){
			((SGScene)go.getGo().getParent()).removeChild(go.getGo());
		}
	}
	
	ArrayDeque<Object> collisionStack;

	public void collisionBetween(Object a, Object b) {
		if (a == b)
			return;
		if (collisionStack == null)
			collisionStack = new ArrayDeque<Object>();
		collisionStack.push(a);
		collisionStack.push(b);
	}

	public void doCollisions() {
		Object a, b;
		if (collisionStack == null)
			collisionStack = new ArrayDeque<Object>();
		while (!collisionStack.isEmpty()) {
			a = collisionStack.pop();
			b = collisionStack.pop();
			((GameObject) a).collisionWith(b);
			((GameObject) b).collisionWith(a);
		}
	}

	public static void main(String[] args) {
		GameEngine gameEngine = new GameEngine();
		gameEngine.initializeGame();
		
		
		JFrame test = new JFrame();
		test.setContentPane(gameEngine.gE.getPanel());
		test.pack();
		test.addKeyListener(gameEngine.ucE);
		test.setVisible(true);

		
		/*
		Wall wall = new Wall(0, 0);
		gameEngine.cE.AddObject(wall.getCo());
		gameEngine.gE.getScene().addChild(wall.getGo());

		SGGroup blocks = new SGGroup();
		FixedBlock block1 = new FixedBlock(80, 80);
		gameEngine.cE.AddObject(block1.getCo());
		blocks.addChild(block1.getGo());
		FixedBlock block2 = new FixedBlock(160, 160);
		gameEngine.cE.AddObject(block2.getCo());
		blocks.addChild(block2.getGo());
		FixedBlock block3 = new FixedBlock(80, 160);
		gameEngine.cE.AddObject(block3.getCo());
		blocks.addChild(block3.getGo());
		FixedBlock block4 = new FixedBlock(160, 80);
		gameEngine.cE.AddObject(block4.getCo());
		blocks.addChild(block4.getGo());
		FixedBlock block5 = new FixedBlock(240, 80);
		gameEngine.cE.AddObject(block5.getCo());
		blocks.addChild(block5.getGo());

		gameEngine.gE.getScene().addChild(blocks);
		*/
		
		
		
		
		// gameEngine.gE.startDrawing();
		gameEngine.startGame();
	}

	public void initializeGame(){
		gE.getPanel().setPreferredSize(new Dimension(800, 600));
		
		this.objectsRoot=this.loadMap("");
		
		
		// TODO refactor to load Background by MapLoader
		SGImage background = new SGImage();
		try {
			background.setImage(ImageIO.read(new File("src/resources/background.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setClipArea(new Area(new Rectangle(0, 0, 800, 600)));
		this.gE.getScene().addChild(background);

		
		//TODO refactor to move this to a better point (e.g. Player added over Network)
		Player player = new Player(185, 185);
		((GameObjectGroup)this.objectsRoot).addChild(player);
		((SGAnimation) ((SGTransform) player.getGo()).getChild()).start();
		
		
		PlayerListener listener = new PlayerListener(player);
		this.ucE.addListener(KeyEvent.VK_DOWN, listener);
		this.ucE.addListener(KeyEvent.VK_UP, listener);
		this.ucE.addListener(KeyEvent.VK_RIGHT, listener);
		this.ucE.addListener(KeyEvent.VK_LEFT, listener);
		this.ucE.addListener(KeyEvent.VK_SPACE, listener);
		
		
		//TODO implement the next line
		this.objectsRoot.initializeAbsolutePositions(new AffineTransform());
		this.objectsRoot.initializeCollisions();
		this.objectsRoot.addToScene(gE.getScene().getChild());
		this.objectsRoot.addToCollisionEngine(cE);
		
	}
	
	public void addAction(ActionObject action) {
		if (action != null)
			actionTimeline.add(action);
	}

	public void doActions() {
		long time = System.currentTimeMillis();
		while (!actionTimeline.isEmpty() && actionTimeline.peek().getTime() < time) {
				actionTimeline.poll().action();
		}
	}

	public void doPreMoves() {
		objectsRoot.doPreMoves();
		/*for (GameObject go : this.objects) {
			if (go instanceof Moveable) {
				if (((Moveable) go).getMovingData().isMoving())
					((Moveable) go).getMovingData().doStepCollision(go.getCo());
			}
		}*/
	}

	public void doMoves() {
		objectsRoot.doMoves();
		/*
		for (GameObject go : this.objects) {
			if (go instanceof Moveable) {
				if (((Moveable) go).getMovingData().isMoving())
					((Moveable) go).getMovingData().doStepGraphic(go);
			}
		}*/
	}

	public void startGame() {
		new Thread(new GameRoundThread(this)).start();
	}

	boolean roundDone=true;
	public void doRound() {
		if(!roundDone)System.out.println("Round Thread Collision");
		roundDone=false;
		doActions();
		doPreMoves();
		this.cE.CheckCollision();
		doCollisions();
		doMoves();
		this.gE.getPanel().repaint();
		roundDone=true;
		//System.out.println(dur);
	}
}
