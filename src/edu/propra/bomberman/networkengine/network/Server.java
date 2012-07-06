package edu.propra.bomberman.networkengine.network;

import java.net.*;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.io.*;

import edu.propra.bomberman.ui.Options;

public class Server implements Runnable {
	private int					ActClients	= 0;
	ArrayDeque<ServerClient>	clients;
	boolean						listening;
	private int					MaxClients	= 1;
	NetworkEngine				nE;

	ServerSocket				serverSocket;

	public Server(NetworkEngine nE) {
		this.serverSocket = null;
		this.listening = true;
		this.nE = nE;
		this.clients = new ArrayDeque<ServerClient>();
	}

	public void broadcastMessage(String message) {
		Iterator<ServerClient> clientIT = clients.iterator();
		while (clientIT.hasNext()) {
			clientIT.next().submitMessage(message);
		}
	}

	public void broadcastMessage(String message, ServerClient sender) {
		Iterator<ServerClient> clientIT = clients.iterator();
		ServerClient next;
		while (clientIT.hasNext()) {
			next = clientIT.next();
			if (next != sender) next.submitMessage(message);
		}
	}

	public void broadcastRequest(String message) {
		Iterator<ServerClient> clientIT = clients.iterator();
		while (clientIT.hasNext()) {
			clientIT.next().submitMessage(message);
		}
	}

	public void ChangeState(int state) {
		for (ServerClient client : this.clients) {
			client.setState(state);
		}
	}

	public void ClientConnect() {
		ActClients++;
	}

	private void ClientDisconnect() {
		ActClients--;
	}

	public void clientGone(ServerClient serverClient) {
		this.clients.remove(serverClient);
	}

	public void connect() {
		try {
			//serverSocket = new ServerSocket(4444, 1, InetAddress.getLocalHost());
			serverSocket = new ServerSocket(Integer.parseInt(Options.PortName));
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(-1);
		}
		System.out.println(serverSocket.getInetAddress().getHostAddress());
	}

	public ServerClient getClientByPID(String pid) {
		Iterator<ServerClient> clientIT = clients.iterator();
		ServerClient next;
		while (clientIT.hasNext()) {
			next = clientIT.next();
			if (next.PID.equals(pid)) return next;
		}
		return null;
	}

	public boolean isMaxClient() {
		return (ActClients >= MaxClients);
	}

	@Override
	public void run() {
		try {
			serverSocket.setSoTimeout(50);
			Socket nC = null;
			while (listening) {
				try {
					nC = serverSocket.accept();
				} catch (SocketTimeoutException e) {
					if (this.isMaxClient()) {
						listening = false;
					}
					continue;
				}
				clients.add(new ServerClient(nC, nE, "PID" + (this.ActClients + 1)));
				new Thread(clients.peekLast()).start();
			}
			serverSocket.close();
			this.nE.maxClientsReached();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		listening = false;
	}
}
