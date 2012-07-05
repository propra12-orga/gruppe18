package edu.propra.bomberman.networkengine.network;

import java.net.*;
import java.io.*;

public class ServerClient implements Runnable {

	ServerProtocol	bp;
	public String	color;
	BufferedReader	in;
	String			name;
	NetworkEngine	nE;
	PrintWriter		out;
	String			PID;

	private boolean	running		= true;

	private Socket	socket		= null;
	long			syncTime	= 0;

	public ServerClient(Socket socket, NetworkEngine nE, String pid) {
		this.socket = socket;
		this.nE = nE;
		this.PID = pid;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bp = new ServerProtocol(nE, this);
	}

	public String receiveMessage(String message) {
		if (message == null || message.length() == 0) return null;
		System.out.println("<-- : " + message);
		return bp.processInput(message);
	}

	@Override
	public void run() {
		String input, output;
		try {
			//Send Connection accepted
			output = this.receiveMessage("MID0 Request ClientConnect " + this.PID);
			this.submitMessage(output);
			while ((input = in.readLine()) != null && running) {
				if (this.bp.messageParse(input)[1].equals("Broadcast")) this.nE.broadcastMessage(input, this);
				output = this.receiveMessage(input);
				this.submitMessage(output);
			}
			out.close();
			in.close();
			socket.close();
			nE.clientGone(this);

		} catch (SocketException e) {
			System.err.println("Socket Exception");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				this.in.close();
				this.out.close();
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setState(int state) {
		System.out.println("Phase " + state);
		if (state == 0) {
			this.bp.ConnectionPhase = true;
			this.bp.GameEndPhase = false;
			this.bp.GamePhase = false;
			this.bp.MapInitializationPhase = false;
			this.bp.PlayerSelectionPhase = false;
		}
		if (state == 1) {
			this.bp.ConnectionPhase = false;
			this.bp.GameEndPhase = false;
			this.bp.GamePhase = false;
			this.bp.MapInitializationPhase = false;
			this.bp.PlayerSelectionPhase = true;
		}
		if (state == 2) {
			this.bp.ConnectionPhase = false;
			this.bp.GameEndPhase = false;
			this.bp.GamePhase = false;
			this.bp.MapInitializationPhase = true;
			this.bp.PlayerSelectionPhase = false;
		}
		if (state == 3) {
			this.bp.ConnectionPhase = false;
			this.bp.GameEndPhase = false;
			this.bp.GamePhase = true;
			this.bp.MapInitializationPhase = false;
			this.bp.PlayerSelectionPhase = false;
		}
		if (state == 4) {
			this.bp.ConnectionPhase = false;
			this.bp.GameEndPhase = true;
			this.bp.GamePhase = false;
			this.bp.MapInitializationPhase = false;
			this.bp.PlayerSelectionPhase = false;
		}
	}

	public void setSyncTime(long time) {
		//TODO test synctime;
		syncTime = System.currentTimeMillis() - time;
	}

	public void submitMessage(String message) {
		if (message == null || message.length() == 0) return;
		System.out.println("--> : " + message);
		out.println(message);
		System.out.println("Message gone: "+System.currentTimeMillis());
	}
}
