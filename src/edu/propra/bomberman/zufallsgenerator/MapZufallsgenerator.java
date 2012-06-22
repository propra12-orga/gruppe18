package zufallsgenerator;

import edu.propra.bomberman.gameengine.objects.GameObjectGroup;

/*
 * Autor Jens Herrmann
 */

public class MapZufallsgenerator {

	int			Start1;											// wird
																	// angepasst
	int			Start2;											// wird
																	// angepasst
	int			Ende1;												// wird
																	// angepasst
	int			iceMauer;
	int			statMauer;

	boolean		Start1bel	= false;
	boolean		Start2bel	= false;
	boolean		Endebel		= false;
	boolean[]	ObjBel		= { Start1bel, Start2bel, Endebel };	// boolean,
																	// um bei
																	// erstellten
																	// Objekt
																	// auf true
																	// zu setzen
	int[]		Objekt		= { Start1, Start2, Ende1 };
	int[]		AltObjekt	= { iceMauer, statMauer };
	int			i, x, y, z;

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
	// _______________________________________________________________________________________

	public void RandomMap() {
		// Warum for nicht klappt, weiß ich nicht
		for (n = 0, n <= 80, n++) {
			
			/*
			 * Zufällige Werte für die in den drei Arrays gespeicherten Variablen. x, y sind Werte für die
			 * Koordinaten auf dem Spielfeld  	
			 */
			i = (int) (Math.random()*3.0*1.0);
			x = (int) (Math.random()*80.0*1.0);
			y = (int) (Math.random()*80.0*1.0);
			z = (int) (Math.random()*2.0*1.0);			
				/*
				 * Die Abfrage prüft, ob Start1, Start2, Ende schon belegt sind,
				 * um Mehrfachbelegung zu vermeiden
				 */
				if (ObjBel[i] == false) {
				
						if (Objekt[i] == Start1) {
								
						GameObjectGroup Start1 = new GameObjectGroup(x, y);
									
						Start1bel=true;
							
						}
						if (Objekt[i] == Start2) {
							
						GameObjectGroup Start2 = new GameObjectGroup(x, y);
										
						Start2bel=true;
						
						}
						if (Objekt[i] == Ende1) {
							
						GameObjectGroup Ende1 = new GameObjectGroup(x, y);
											
						Endebel=true;
						}
						/*
						 * Sind die beide Start- und die Endeposition auf true gesetzt,
						 * dann wird in den else-Part gesprungen
						 */
						else {
							int obj = AltObjekt[z];
							GameObjectGroup obj = new GameObjectGroup(x, y);
						}	
				}
			}
		}
}
