

package edu.propra.bomberman.networkengine.network;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.actions.BombDownAction;
import edu.propra.bomberman.gameengine.actions.GameOverAction;
import edu.propra.bomberman.gameengine.actions.PlayerBombGrowAction;
import edu.propra.bomberman.gameengine.actions.PlayerBombUpAction;
import edu.propra.bomberman.gameengine.actions.PlayerDeadAction;
import edu.propra.bomberman.gameengine.actions.PlayerWonAction;
import edu.propra.bomberman.gameengine.actions.StartMoveAction;
import edu.propra.bomberman.gameengine.actions.StopMoveAction;
import edu.propra.bomberman.gameengine.objects.Exit;
import edu.propra.bomberman.gameengine.objects.FixedBlock;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.GameObjectGroup;
import edu.propra.bomberman.gameengine.objects.IceBlock;
import edu.propra.bomberman.gameengine.objects.Player;
import edu.propra.bomberman.gameengine.objects.Wall;
import edu.propra.bomberman.graphicengine.SGTransform;

public class ClientProtocol {
	private boolean	ConnectionPhase			= true;

	private boolean	GameEndPhase			= false;
	private boolean	GamePhase				= false;
	private boolean	MapInitializationPhase	= false;
	long			messageCount;
	NetworkEngine	nE;

	String			playeroid				= "";

	private boolean	PlayerSelectionPhase	= false;

	public ClientProtocol(NetworkEngine nE) {
		this.nE = nE;
	}

	private String AddAction(String[] messageParts) {
		if (messageParts[3].equals("BombDownAction")) {
			if (Integer.parseInt(messageParts[5]) < -9999) return null;
			Object actor = SGameEngine.get().getByOID(messageParts[7]);
			SGameEngine.get().addToNetStack(new BombDownAction(actor, Long.parseLong(messageParts[8]), messageParts[4], Integer.parseInt(messageParts[5]), Integer.parseInt(messageParts[6])));
			//SGameEngine.get().addAction(new BombDownAction(actor, Long.parseLong(messageParts[8]) + this.nE.syncTime, messageParts[4], Integer.parseInt(messageParts[5]), Integer.parseInt(messageParts[6])), false);
		} else if (messageParts[3].equals("StartMoveAction")) {
			Object actor = SGameEngine.get().getByOID(messageParts[5]);

			SGameEngine.get().addToNetStack(new StartMoveAction(Integer.parseInt(messageParts[4]), actor, Long.parseLong(messageParts[6]), Integer.parseInt(messageParts[7]), Integer.parseInt(messageParts[8])));
			//((Player) actor).absTransform.setToTranslation(Integer.parseInt(messageParts[7]), Integer.parseInt(messageParts[8]));
			//((SGTransform) ((Player) actor).getGo()).getTransform().setToTranslation(Integer.parseInt(messageParts[7]), Integer.parseInt(messageParts[8]));
			//SGameEngine.get().addAction(new StartMoveAction(Integer.parseInt(messageParts[4]), actor, Long.parseLong(messageParts[6]) + this.nE.syncTime), false);
		} else if (messageParts[3].equals("StopMoveAction")) {
			Object actor = SGameEngine.get().getByOID(messageParts[4]);
			SGameEngine.get().addToNetStack(new StopMoveAction(actor, Long.parseLong(messageParts[5]), Integer.parseInt(messageParts[6]), Integer.parseInt(messageParts[7])));
			//((Player) actor).absTransform.setToTranslation(Integer.parseInt(messageParts[6]), Integer.parseInt(messageParts[7]));
			//((SGTransform) ((Player) actor).getGo()).getTransform().setToTranslation(Integer.parseInt(messageParts[6]), Integer.parseInt(messageParts[7]));
			//SGameEngine.get().addAction(new StopMoveAction(actor, Long.parseLong(messageParts[5]) + this.nE.syncTime), false);
		} else if (messageParts[3].equals("GameOverAction")) {
			SGameEngine.get().addToNetStack(new GameOverAction(messageParts[4], Long.parseLong(messageParts[5])));
			//SGameEngine.get().addAction(new GameOverAction(messageParts[4], Long.parseLong(messageParts[5]) + this.nE.syncTime));
		} else if (messageParts[3].equals("PlayerDeadAction")) {
			Player actor = (Player) SGameEngine.get().getByOID(messageParts[4]);
			SGameEngine.get().addToNetStack(new PlayerDeadAction(actor));
		} else if (messageParts[3].equals("PlayerWonAction")) {
			Player actor = (Player) SGameEngine.get().getByOID(messageParts[4]);
			SGameEngine.get().addToNetStack(new PlayerWonAction(actor));
		} else if (messageParts[3].equals("PlayerBombGrowAction")) {
			Player actor = (Player) SGameEngine.get().getByOID(messageParts[4]);
			GameObject item = (GameObject) SGameEngine.get().getByOID(messageParts[5]);
			SGameEngine.get().addToNetStack(new PlayerBombGrowAction(actor, item));
		} else if (messageParts[3].equals("PlayerBombUpAction")) {
			Player actor = (Player) SGameEngine.get().getByOID(messageParts[4]);
			GameObject item = (GameObject) SGameEngine.get().getByOID(messageParts[5]);
			SGameEngine.get().addToNetStack(new PlayerBombUpAction(actor, item));
		}
		return null;
	}

	private String AddObject(String[] messageParts) {
		if (messageParts[3].equals("GameObjectGroup")) {
			if (messageParts[7].equals("0")) {
				SGameEngine.get().addToNetStack(new GameObjectGroup(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]));
				SGameEngine.get().addToNetStack("null");
				//SGameEngine.get().addObject(new GameObjectGroup(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]), null, false);
			} else {
				SGameEngine.get().addToNetStack(new GameObjectGroup(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]));
				if(messageParts[7].equals("0")){
					SGameEngine.get().addToNetStack(messageParts[7]);	
				}else{
					SGameEngine.get().addToNetStack((GameObjectGroup) SGameEngine.get().getByOID(messageParts[7]));
				}
				//SGameEngine.get().addObject(new GameObjectGroup(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]), (GameObjectGroup) SGameEngine.get().getByOID(messageParts[7]), false);
			}
		} else if (messageParts[3].equals("FixedBlock")) {
			SGameEngine.get().addToNetStack(new FixedBlock(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]));
			SGameEngine.get().addToNetStack((GameObjectGroup) SGameEngine.get().getByOID(messageParts[7]));
			//SGameEngine.get().addObject(new FixedBlock(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]), (GameObjectGroup) SGameEngine.get().getByOID(messageParts[7]), false);
		} else if (messageParts[3].equals("IceBlock")) {
			SGameEngine.get().addToNetStack(new IceBlock(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6], Integer.parseInt(messageParts[7])));
			SGameEngine.get().addToNetStack((GameObjectGroup) SGameEngine.get().getByOID(messageParts[8]));
			//SGameEngine.get().addObject(new IceBlock(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6], Integer.parseInt(messageParts[7])), (GameObjectGroup) SGameEngine.get().getByOID(messageParts[8]), false);
		} else if (messageParts[3].equals("Wall")) {
			SGameEngine.get().addToNetStack(new Wall(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]));
			SGameEngine.get().addToNetStack((GameObjectGroup) SGameEngine.get().getByOID(messageParts[7]));
			//SGameEngine.get().addObject(new Wall(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]), (GameObjectGroup) SGameEngine.get().getByOID(messageParts[7]), false);
		} else if (messageParts[3].equals("Exit")) {
			SGameEngine.get().addToNetStack(new Exit(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]));
			SGameEngine.get().addToNetStack((GameObjectGroup) SGameEngine.get().getByOID(messageParts[7]));
			//SGameEngine.get().addObject(new Exit(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6]), (GameObjectGroup) SGameEngine.get().getByOID(messageParts[7]), false);
		} else if (messageParts[3].equals("Player")) {
			//mid mtype mname objtype objx objy name oid parent_oid
			if (messageParts[7].equals(playeroid)) {
				Player player = new Player(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6], 1, messageParts[7]);
				SGameEngine.get().setPlayer(player, false);
			} else {
				Player player = new Player(Integer.parseInt(messageParts[4]), Integer.parseInt(messageParts[5]), messageParts[6], 1, messageParts[7]);
				SGameEngine.get().addToNetStack(player);
				SGameEngine.get().addToNetStack((GameObjectGroup) SGameEngine.get().getByOID(messageParts[8]));
				//SGameEngine.get().addObject(player, (GameObjectGroup) SGameEngine.get().getByOID(messageParts[8]), false);
			}
		}
		return null;
	}

	private String EndConnectionPhaseBroadcast(String[] messageParts) {
		this.ConnectionPhase = false;
		this.PlayerSelectionPhase = true;
		return "";
	}

	private String EndMapInitializationPhaseBroadcast(String[] messageParts) {
		MapInitializationPhase = false;
		GamePhase = true;
		SGameEngine.get().startGame();
		return null;
	}

	private String EndPlayerSelectionPhaseBroadcast(String[] messageParts) {
		this.ConnectionPhase = false;
		this.PlayerSelectionPhase = false;
		this.MapInitializationPhase = true;
		return "";
	}

	private String getMessageID() {
		return "MID" + messageCount;
	}

	public String[] messageParse(String message) {
		return message.split("\\s");
	}

	private String PlayerOID(String[] messageParts) {
		playeroid = messageParts[3];
		// TODO Auto-generated method stub
		return null;
	}

	public String processInput(String input) {
		String[] messageParts = this.messageParse(input);
		if (ConnectionPhase) {
			if (messageParts[2].equals("ClientConnect") && messageParts[3].equals("Accepted")) return getMessageID() + " Request SyncTime " + System.currentTimeMillis();
			if (messageParts[2].equals("ClientConnect") && messageParts[3].equals("Rejected")) return "";
			if (messageParts[2].equals("SyncTime")) return SyncTime(messageParts);
			if (messageParts[2].equals("EndConnectionPhase")) return EndConnectionPhaseBroadcast(messageParts);
		} else if (PlayerSelectionPhase) {
			if (messageParts[2].equals("UserName") && messageParts[1].equals("Request")) return UserNameRequest(messageParts);
			if (messageParts[2].equals("UserColor") && messageParts[1].equals("Request")) return UserColorRequest(messageParts);
			if (messageParts[2].equals("EndPlayerSelectionPhase")) return EndPlayerSelectionPhaseBroadcast(messageParts);
		} else if (MapInitializationPhase) {
			if (messageParts[2].equals("AddObject")) return AddObject(messageParts);
			if (messageParts[2].equals("AddPlayer")) return PlayerOID(messageParts);
			if (messageParts[2].equals("EndMapInitializationPhase")) return EndMapInitializationPhaseBroadcast(messageParts);
		} else if (GamePhase) {
			//if (messageParts[2].equals("AddObject")) return AddObject(messageParts);
			//if(messageParts[2].equals("RemoveObject"))return RemoveObject(messageParts);
			if (messageParts[2].equals("AddAction")) return AddAction(messageParts);
			//if(messageParts[2].equals("RemoveAction"))return RemoveAction(messageParts);
		} else if (GameEndPhase) {

		}
		return "";
	}

	private String SyncTime(String[] messageParts) {
		SGameEngine.get().calcSyncTime(Long.parseLong(messageParts[3]), System.currentTimeMillis());
		//this.nE.setSyncTime(Long.parseLong(messageParts[3]));
		return "";
	}

	private String UserColorRequest(String[] messageParts) {
		//TODO let Player Select Color
		return messageParts[0] + " Response UserColor YourChoice";
	}

	private String UserNameRequest(String[] messageParts) {
		//TODO let Player Select Name
		return messageParts[0] + " Response UserName YourChoice";
	}
}
