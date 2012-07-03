package edu.propra.bomberman.gameengine;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.Bomberman;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.propra.bomberman.audio.Jukebox;
import edu.propra.bomberman.collisionengine.CollisionEngine;
import edu.propra.bomberman.gameengine.actions.ActionObject;
import edu.propra.bomberman.gameengine.actions.BombDownAction;
import edu.propra.bomberman.gameengine.actions.GameOverAction;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Exit;
import edu.propra.bomberman.gameengine.objects.Explosion;
import edu.propra.bomberman.gameengine.objects.FixedBlock;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.GameObjectGroup;
import edu.propra.bomberman.gameengine.objects.IceBlock;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.gameengine.objects.Wall;
import edu.propra.bomberman.graphicengine.GraphicEngine;
import edu.propra.bomberman.graphicengine.IParent;
import edu.propra.bomberman.graphicengine.SGGroup;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGPanel;
import edu.propra.bomberman.graphicengine.SGScene;
import edu.propra.bomberman.networkengine.network.NetworkEngine;
import edu.propra.bomberman.usercontrolengine.PlayerListener;
import edu.propra.bomberman.usercontrolengine.UserControlEngine;

public class GameEngine {
	private GraphicEngine				gE;
	private CollisionEngine				cE;
	private NetworkEngine				nE;
	private UserControlEngine			ucE;
	private Jukebox						sE;
	
	private PriorityQueue<ActionObject>	actionTimeline;
	private GameObjectGroup				objectsRoot;

	public long ObjectCounter=0;
	
	public GameEngine() {
		// initialize Engines
		gE = new GraphicEngine();
		cE = new CollisionEngine();
		cE.setGameEngine(this);
		nE = new NetworkEngine();
		ucE = new UserControlEngine(this);
		setSoundEngine(new Jukebox());

		actionTimeline = new PriorityQueue<ActionObject>();
		objectsRoot = null;

	}

	public GameObject loadMap(String filename) {
		// TODO load from file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(Bomberman.class.getClassLoader().getResourceAsStream(filename));
			Node root = doc.getFirstChild();
			Node act = root;
			GameObjectGroup actGO = null;
			GameObjectGroup GOroot = null;
			int depth = 1;
			do {
				if (act.getNodeType() == Node.ELEMENT_NODE) {
					if (act.getNodeName().equals("geGroup")) {
						if (actGO == null) {
							actGO = (GameObjectGroup) this.getNewGO(act);
							GOroot = actGO;
							this.addObject(actGO, null,true);
						} else {
							GameObjectGroup actGO2 = (GameObjectGroup) this.getNewGO(act);
							this.addObject(actGO2, actGO,true);
							actGO = actGO2;
						}
					} else {
						this.addObject(this.getNewGO(act), actGO,true);
					}

				}
				if (act.hasChildNodes()) {
					act = act.getFirstChild();
					depth++;
					continue;
				}
				if (act.getNextSibling() != null) {
					act = act.getNextSibling();
					continue;
				} else {
					if (act.getNodeName().equals("geGroup")) actGO = (GameObjectGroup) actGO.getParent();
					act = act.getParentNode().getNextSibling();
					depth--;
					continue;
				}
			} while (act != root && act != null && act != doc);
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

	private GameObject getNewGO(Node node) {
		if (node.getNodeName().equals("geGroup")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			GameObjectGroup group = new GameObjectGroup(x, y,"oid"+ObjectCounter);
			return group;
		}
		if (node.getNodeName().equals("geFixedBlock")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			FixedBlock fb = new FixedBlock(x, y,"oid"+ObjectCounter);
			return fb;
		}
		if (node.getNodeName().equals("geWall")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			Wall wall = new Wall(x, y,"oid"+ObjectCounter);
			return wall;
		}
		if (node.getNodeName().equals("geExit")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			Exit exit = new Exit(x, y,"oid"+ObjectCounter);
			return exit;
		}
		if (node.getNodeName().equals("geBomb")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			Bomb bomb = new Bomb(null, x, y,"oid"+ObjectCounter);
			return bomb;
		}

		if (node.getNodeName().equals("geExplosion")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			int size = Integer.parseInt(node.getAttributes().getNamedItem("size").getNodeValue());
			Explosion explosion = new Explosion(x, y, size,"oid"+ObjectCounter);
			return explosion;
		}

		if (node.getNodeName().equals("geIceBlock")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			IceBlock iceblock = new IceBlock(x, y,"oid"+ObjectCounter,-1);
			return iceblock;
		}
		System.err.println("Unknown Type of Node when parsing XML");
		return null;

	}

	public void removeGameObject(GameObject go) {
		this.objectsRoot.removeChildRecursive(go);
		this.cE.DelObject(go.getCo());
		if (go.getGo().getParent() instanceof SGGroup) {
			((SGGroup) go.getGo().getParent()).removeChild(go.getGo());
		} else if (go.getGo().getParent() instanceof SGScene) {
			((SGScene) go.getGo().getParent()).removeChild(go.getGo());
		}
	}

	ArrayDeque<Object>	collisionStack;

	public void collisionBetween(Object a, Object b) {
		if (a == b) return;
		if (collisionStack == null) collisionStack = new ArrayDeque<Object>();
		collisionStack.push(a);
		collisionStack.push(b);
	}

	public void doCollisions() {
		Object a, b;
		if (collisionStack == null) collisionStack = new ArrayDeque<Object>();
		while (!collisionStack.isEmpty()) {
			a = collisionStack.pop();
			b = collisionStack.pop();
			((GameObject) a).collisionWith(b);
			((GameObject) b).collisionWith(a);
		}
	}

	public void initializeGame() {
		gE.getPanel().setPreferredSize(new Dimension(800, 600));
		SGImage background = new SGImage();
		try {
			background.setImage(ImageIO.read(Bomberman.class.getClassLoader().getResource("resources/background.png").openStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		background.setClipArea(new Area(new Rectangle(0, 0, 800, 600)));
		this.gE.addSGNode(background, null);
	}

	public void startOnePlayer() {
		int scene=(int)(Math.random()*(5d-1d)+1d);
		//this.objectsRoot = (GameObjectGroup) this.loadMap("scene"+scene+".xml");
		RandomMapGenerator randmap=new RandomMapGenerator();
		this.objectsRoot = (GameObjectGroup) randmap.RandomMap();//.("scene"+scene+".xml");
		Player player = new Player(25, 25, "Player 1", 0,"oid"+ObjectCounter);
		this.addObject(player, null,false);
		PlayerListener listener = new PlayerListener(player, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
		listener.login(this.ucE);
		this.players = 1;
		// gameEngine.gE.startDrawing();
		this.startGame();

	}
	public void startTwoPlayerNetwork() {
		this.nE.connect();
		this.nE.start();
		}
	
	public void AllPlayersConnected(){
		if(this.nE.isServer()){
			this.nE.broadcastMessage("IDPPP Broadcast EndPlayerSelectionPhase");
			int scene=(int)(Math.random()*(9d-5d)+5d);
			this.objectsRoot = (GameObjectGroup) this.loadMap("scene"+scene+".xml");	
			Player player1 = new Player(25, 25, "Player1", 0,"oid"+ObjectCounter);
			this.setPlayer(player1,true);
			
			//TODO Make this for individualPlayer
			this.nE.broadcastMessage("MIDQQQ Broadcast AddPlayer oid"+ObjectCounter);
			Player player2 = new Player(735, 535, "Player2", 1,"oid"+ObjectCounter);
			this.addObject(player2, null,true);
			this.players = 2;
		}
		this.startGame();
	}
	public void startTwoPlayer() {
		int scene=(int)(Math.random()*(9d-5d)+5d);
		this.objectsRoot = (GameObjectGroup) this.loadMap("scene"+scene+".xml");
		Player player = new Player(25, 25, "Player1", 0,"oid"+ObjectCounter);
		this.addObject(player, null,false);
		PlayerListener playerListener = new PlayerListener(player, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
		playerListener.login(this.ucE);

		Player enemy = new Player(735, 535, "Player2", 1,"oid"+ObjectCounter);
		this.addObject(enemy, null,false);
		PlayerListener enemyListener = new PlayerListener(enemy, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
		enemyListener.login(this.ucE);
		this.players = 2;
		// gameEngine.gE.startDrawing();
		this.startGame();
	}

	public void addAction(ActionObject action) {
		if (action != null){
			actionTimeline.add(action);
			actionCount++;
		}
	}

	public void addAction(ActionObject action, boolean submit) {
		addAction(action);
		if(submit && action!=null && this.nE.networkGame)
			this.nE.broadcastMessage("MIDYYY Broadcast AddAction"+action.getMessageData());
	}
	public void doActions() {
		long time = System.currentTimeMillis();
		while (!actionTimeline.isEmpty() && actionTimeline.peek().getTime() < time) {
			actionTimeline.poll().action();
		}
	}

	public void doPreMoves() {
		objectsRoot.doPreMoves();
	}

	public void doMoves() {
		objectsRoot.doMoves();
	}

	private GameRoundThread	gt;

	public void startGame() {
		if(this.nE.networkGame && this.nE.isServer()){
			this.nE.InitializeMapPhaseOver();
		}		

		Bomberman.gameFrame.addKeyListener(this.getUserControlEngine());
		Bomberman.gameFrame.invalidate();
		Bomberman.gameFrame.pack();
		Bomberman.gameFrame.setVisible(true);
		gt = new GameRoundThread(this);
		new Thread(gt).start();
	}

	boolean		roundDone	= true;
	private int	players;
	long dur=0;
	public void doRound() {
		if (!roundDone)return;
		roundDone = false;
		//dur=System.currentTimeMillis();
		this.gE.getPanel().updateCache = false;
		doActions();
		doPreMoves();
		this.cE.CheckCollision();
		doCollisions();
		doMoves();
		this.gE.getPanel().updateCache = true;
		this.gE.getPanel().repaint(); 
		//dur=System.currentTimeMillis()-dur;	
		//System.out.println(dur);
		roundDone = true;
	}

	public GameObjectGroup addObject(GameObject obj, GameObjectGroup parent) {
		ObjectCounter++;
		if (parent == null) parent = this.objectsRoot;
		if (parent == null) {
			parent = null;
			this.objectsRoot = (GameObjectGroup) obj;
			obj.initializeAbsolutePositions(new AffineTransform());
			obj.initializeCollisions();
			this.gE.addSGNode(obj.getGo(), null);
		} else {
			parent.addChild(obj);
			obj.initializeAbsolutePositions(parent.absTransform);
			obj.initializeCollisions();
			this.gE.addSGNode(obj.getGo(), parent.getGoLeaf());
			if (!(obj instanceof GameObjectGroup)) this.cE.AddObject(obj.getCo());
		}
		return parent;
	}
	public void addObject(GameObject obj, GameObjectGroup parent, boolean doAdd) {
		parent=this.addObject(obj, parent);
		if(doAdd && this.nE.networkGame)
			if(parent!=null)
				this.nE.broadcastMessage("MIDXXX Broadcast AddObject "+obj.getMessageData()+" "+parent.getOid());
			else
				this.nE.broadcastMessage("MIDXXX Broadcast AddObject "+obj.getMessageData()+" 0");
		
	}

	public void removeObject(GameObject obj) {
		if(obj!=null && obj.getParent()!=null){
			((IParent) obj.getParent()).removeChild(obj);
			this.gE.removeSGNode(obj.getGo());
			this.cE.DelObject(obj.getCo());
		}
	}

	public void endGame() {
		this.gt.stopRunning();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.cE.releaseData();
		this.gE.releaseData();
		this.ucE.releaseData();
		this.nE.releaseData();
		this.objectsRoot.releaseData();
		this.actionTimeline.clear();
		this.collisionStack.clear();
		Bomberman.gameFrame.gameEnd();
	}

	public void explodeBomb(Bomb bomb) {
	}

	public void removeExplosion(Explosion boom) {
		this.removeObject(boom);
	}

	public void removeAction(ActionObject action) {
		this.actionTimeline.remove(action);
	}

	public CollisionEngine getCollisionEngine() {
		return this.cE;
	}

	public void removePlayer(Object actor) {
		this.players--;
		if (this.players == 0) {
			this.addAction(new GameOverAction(this.getActionID(),System.currentTimeMillis()),true);
		}
	}

	public GraphicEngine getGraphicEngine() {
		return this.gE;
	}

	public UserControlEngine getUserControlEngine() {
		return this.ucE;
	}

	public Jukebox getSoundEngine() {
		return sE;
	}

	public void setSoundEngine(Jukebox sE) {
		this.sE = sE;
	}

	public GameObject getByOID(String oid) {
		return this.objectsRoot.getByOid(oid);
	}

	public void setPlayer(Player player,boolean submit) {
		this.addObject(player, null,submit);
		PlayerListener listener = new PlayerListener(player, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
		listener.login(this.ucE);
	}

	long actionCount=0;
	public String getActionID() {
		return "aid"+actionCount;
	}

}
