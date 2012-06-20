package netzwerk;

import java.net.*;
import java.io.*;
/*
 *  Autor: Jens Herrmann
 */
public class NetzwerkClass implements Runnable{

/*
 *  In dieser Klasse wird der Dienst für das Spielen im Netzwerk bereitgestellt.
 *  Aktiviert wird dieser Dienst, wenn im Anfangsfenster ein Haken gesetzt wird, so
 *  wird ein ServerSocket eingerichtet.
 */

static InetAddress Add;
static boolean netzwerkAktiv = false; // Die boolesche Abfrage
static ServerSocket BomberServer; // Der ServerSocket
static Socket ClientBomberServer; // Zweiter ServerSocket
//Socket BomberClient; //Ein "localhost"
static InputStream inStream; // Deklaration des eingehenden Datenstromes
static OutputStream outStream; // Deklaration des ausgehenden Datenstromes
//protected static final Exception IOException = null;

/*
 * ______________________________Methoden______________________________________
 */

// Methode für die ServerSocket aktivierung
public void ServerAktivierung() {
	
		try {
			BomberServer = new ServerSocket(1234); // Instanzziierung eines ServerSocket
		}
		catch(IOException e){} 
		}




// Die Methode, die die Verbindung mit dem Socket herstellt
public void ServerInstantiierung() {
		
		try {
			
			while(true) {    // Endlos nach einer Verbindung lauschen
				Socket ClientBomberServer = BomberServer.accept(); // Die Verbindung akzeptieren 
				if (true) {
					ClientBomberServer.setKeepAlive(true); //Setze true, wenn die Verbindung steht
					Add = ClientBomberServer.getLocalAddress(); //Setze true, wenn die Verbindung steht
					//ClientBomberServer.setReceiveBufferSize(5000);	// Setze einen Buffer, um eine bessere Verbindung zu gewährleisten
					inStream =  ClientBomberServer.getInputStream(); // Eingehenden Datenstrom herstellen
					outStream = ClientBomberServer.getOutputStream(); // Ausgehenden Datenstrom herstellen
					
					
				}
				System.out.printf("Sie sind mit" ,Add, "verbunden.");
			}
		} catch(IOException e) {
			e.printStackTrace(); //Bei Ausgabe den Fehler ausgeben
			}
		
}





// Mit dieser Methode wird die Verbindung, falls diese nicht schon durch einen Fehler getrennt wurde
public void ServerSchließen( ) throws IOException {
	
	try {
	ClientBomberServer.getKeepAlive();
	}
	catch(SocketException e){ System.out.println("Es ist ein Fehler aufgetreten!");
	
	ClientBomberServer.close(); // Schließen des Servers bei einer SocketException
	
	}
	ClientBomberServer.close(); // Bei Ausführung dieser Methode ebenfalls schließen
}


@Override
public void run() {
	// TODO Auto-generated method stub
}




/*
 * __________________________________Was ist noch zu tun bzw. wird gemacht___________________________
 * 
 * 1. Einbindung in die Main-Methode und die Game Engine
 * 2. Eine Methode zur Zuweisung von Socket und ServerSocket
 * 3. Ein Chat-Modul zur einfachen Kommunikation
 */

}
