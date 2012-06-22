package edu.propra.bomberman.networkengine.netzwerk;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class BombermanSocketTest2 extends NetzwerkClass implements Runnable {

	/*
	 * Dies ist ein Codefragment, dass nur als Test
	 */

	public BombermanSocketTest2(boolean aktivVer, InetAddress add, boolean netzwerkAktiv, ServerSocket bomberServer, Socket clientBomberServer, InputStream inStream, OutputStream outStream) {
		super(aktivVer, add, netzwerkAktiv, bomberServer, clientBomberServer, inStream, outStream);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		// Memo: statische Variblen erstellt

		NetzwerkClass test2 = new NetzwerkClass(aktivVer, Add, aktivVer, BomberServer, ClientBomberServer, inStream, outStream);
		test2.ServerAktivierung();
		test2.ServerInstantiierung();

	}
}