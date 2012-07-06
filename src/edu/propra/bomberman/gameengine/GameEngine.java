//Author Nadescha, Jens
/**
 * For Control of gameflow and control of the other engines
 * Contains oa GameObject Graph and an ActionTimeline 
 */

package edu.propra.bomberman.gameengine;

import java.awt.Dimension;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;
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
import edu.propra.bomberman.gameengine.actions.GameOverAction;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.BombGrowItem;
import edu.propra.bomberman.gameengine.objects.BombUpItem;
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
import edu.propra.bomberman.graphicengine.SGScene;
import edu.propra.bomberman.networkengine.network.NetworkEngine;
import edu.propra.bomberman.usercontrolengine.PlayerListener;
import edu.propra.bomberman.usercontrolengine.UserControlEngine;

public class GameEngine {
	long								actionCount		= 0;
	private PriorityQueue<ActionObject>	actionTimeline;
	private CollisionEngine				cE;
	ArrayDeque<Object>					collisionStack;
	long								dur				= 0;

	private GraphicEngine				gE;
	private GameRoundThread				gt;

	private NetworkEngine				nE;

	public long							ObjectCounter	= 0;

	private GameObjectGroup				objectsRoot;

	private int							players;

	boolean								roundDone		= true;

	private Jukebox						sE;

	private UserControlEngine			ucE;
	
	private ArrayDeque<Object> netStack;
	
	public GameEngine() {
		System.out.println("GameEngine.GameEngine()");
		
		System.out.println("    Initializing Engines");
		gE = new GraphicEngine();
		cE = new CollisionEngine();
		cE.setGameEngine(this);
		nE = new NetworkEngine();
		ucE = new UserControlEngine(this);
		sE = new Jukebox();
		System.out.println("    Engines Initialized");

		actionTimeline = new PriorityQueue<ActionObject>();
		netStack=new ArrayDeque<Object>();
		objectsRoot = null;
		preloadClasses();

	}

	public void preloadClasses(){
		System.out.println("    preloading Classes");
		System.out.println("        "+Bomb.class.getName()+" loaded ("+Bomb.images.length+")");
		System.out.println("        "+BombGrowItem.class.getName()+" loaded ("+BombGrowItem.image.getType()+")");
		System.out.println("        "+BombUpItem.class.getName()+" loaded ("+BombUpItem.image.getType()+")");		
		System.out.println("        "+Exit.class.getName()+" loaded ("+Exit.image.getType()+")");
		System.out.println("        "+Explosion.class.getName()+" loaded ("+Explosion.image.length+")");		
		System.out.println("        "+FixedBlock.class.getName()+" loaded ("+FixedBlock.image.getType()+")");
		System.out.println("        "+IceBlock.class.getName()+" loaded ("+IceBlock.image.getType()+")");
		System.out.println("        "+Player.class.getName()+" loaded ("+Player.images.length+")");
		System.out.println("        "+Wall.class.getName()+" loaded ("+Wall.image.getType()+")");		
	}
	
	
	public void addAction(ActionObject action) {
		//System.out.println("GameEngine.addAction(1)");
		if (action != null) {
			actionTimeline.add(action);
			actionCount++;
		}
	}

	public void addAction(ActionObject action, boolean submit) {
		//System.out.println("GameEngine.addAction(2)");
		addAction(action);
		if (submit && action != null && this.nE.networkGame) this.nE.broadcastMessage("MIDYYY Broadcast AddAction" + action.getMessageData());
	}

	public GameObjectGroup addObject(GameObject obj, GameObjectGroup parent) {
		//System.out.println("GameEngine.addObject(1)");
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
		//System.out.println("GameEngine.addObject(2)");
		parent = this.addObject(obj, parent);
		if (doAdd && this.nE.networkGame) 
			if (parent != null)
				this.nE.broadcastMessage("MIDXXX Broadcast AddObject " + obj.getMessageData() + " " + parent.getOid());
			else
				this.nE.broadcastMessage("MIDXXX Broadcast AddObject " + obj.getMessageData() + " 0");

	}

	public void AllPlayersConnected() {
		System.out.println("GameEngine.AllPlayersConnected()");
		if (this.nE.isServer()) {
			this.nE.broadcastMessage("IDPPP Broadcast EndPlayerSelectionPhase");
			int scene = (int) (Math.random() * (9d - 5d) + 5d);
			this.objectsRoot = (GameObjectGroup) SceneMapGenerator.loadMap("scene" + scene + ".xml",this);
			objectsRoot.initializeAbsolutePositions(new AffineTransform());
			objectsRoot.initializeCollisions();
			
			Player player1 = new Player(25, 25, "Player1", 0, "oid" + ObjectCounter);
			this.setPlayer(player1, true);

			//TODO Make this for individualPlayer
			this.nE.broadcastMessage("MIDQQQ Broadcast AddPlayer oid" + ObjectCounter);
			Player player2 = new Player(735, 535, "Player2", 1, "oid" + ObjectCounter);
			this.addObject(player2, null, true);
			this.players = 2;
		}
		this.startGame();
	}

	public void collisionBetween(Object a, Object b) {
		//System.out.println("GameEngine.collisionBetween()");
		if (a == b) return;
		if (collisionStack == null) collisionStack = new ArrayDeque<Object>();
		collisionStack.push(a);
		collisionStack.push(b);
	}

	public void doActions() {
		//System.out.println("GameEngine.doActions()");
		long time = SGameEngine.get().getTime();
		ActionObject action;
		while (!actionTimeline.isEmpty() && actionTimeline.peek().getTime() < time) {
			action=actionTimeline.poll();
			if(action!=null)action.action();
		}
	}

	public void doCollisions() {
		//System.out.println("GameEngine.doCollisions()");
		Object a, b;
		if (collisionStack == null) collisionStack = new ArrayDeque<Object>();
		while (!collisionStack.isEmpty()) {
			a = collisionStack.pop();
			b = collisionStack.pop();
			((GameObject) a).collisionWith(b);
			((GameObject) b).collisionWith(a);
		}
	}

	public void doMoves() {
		//System.out.println("GameEngine.doMoves()");
		objectsRoot.doMoves();
	}

	public void doPreMoves() {
		//System.out.println("GameEngine.doPreMoves()");
		objectsRoot.doPreMoves();
	}
	
	public void doNetStack(){
		//System.out.println("GameEngine.doNetStack()");
		if(netStack==null) netStack=new ArrayDeque<Object>();
		Object next;
		Object parent;
		while(!netStack.isEmpty()){
			next=netStack.pollFirst();
			if(next instanceof ActionObject)this.addAction((ActionObject) next,false);
			if(next instanceof GameObject){	
				parent=netStack.pollFirst();
				if(parent instanceof  String){
					this.addObject((GameObject)next, null);				
				}else{
					this.addObject((GameObject)next, (GameObjectGroup) parent);
				}
			}
		}
	}
	public void doRound() {
		//System.out.println("GameEngine.doRound()");
		if (!roundDone) return;
		roundDone = false;
		//dur=SGameEngine.get().getTime();
		this.gE.getPanel().updateCache = false;
		doNetStack();
		doActions();
		doPreMoves();
		this.cE.CheckCollision();
		doCollisions();
		doMoves();
		this.gE.getPanel().updateCache = true;
		this.gE.getPanel().repaint();
		//dur=SGameEngine.get().getTime()-dur;	
		////System.out.println(dur);
		roundDone = true;
	}

	public void endGame() {
		System.out.println("GameEngine.endGame()");
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


	public String getActionID() {
		//System.out.println("GameEngine.getActionID()");
		return "aid" + actionCount;
	}

	public GameObject getByOID(String oid) {
		//System.out.println("GameEngine.getByOID()");
		return this.objectsRoot.getByOid(oid);
	}

	public CollisionEngine getCollisionEngine() {
		//System.out.println("GameEngine.getCollisionEngine()");
		return this.cE;
	}

	public GraphicEngine getGraphicEngine() {
		//System.out.println("GameEngine.getGraphicEngine()");
		return this.gE;
	}

	

	public Jukebox getSoundEngine() {
		//System.out.println("GameEngine.getSoundEngine()");
		return sE;
	}

	public UserControlEngine getUserControlEngine() {
		//System.out.println("GameEngine.getUserControlEngine()");
		return this.ucE;
	}

	public void initializeGame() {
		System.out.println("GameEngine.initializeGame()");
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


	public void removeAction(ActionObject action) {
		//System.out.println("GameEngine.removeAction()");
		this.actionTimeline.remove(action);
	}

	public void removeExplosion(Explosion boom) {
		//System.out.println("GameEngine.removeExplosion()");
		this.removeObject(boom);
	}

	public void removeGameObject(GameObject go) {
		//System.out.println("GameEngine.removeGameObject()");
		this.objectsRoot.removeChildRecursive(go);
		this.cE.DelObject(go.getCo());
		if (go.getGo().getParent() instanceof SGGroup) {
			((SGGroup) go.getGo().getParent()).removeChild(go.getGo());
		} else if (go.getGo().getParent() instanceof SGScene) {
			((SGScene) go.getGo().getParent()).removeChild(go.getGo());
		}
	}

	public void removeObject(GameObject obj) {
		//System.out.println("GameEngine.removeObject()");
		if (obj != null && obj.getParent() != null) {
			((IParent) obj.getParent()).removeChild(obj);
			this.gE.removeSGNode(obj.getGo());
			this.cE.DelObject(obj.getCo());
		}
	}

	public void removePlayer(Object actor) {
		//System.out.println("GameEngine.removePlayer()");
		this.players--;
		if (this.players == 0) {
			this.addAction(new GameOverAction(this.getActionID(), SGameEngine.get().getTime()), true);
		}
	}

	public Player you;
	public void setPlayer(Player player, boolean submit) {
		//System.out.println("GameEngine.setPlayer()");
		this.you=player;
		this.addObject(player, null, submit);
		PlayerListener listener = new PlayerListener(player, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
		listener.login(this.ucE);
	}

	public void setSoundEngine(Jukebox sE) {
		this.sE = sE;
	}

	public void startGame() {
		System.out.println("GameEngine.startGame()");
		if (this.nE.networkGame && this.nE.isServer()) {
			this.nE.InitializeMapPhaseOver();
		}
		Bomberman.gameFrame.addKeyListener(this.getUserControlEngine());
		Bomberman.gameFrame.invalidate();
		Bomberman.gameFrame.pack();
		Bomberman.gameFrame.setVisible(true);
		gt = new GameRoundThread(this);
		new Thread(gt).start();
	}

	public void startOnePlayer() {
		System.out.println("GameEngine.startOnePlayer()");
		int scene = (int) (Math.random() * (5d - 1d) + 1d);
		//this.objectsRoot = (GameObjectGroup) this.loadMap("scene"+scene+".xml");
		RandomMapGenerator randmap = new RandomMapGenerator();
		this.objectsRoot = randmap.RandomMap();//.("scene"+scene+".xml");
		Player player = new Player(25, 25, "Player 1", 0, "oid" + ObjectCounter);
		this.setPlayer(player, false);
		this.players = 1;
		this.startGame();

	}

	public void startTwoPlayer() {
		System.out.println("GameEngine.startTwoPlayer()");
		int scene = (int) (Math.random() * (9d - 5d) + 5d);
		this.objectsRoot = (GameObjectGroup) SceneMapGenerator.loadMap("scene" + scene + ".xml",this);
		this.objectsRoot.initializeAbsolutePositions(new AffineTransform());
		this.objectsRoot.initializeCollisions();
		Player player = new Player(25, 25, "Player1", 0, "oid" + ObjectCounter);
		this.setPlayer(player, false);

		Player enemy = new Player(735, 535, "Player2", 1, "oid" + ObjectCounter);
		this.addObject(enemy, null, false);
		PlayerListener enemyListener = new PlayerListener(enemy, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E);
		enemyListener.login(this.ucE);
		this.players = 2;
		
		this.startGame();
	}

	public void startTwoPlayerNetwork() {
		System.out.println("GameEngine.startTwoPlayerNetwork()");
		this.nE.connect();
		this.nE.start();
	}
	boolean mapInit=true;
	boolean flag=false;
	public void addToNetStack(Object object) {
		//System.out.println("GameEngine.addToNetStack()");
		if(object!=null)this.netStack.add(object);
		//System.out.println("message on stack:"+ System.currentTimeMillis());
		if(mapInit){
			if(object instanceof GameObject || object instanceof String){
				if(flag){
					this.doNetStack();
					flag=false;
				}else{
					flag=true;
				}
			}
		}
	}
	
	private long syncTime=0;
	public long getTime(){
		return System.currentTimeMillis()
				-syncTime;
	}
	
	public void calcSyncTime(long servertime,long clienttime){
		syncTime=clienttime-servertime;
	}

	public NetworkEngine getNetworkEngine() {
		return this.nE;
	}
}
