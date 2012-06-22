package edu.propra.bomberman.collisionengine;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Iterator;

import edu.propra.bomberman.gameengine.GameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.BombUpItem;
import edu.propra.bomberman.gameengine.objects.Exit;
import edu.propra.bomberman.gameengine.objects.Explosion;

public class CollisionEngine {

	/**
	 * Arrayliste fuer die Kollisionsobjekte
	 */
	ArrayList<CollisionObject>	objects;
	private GameEngine			gameEngine;

	public CollisionEngine() {
		objects = new ArrayList<CollisionObject>();
	}

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
			for (int j = i + 1; j < objects.size(); j++) {
				b = objects.get(j);
				if (a == b) continue;
				/**
				 * vergleiche a und b indem a geclont wird
				 */
				temp = (Area) a.getCollisionArea().clone();
				temp.intersect(b.getCollisionArea());
				if (!temp.isEmpty()) {
					/**
					 * Teilt der Gameengine mit ob objekte kollidieren
					 */

					getGameEngine().collisionBetween(a.getPrivot(), b.getPrivot());
				}
			}
		}
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	public AffineTransform checkCollisionsDirectly(CollisionObject oldObject, AffineTransform trans) {
		double xPossible = trans.getTranslateX();
		double yPossible = trans.getTranslateY();
		double dist = Math.sqrt(Math.pow(xPossible, 2) + Math.pow(yPossible, 2));

		boolean posx = true;
		boolean posy = true;
		if (xPossible < 0) {
			posx = false;
		} else if (xPossible > 0) {
			posx = true;
		}
		if (yPossible < 0) {
			posy = false;
		} else if (yPossible > 0) {
			posy = true;
		}

		oldObject.getCollisionArea().transform(trans);

		// TODO find way to optimize move when colliding with corner

		Area intersection;
		CollisionObject partnerObj;
		Iterator<CollisionObject> it = this.objects.iterator();
		while (it.hasNext()) {
			partnerObj = it.next();
			//TODO create interface for objects which aren't blocking others
			if (partnerObj != oldObject && !(partnerObj.getPrivot() instanceof Exit) && !(partnerObj.getPrivot() instanceof Explosion) && !(partnerObj.getPrivot() instanceof BombUpItem)) {
				if (!((partnerObj.getPrivot() instanceof Bomb) && !((Bomb) partnerObj.getPrivot()).playerOut)) {
					intersection = (Area) partnerObj.getCollisionArea().clone();
					intersection.intersect(oldObject.getCollisionArea());
					if (!intersection.isEmpty()) {
						AffineTransform undo = new AffineTransform();
						Rectangle iR = intersection.getBounds();
						if (iR.width < iR.height) {
							if (xPossible < 0) {
								xPossible += iR.width;
								undo.translate(iR.width, 0);
							} else if (xPossible > 0) {
								xPossible -= iR.width;
								undo.translate(-iR.width, 0);
							}
						} else if (iR.width > iR.height) {
							if (yPossible < 0) {
								yPossible += iR.height;
								undo.translate(0, iR.height);
							} else if (yPossible > 0) {
								yPossible -= iR.height;
								undo.translate(0, -iR.height);
							}
						}
						oldObject.getCollisionArea().transform(undo);
					}
				}
			}
		}
		/*
		 * if (xPossible < 0 && posx) xPossible = 0; if (xPossible > 0 && !posx)
		 * xPossible = 0; if (yPossible < 0 && posy) yPossible = 0; if
		 * (yPossible > 0 && !posy) yPossible = 0;
		 */
		trans.setToTranslation(xPossible, yPossible);
		return trans;
	}

	public void releaseData() {
		this.objects.clear();
	}

}
