package edu.propra.bomberman.networkengine.network;

import edu.propra.bomberman.gameengine.SGameEngine;

public class NetworkEngine {
	public static void main(String[] args) {
		NetworkEngine ne = new NetworkEngine();
		ne.connect();
		ne.start();
	}

	Client			client;
	String			clientID;
	public boolean	networkGame			= false;
	int				playerColorsNeeded	= -1;

	int				playerNamesNeeded	= -1;

	Server			server;

	long			syncTime;

	public void broadcastMessage(String message) {
		if (server != null) {
			server.broadcastMessage(message);
		} else {
			client.submitMessage(message);
		}
	}

	public void broadcastMessage(String message, ServerClient socket) {
		if (server != null) {
			server.broadcastMessage(message, socket);
		}
	}

	public void clientGone(ServerClient serverClient) {
		this.server.clientGone(serverClient);
	}

	public void connect() {
		client = new Client(this);
		if (client.connect()) {
			System.out.println("I'm a client");
			server = null;
		} else {
			System.out.println("I'm a server");
			client = null;
			server = new Server(this);
			server.connect();
		}
	}

	public void InitializeMapPhaseOver() {
		server.ChangeState(3);
		this.server.broadcastMessage("IDPPP Broadcast EndMapInitializationPhase");
	}

	public void isPlayerPhaseOver() {
		if (this.playerColorsNeeded == 0 && this.playerNamesNeeded == 0) {
			this.PlayerSelectionPhaseOver();
		}
	}

	public boolean isServer() {
		return this.server != null;
	}

	public void maxClientsReached() {
		System.out.println("MaxClientsReached");
		//server.listening=false;
		//STOPING CONNECTION PHASE
		server.ChangeState(1);
		server.broadcastMessage("IDSTART Broadcast EndConnectionPhase");
		playerNamesNeeded = server.clients.size();
		server.broadcastRequest("IDSTART Request UserName");
		playerColorsNeeded = server.clients.size();
		server.broadcastRequest("IDSTART Request UserColor");
	}

	private void PlayerSelectionPhaseOver() {
		server.ChangeState(2);
		SGameEngine.get().AllPlayersConnected();
	}

	public void playerToldColor() {
		playerColorsNeeded--;
	}

	public void playerToldName() {
		playerNamesNeeded--;
	}

	public void releaseData() {

	}

	public void setClientID(String string) {
		this.clientID = clientID;
	}

	public void setSyncTime(long time) {
		//TODO test if time sync is correct
		this.syncTime = System.currentTimeMillis() - time;
	}

	public void start() {
		if (client != null) new Thread(client).start();
		if (server != null) new Thread(server).start();
		this.networkGame = true;
		System.out.println("Network is running");
	}

}
