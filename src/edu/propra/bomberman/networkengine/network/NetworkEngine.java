package edu.propra.bomberman.networkengine.network;

import java.util.Iterator;

import edu.propra.bomberman.gameengine.SGameEngine;

public class NetworkEngine {
	String clientID;
	Client client;
	Server server;
	long syncTime;
	public boolean networkGame=false;
	
	public void connect(){
		client=new Client(this);
		if(client.connect()){
			System.out.println("I'm a client");
			server=null;
		}else{
			System.out.println("I'm a server");
			client=null;
			server=new Server(this);
			server.connect();
		}
	}
	
	public void start(){
		if(client!=null)new Thread(client).start();
		if(server!=null)new Thread(server).start();
		this.networkGame=true;
		System.out.println("Network is running");
	}
	
	
	public void releaseData() {
		
	}
	
	public static void main(String[] args) {
		NetworkEngine ne=new NetworkEngine();
		ne.connect();
		ne.start();
	}

	public void setSyncTime(long time) {
		//TODO test if time sync is correct
		this.syncTime=System.currentTimeMillis()-time;
	}

	public void setClientID(String string) {
		this.clientID=clientID;
	}

	int playerNamesNeeded=-1;
	int playerColorsNeeded=-1;
	public void maxClientsReached() {
		System.out.println("MaxClientsReached");
		//server.listening=false;
		//STOPING CONNECTION PHASE
		server.ChangeState(1);
		server.broadcastMessage("IDSTART Broadcast EndConnectionPhase");
		playerNamesNeeded=server.clients.size();
		server.broadcastRequest("IDSTART Request UserName");
		playerColorsNeeded=server.clients.size();
		server.broadcastRequest("IDSTART Request UserColor");
	}
	public void playerToldName(){playerNamesNeeded--;}
	public void playerToldColor(){playerColorsNeeded--;}
	
	public void clientGone(ServerClient serverClient) {
		this.server.clientGone(serverClient);
	}

	public void isPlayerPhaseOver() {
		if(this.playerColorsNeeded==0 && this.playerNamesNeeded==0){
			this.PlayerSelectionPhaseOver();
		}
	}

	private void PlayerSelectionPhaseOver() {
		server.ChangeState(2);
		SGameEngine.get().AllPlayersConnected();
	}
	public void InitializeMapPhaseOver(){
		server.ChangeState(3);
		this.server.broadcastMessage("IDPPP Broadcast EndMapInitializationPhase");	
	}
	
	public void broadcastMessage(String message){
		if(server!=null){
			server.broadcastMessage(message);		
		}else{
			client.submitMessage(message);
		}
	}
	public void broadcastMessage(String message,ServerClient socket){
		if(server!=null){
			server.broadcastMessage(message,socket);
		}
	}
	public boolean isServer() {
		return this.server!=null;
	}
	

}
