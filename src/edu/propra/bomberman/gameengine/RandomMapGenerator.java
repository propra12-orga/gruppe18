package edu.propra.bomberman.gameengine;

import edu.propra.bomberman.gameengine.objects.*;

/**
 * @author Jens Herrmann
 */

public class RandomMapGenerator {

	/**
	 * @version 2.1
	 */
	
	// Platzhalter für die zu erstellenden Objekte
	int Start1 = 1;
	int Start2 = 2;
	int Ende1 = 3;
	int EisBlock = 4;
	int StatBlock = 5;
	int Wand = 6;
	// boolean, um bei erstelltem Objekt auf true zu setzen
	boolean Start1bel = false;
	boolean Start2bel = false;
	boolean Endebel = false;
	boolean Wandbel = false;
	// Hilfsarrays für einmal und mehrmals zu erstellende Objekte
	boolean[] ObjBel = { Start1bel, Start2bel, Endebel, Wandbel };
	int[] Objekt = { Start1, Start2, Ende1, Wand };
	int[] AltObjekt = { EisBlock, StatBlock };
	int i, x, y, z;


	/**
	 * Die beiden obigen Werte sollen über Spielereingabe entgegengenommen
	 * werden, um eine mögliche Unspielbarkeit vorzubeugen oder eine
	 * Beeinflussung des Schwierigkeitsgrades bereitzuhalten
	 */
	//
	// _______________________________________________________________________________________

	public GameObjectGroup RandomMap() {

		// Array zum Zwischenspeichern der Belegungen, um Doppelbelegungen zu
		// vermeiden
		int[][] used = new int[19][14];
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 14; j++) {
				used[i][j] = -1;
			}

		}

		/*
		 * Einmal gebrauchte Objekte werden sofort durch Zufallszahlen einer
		 * Position zu geordnet
		 */
		GameObjectGroup root=new GameObjectGroup(0, 0, "oid"+SGameEngine.get().ObjectCounter);
		SGameEngine.get().addObject(root, null);
	


		x = (int) (Math.random() * 19.0 * 1.0);
		y = (int) (Math.random() * 14.0 * 1.0);
		Wall Wand = new Wall(0, 0,"oid"+SGameEngine.get().ObjectCounter);
		SGameEngine.get().addObject(Wand,root);
		used[x][y] = 6;
		System.out.println("Wall " + "x:" + x + " y:" + y);
	
		GameObjectGroup container=new GameObjectGroup(25, 25, "oid"+SGameEngine.get().ObjectCounter);
		SGameEngine.get().addObject(container, root);
	
		// StartPoint Start1 = new StartPoint(x, y,"Spieler 1");
		// SGameEngine.get().addObject(Start1);
		x = (int) (Math.random() * 19.0 * 1.0);
		y = (int) (Math.random() * 14.0 * 1.0);
		// Start1bel = true;
		used[x][y] = 1;
		System.out.println("Startpoint1 " + "x:" + x + " y:" + y);

		// StartPoint Start2 = new StartPoint(x, y,"Spieler 2");
		// SGameEngine.get().addObject(Start2);
		x = (int) (Math.random() * 19.0 * 1.0);
		y = (int) (Math.random() * 14.0 * 1.0);
		// Start2bel = true;
		used[x][y] = 2;
		System.out.println("Startpoint2 " + "x:" + x + " y:" + y);

		Exit Ende1 = new Exit(x*40, y*40,"oid"+SGameEngine.get().ObjectCounter);
		 SGameEngine.get().addObject(Ende1,container);
		x = (int) (Math.random() * 8.0 * 1.0);
		y = (int) (Math.random() * 9.0 * 1.0);
		// Endebel = true;
		used[x][y] = 3;
		System.out.println("Exit " + "x:" + x + " y:" + y);


		/**
		 * @Param Zufällige Werte für die Objekte FixedBlock und IceBlock. x,
		 *            y sind Werte für die Koordinaten auf dem Spielfeld und
		 *            werden im Array used gespeichert Mit der Variable Counter
		 *            kann später der Schwierigkeitsgrat variiert werden, da
		 *            dieser die Anzahl der Fixed- und IceBlöcke angibt, letzere
		 *            sind die zerstörbaren
		 */

	
		for (int counter = 0; counter <= 90; counter++) {
			
			x = (int) (Math.random() * 19.0 * 1.0);
			y = (int) (Math.random() * 14.0 * 1.0);
			z = (int) (Math.random() * 2.0 * 1.0);
			/**
			 * @Paramargs Sind die beide Start- und die Endeposition auf true
			 *            gesetzt, dann wird in den else-Part gesprungen
			 */

			if ((z == 0) && (used[x][y])==-1) {
				FixedBlock StatBlock = new FixedBlock(x*40, y*40,"oid"+SGameEngine.get().ObjectCounter);
				SGameEngine.get().addObject(StatBlock,container);
				used[x][y] = 4;
				System.out.println("Fixed Block " + "x:" + x + " y:" + y);
				continue;
			}

			if ((z == 1) && (used[x][y])==-1) {
				IceBlock EisBlock = new IceBlock(x*40, y*40,"oid"+SGameEngine.get().ObjectCounter,-1);
				 SGameEngine.get().addObject(EisBlock,container);
				used[x][y] = 5;
				System.out.println("Ice Block " + "x:" + x + " y:" + y);
				continue;
			}
		}
	
System.out.println("Die Karte ist erstellt");
		for (x = 1; x < 18; x++) {
			for (y = 1; y < 13; y++) {
				if (used[x][y] == 1) {
					if ((used[x+1][y+1] == (3 | 4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x-1][y-1] == (3 | 4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x+1][y-1] == (3 | 4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x-1][y+1] == (3 | 4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
				}
				else if (used[x][y] == 2) {
					if ((used[x+1][y+1] == (3 | 4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x-1][y-1] == (3 | 4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x+1][y-1] == (3 | 4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x-1][y+1] == (3 | 4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
				}
				else if (used[x][y] == 3) {
					if ((used[x+1][y+1] == (4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x-1][y-1] == (4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x+1][y-1] == (4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if ((used[x-1][y+1] == (4 | 5 | 6)) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
				}
			}
		}
		return root;
	}

	public static void main(String[] args) {
		RandomMapGenerator mapgen = new RandomMapGenerator();
		mapgen.RandomMap();
	}
}
