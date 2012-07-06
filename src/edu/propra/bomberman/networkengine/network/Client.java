package edu.propra.bomberman.networkengine.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import edu.propra.bomberman.ui.Options;

public class Client implements Runnable {
	ClientProtocol	bp;
	BufferedReader	in	= null;
	boolean			listen;
	NetworkEngine	nE;
	PrintWriter		out	= null;
	String			PID;
	Socket			socket;

	public Client(NetworkEngine nE) {
		this.nE = nE;
		this.socket = null;
		this.listen = true;
		this.bp = new ClientProtocol(nE);
	}

	public boolean connect() {
		boolean connected = false;
		try {
			if(Options.HostName.equals("localhost")){
				socket = new Socket(InetAddress.getLocalHost(), Integer.parseInt(Options.PortName));
			}else{
				socket = new Socket(Options.HostName, Integer.parseInt(Options.PortName));
			}
			connected = true;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (ConnectException e) {
			//System.out.println("Keine Verbindung möglich");
			connected = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connected;
	}

	public String receiveMessage(String message) {
		if (message == null || message.length() == 0) return "";
		System.out.println("<-- : " + message);
		return bp.processInput(message);
	}

	@Override
	public void run() {
		//System.out.println("Client is listening");
		try {
			while (listen) {
				String input = in.readLine();
				//System.out.println("Message received: "+System.currentTimeMillis());
				String output = this.receiveMessage(input);
				this.submitMessage(output);
			}
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

	public void submitMessage(String message) {
		if (message == null || message.length() == 0) return;
		System.out.println("--> : " + message);
		out.println(message);
	}

}
