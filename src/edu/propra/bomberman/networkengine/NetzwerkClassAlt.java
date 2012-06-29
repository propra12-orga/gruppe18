package edu.propra.bomberman.networkengine.netzwerk;

import java.net.*;
import java.io.*;

/*
 * Autor Jens Herrmann
 */
public class NetzwerkClassAlt {

	static int	backlog	= 10;
	static int	port	= 2233;
	boolean		Server	= false;

	public void BomberClient() {
	
	try {
		// Erzeugung eines Client mit Ein- und Ausgabeströmen
		Socket BomberClient = new Socket(Bomberman, Port);
		BomberClient.getInputStream(BomberServer.outputStream);
		BomberClient.getOutputStream();
	
		catch(IOException e) {
			e.printStackTrace(); //Bei Ausgabe den Fehler ausgeben
		}	
	}
}

	public void TekkiClient() {
	
	try {
		// Erzeugung eines Client mit Ein- und Ausgabeströmen
		Socket TekkiClient = new Socket(Bomberman, Port);
		TekkiClient.getInputStream(BomberServer.outputStream);
		TekkiClient.getOutputStream();
	
		catch(IOException e) {
			e.printStackTrace(); //Bei Ausgabe den Fehler ausgeben
		}	
	}
}	/*
	 * Eine Alternative für die Server-Socket Instantiierung
	 */

	public void ServerSocket() {
		
		// Abfrage, ob ein Server gewünscht ist
		if (Server == true) {
			
			try {
				// Erzeugung eines ServerSocket mit Port und Backlog
				ServerSocket BomberServer = new ServerSocket(port, backlog);
					// Eine Endlosschleife
					while (true) { 
					
						System.out.println(Das Bomben kann bald losgehen...);
						Socket BomberClient = socket.accepted();
						Socket TekkiClient = socket.accepted();
						PrintStream outStream = new PrintStream( BomberClient.getOutputStream();
						PrintStream outStream = new PrintStream( TekkiClient.getOutputStream();
						
						
					catch(IOException e) {
						e.printStackTrace(); //Bei Ausgabe den Fehler ausgeben
							}
					
				}
			}
		}
	}
}
