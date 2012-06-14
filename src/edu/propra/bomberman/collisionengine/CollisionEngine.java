package edu.propra.bomberman.collisionengine;

import java.awt.geom.Area;
import java.util.ArrayList;

import edu.propra.bomberman.gameengine.GameEngine;

public class CollisionEngine {

	/**
	 * Arrayliste fuer die Kollisionsobjekte
	 */
	ArrayList<CollisionObject> objects;
	private GameEngine gameengine;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Methode fügt ein Collisionsobjekt hinzu
	 */
	public void AddObject(CollisionObject obj) {
		objects.add(obj);
	}

	/**
	 * Methode entfernt ein Collisionsobjekt
	 */
	public void DelObject(CollisionObject obj) {
		objects.remove(obj);
	}

	public void CheckCollision() {

		/**
		 * Prüft ob Kollision stattgefunden hat Vergleich findet zwischen Object
		 * i und Object j statt
		 */

		CollisionObject a, b;
		Area temp;
		/**
		 * Temporäre Area um den Schnitt zwischen den Objekten zu vergleichen
		 */
		for (int i = 0; i < objects.size(); i++) {
			a = objects.get(i);
			for (int j = 0; j < objects.size(); j++) {
				b = objects.get(j);

				/**
				 * vergleiche a und b indem a geclont wird
				 */
				temp = (Area) a.getCollisionArea().clone();
				temp.intersect(b.getCollisionArea());
				if (!temp.isEmpty()) {
					/**
					 * Teilt der Gameengine mit ob objekte kollidieren
					 */
					gameengine.collisionBetween(a.getPrivot(), b.getPrivot());
				}
			}
		}
	}

}
