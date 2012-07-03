package edu.propra.bomberman.networkengine.network;

import java.net.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

public class Server implements Runnable {
	ArrayDeque<ServerClient> clients;
	ServerSocket	serverSocket;
	boolean			listening;
	NetworkEngine nE;
	public Server(NetworkEngine nE) {
		this.serverSocket = null;
		this.listening = true;
		this.nE=nE;
		this.clients=new ArrayDeque<ServerClient>();
	}

	public void connect() {
		try {
			serverSocket = new ServerSocket(4444,1,InetAddress.getLocalHost());
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(-1);
		}
		System.out.println(serverSocket.getInetAddress().getHostAddress());
	}
	
	
	@Override
	public void run() {
		try {
			serverSocket.setSoTimeout(50);
			Socket nC=null;
			while (listening){
				try{
					nC=serverSocket.accept();
				}catch(SocketTimeoutException e){
					if(this.isMaxClient()){
						listening=false;
					}
					continue;
				}
				clients.add(new ServerClient(nC,nE,"PID"+(this.ActClients+1)));
				new Thread(clients.peekLast()).start();
			}
			serverSocket.close();
			this.nE.maxClientsReached();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void stop(){
		listening=false;
	}
	
	public void broadcastMessage(String message){
		Iterator<ServerClient> clientIT=clients.iterator();
		while(clientIT.hasNext()){
			clientIT.next().submitMessage(message);
		}
	}

	public void broadcastMessage(String message, ServerClient sender) {
		Iterator<ServerClient> clientIT=clients.iterator();
		ServerClient next;
		while(clientIT.hasNext()){
			next=clientIT.next();
			if(next!=sender)next.submitMessage(message);
		}
	}

	public void clientGone(ServerClient serverClient) {
		this.clients.remove(serverClient);
	}
	public ServerClient getClientByPID(String pid){
		Iterator<ServerClient> clientIT=clients.iterator();
		ServerClient next;
		while(clientIT.hasNext()){
			next=clientIT.next();
			if(next.PID.equals(pid))return next;
		}
		return null;
	}
	
	private int MaxClients=1;
	private int ActClients=0;
	public void ClientConnect(){
		ActClients++;
	}
	private void ClientDisconnect(){
		ActClients--;
	}

	public boolean isMaxClient(){
		return (ActClients>=MaxClients);
	}

	public void broadcastRequest(String message) {
		Iterator<ServerClient> clientIT=clients.iterator();
		while(clientIT.hasNext()){
			clientIT.next().submitMessage(message);
		}
	}
	
	public void ChangeState(int state){
		for(ServerClient client : this.clients){
			client.setState(state);
		}
	}
}
