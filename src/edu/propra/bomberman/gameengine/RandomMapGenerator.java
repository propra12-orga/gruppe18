package edu.propra.bomberman.gameengine;

import edu.propra.bomberman.gameengine.objects.*;

/**
 * @author Jens Herrmann
 */

public class RandomMapGenerator {

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

	// Getter und Setter für die boole'schen Variablen. Werden gelöscht, wenn
	// nicht benötigt
	public boolean isStart1bel() {
		return Start1bel;
	}

	public void setStart1bel(boolean start1bel) {
		Start1bel = start1bel;
	}

	public boolean isStart2bel() {
		return Start2bel;
	}

	public void setStart2bel(boolean start2bel) {
		Start2bel = start2bel;
	}

	public boolean isEndebel() {
		return Endebel;
	}

	public void setEndebel(boolean endebel) {
		Endebel = endebel;
	}

	/*
	 * Die beiden obigen Werte sollen über Spielereingabe entgegengenommen
	 * werden, um eine mögliche Unspielbarkeit vorzubeugen oder eine
	 * Beeinflussung des Schwierigkeitsgrades bereitzuhalten
	 */
	//
	// _______________________________________________________________________________________

	public void RandomMap() {

		// Array zum Zwischenspeichern der Belegungen, um Doppelbelegungen zu
		// vermeiden
		int[][] used = new int[25][25];
		for (int i = 0; i <= 24; i++) {
			for (int j = 0; j <= 24; j++) {
				used[i][j] = -1;
			}

		}

		/*
		 * Einmal gebrauchte Objekte werden sofort durch Zufallszahlen einer
		 * Position zu geordnet
		 */

		// StartPoint Start1 = new StartPoint(x, y,"Spieler 1");
		// SGameEngine.get().addObject(Start1);
		x = (int) (Math.random() * 20.0 * 1.0);
		y = (int) (Math.random() * 20.0 * 1.0);
		// Start1bel = true;
		used[x][y] = 1;
		System.out.println("Startpoint1 " + "x:" + x + " y:" + y);

		// StartPoint Start2 = new StartPoint(x, y,"Spieler 2");
		// SGameEngine.get().addObject(Start2);
		x = (int) (Math.random() * 20.0 * 1.0);
		y = (int) (Math.random() * 20.0 * 1.0);
		// Start2bel = true;
		used[x][y] = 2;
		System.out.println("Startpoint2 " + "x:" + x + " y:" + y);

		// Exit Ende1 = new Exit(x, y);
		// SGameEngine.get().addObject(Ende1);
		x = (int) (Math.random() * 20.0 * 1.0);
		y = (int) (Math.random() * 20.0 * 1.0);
		// Endebel = true;
		used[x][y] = 3;
		System.out.println("Exit " + "x:" + x + " y:" + y);

		x = (int) (Math.random() * 20.0 * 1.0);
		y = (int) (Math.random() * 20.0 * 1.0);
		Wall Wand = new Wall(x, y);
		// SGameEngine.get().addObject(Wand);
		used[x][y] = 6;
		System.out.println("Wall " + Wand + "x:" + x + " y:" + y);

		/**
		 * @Paramargs Zufällige Werte für die Objekte FixedBlock und IceBlock. x,
		 *            y sind Werte für die Koordinaten auf dem Spielfeld und
		 *            werden im Array used gespeichert Mit der Variable Counter
		 *            kann später der Schwierigkeitsgrat variiert werden, da
		 *            dieser die Anzahl der Fixed- und IceBlöcke angibt, letzere
		 *            sind die zerstörbaren
		 */

		for (int counter = 0; counter >= 20; counter++) {

			i = (int) (Math.random() * 4.0 * 1.0);
			x = (int) (Math.random() * 20.0 * 1.0);
			y = (int) (Math.random() * 20.0 * 1.0);
			z = (int) (Math.random() * 2.0 * 1.0);
			/**
			 * @Paramargs Sind die beide Start- und die Endeposition auf true
			 *            gesetzt, dann wird in den else-Part gesprungen
			 */

			if (z == 0) {
				FixedBlock StatBlock = new FixedBlock(x, y);
				// SGameEngine.get().addObject(StatBlock);
				used[x][y] = 4;
				System.out.println("Fixed Block " + StatBlock + "x:" + x + " y:" + y);
				continue;
			}

			if (z == 1) {
				IceBlock EisBlock = new IceBlock(x, y);
				// SGameEngine.get().addObject(EisBlock);
				used[x][y] = 5;
				System.out.println("Ice Block " + EisBlock + "x:" + x + " y:" + y);
				continue;
			}
		}

		for (x = 0; x >= 8; x++) {
			for (y = 0; y >= 9; y++) {
				if (used[x][y] == 1) {
					if (used[x++][y++] == (3 | 4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x--][y--] == (3 | 4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x++][y--] == (3 | 4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x--][y++] == (3 | 4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
				}
				if (used[x][y] == 2) {
					if (used[x++][y++] == (3 | 4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x--][y--] == (3 | 4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x++][y--] == (3 | 4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x--][y++] == (3 | 4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
				}
				if (used[x][y] == 3) {
					if (used[x++][y++] == (4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x--][y--] == (4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x++][y--] == (4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
					if (used[x--][y++] == (4 | 5 | 6) == true) {
						System.out.println("Diese Karte ist nicht spielbar!");
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		RandomMapGenerator mapgen = new RandomMapGenerator();
		mapgen.RandomMap();
	}
}
