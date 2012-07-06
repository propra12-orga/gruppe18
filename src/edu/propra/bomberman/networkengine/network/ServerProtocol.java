package edu.propra.bomberman.networkengine.network;

import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.actions.BombDownAction;
import edu.propra.bomberman.gameengine.actions.GameOverAction;
import edu.propra.bomberman.gameengine.actions.StartMoveAction;
import edu.propra.bomberman.gameengine.actions.StopMoveAction;
import edu.propra.bomberman.gameengine.objects.*;
import edu.propra.bomberman.graphicengine.SGTransform;

public class ServerProtocol {

	public boolean			ConnectionPhase			= true;
	public boolean			GameEndPhase			= false;
	public boolean			GamePhase				= false;
	public boolean			initializing			= false;
	public boolean			MapInitializationPhase	= false;

	long					messageCount;
	private NetworkEngine	nE;
	public boolean			PlayerSelectionPhase	= false;
	private ServerClient	sC;

	/*
	 * Message Definition
	 * MessageID MessageType Message MessageData*
	 */

	public ServerProtocol(NetworkEngine nE, ServerClient sc) {
		this.nE = nE;
		this.sC = sc;
	}

	private String AddActionBroadcast(String[] messageParts) {
		if (messageParts[3].equals("BombDownAction")) {
			if (Integer.parseInt(messageParts[5]) < -9999) return null;
			Object actor = SGameEngine.get().getByOID(messageParts[7]);
			SGameEngine.get().addToNetStack(new BombDownAction(actor, Long.parseLong(messageParts[8]), messageParts[4], Integer.parseInt(messageParts[5]), Integer.parseInt(messageParts[6])));
			//SGameEngine.get().addAction(new BombDownAction(actor, Long.parseLong(messageParts[8]) + this.nE.syncTime, messageParts[4], Integer.parseInt(messageParts[5]), Integer.parseInt(messageParts[6])), false);
		} else if (messageParts[3].equals("StartMoveAction")) {
			Object actor = SGameEngine.get().getByOID(messageParts[5]);
			
			SGameEngine.get().addToNetStack(new StartMoveAction(Integer.parseInt(messageParts[4]), actor, Long.parseLong(messageParts[6]),Integer.parseInt(messageParts[7]),Integer.parseInt(messageParts[8])));
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
			/*if (messageParts[4].equals(playeroid)) {
				Player actor = (Player) SGameEngine.get().getByOID(messageParts[4]);
				if (actor.death) {
					return "MIDXXX Broadcast PlayerDeadAction 1";
				} else {
					return "MIDXXX Broadcast PlayerDeadAction 0";
				}
			}*/
		} else if (messageParts[3].equals("PlayerWonAction")) {

		}
		return null;
	}

	private String ClientConnect(String[] messageParts) {
		if (nE.server.isMaxClient()) {
			return getMessageID() + "Response ClientConnect Rejected";
		} else {
			nE.server.ClientConnect();
			sC.PID = messageParts[3];
			return getMessageID() + " Response ClientConnect Accepted " + sC.PID;
		}
	}

	private String getMessageID() {
		return "MID" + messageCount;
	}

	public String[] messageParse(String message) {
		return message.split("\\s");
	}

	public String processInput(String input) {
		String[] messageParts = this.messageParse(input);

		if (ConnectionPhase) {
			if (messageParts[2].equals("ClientConnect")) {
				return ClientConnect(messageParts);
			}
			if (messageParts[2].equals("SyncTime")) return SyncTimeRequest(messageParts);
		} else if (PlayerSelectionPhase) {
			if (messageParts[2].equals("UserName")) return UserNameResponse(messageParts);
			if (messageParts[2].equals("UserColor")) return UserColorResponse(messageParts);
		} else if (MapInitializationPhase) {

		} else if (GamePhase) {
			if (messageParts[2].equals("AddAction")) {
				return AddActionBroadcast(messageParts);
			}
			//TODO react if player is not death at owner game
			//if(messageParts[2].equals("PlayerDeadAction")){return PlayerDeadAction}
		} else if (GameEndPhase) {

		}

		return "";
	}

	private String SyncTimeRequest(String[] messageParts) {
		//this.sC.setSyncTime(Long.parseLong(messageParts[3]));
		return messageParts[0] + " Response SyncTime " + System.currentTimeMillis();
	}

	private String UserColorResponse(String[] messageParts) {
		if (messageParts[3].equals("YourChoice")) {
			this.sC.color = "default";
		} else {
			this.sC.color = messageParts[3];
		}
		this.nE.playerToldColor();
		this.nE.isPlayerPhaseOver();
		return "";
	}

	private String UserNameResponse(String[] messageParts) {
		if (messageParts[3].equals("YourChoice")) {
			this.sC.name = "Player_" + sC.PID;
		} else {
			this.sC.name = messageParts[3];
		}
		this.nE.playerToldName();
		this.nE.isPlayerPhaseOver();
		return "";
	}

}
