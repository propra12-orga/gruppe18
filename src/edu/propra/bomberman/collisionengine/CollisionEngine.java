//Author Alex
package edu.propra.bomberman.collisionengine;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Iterator;

import edu.propra.bomberman.gameengine.GameEngine;
import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.BombGrowItem;
import edu.propra.bomberman.gameengine.objects.BombUpItem;
import edu.propra.bomberman.gameengine.objects.Exit;
import edu.propra.bomberman.gameengine.objects.Explosion;
import edu.propra.bomberman.gameengine.objects.Skull;

public class CollisionEngine {

	private GameEngine			gameEngine;
	/**
	 * Arrayliste fuer die Kollisionsobjekte
	 */
	ArrayList<CollisionObject>	objects;

	public CollisionEngine() {
		objects = new ArrayList<CollisionObject>();
	}

	/**
	 * Methode fügt ein Collisionsobjekt hinzu
	 */
	public void AddObject(CollisionObject obj) {
		objects.add(obj);
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
				if (a.getCollisionArea().getBounds().intersects(b.getCollisionArea().getBounds())) {
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
	}

	public AffineTransform checkCollisionsDirectly(CollisionObject oldObject, AffineTransform trans) {
		int dir;
		if (trans.getTranslateX() < 0.01 && trans.getTranslateX() > -0.01) {
			trans.setToTranslation(0, trans.getTranslateY());
			if (trans.getTranslateY() > 0) {
				dir = 180;//down
			} else {
				dir = 0;//up
			}
		} else {
			trans.setToTranslation(trans.getTranslateX(), 0);
			if (trans.getTranslateX() > 0) {
				dir = 90;//right
			} else {
				dir = 270;//left
			}
		}
		Area undone = (Area) oldObject.getCollisionArea().clone();
		oldObject.getCollisionArea().transform(trans);
		Area intersection = new Area();
		Area tempIntersection;
		CollisionObject partnerObj;
		Iterator<CollisionObject> it = this.objects.iterator();
		while (it.hasNext()) {
			partnerObj = it.next();
			if (partnerObj != oldObject && !(partnerObj.getPrivot() instanceof Exit) && !(partnerObj.getPrivot() instanceof Explosion) && !(partnerObj.getPrivot() instanceof BombUpItem)&& !(partnerObj.getPrivot() instanceof BombGrowItem)&& !(partnerObj.getPrivot() instanceof Skull)) {
				if (!((partnerObj.getPrivot() instanceof Bomb) && !((Bomb) partnerObj.getPrivot()).playerOut)) {
					if (partnerObj.getCollisionArea().getBounds().intersects(oldObject.getCollisionArea().getBounds())) {
						tempIntersection = (Area) partnerObj.getCollisionArea().clone();
						tempIntersection.intersect(oldObject.getCollisionArea());
						intersection.add(tempIntersection);
					}
				}
			}
		}
		//transLength must be used
		if (!intersection.isEmpty()) {
			////System.out.println("Trans before = x:" + trans.getTranslateX() + " y:" + trans.getTranslateY());
			if (dir == 90) { //right x>0 y=0			|| dir==270){//horizontal
				////System.out.print("Moving right - ");
				if (intersection.getBounds().height < oldObject.getCollisionArea().getBounds2D().getHeight() / 2) {
					//ecke  
					if (intersection.getBounds().y == oldObject.getCollisionArea().getBounds().y) {
						if (intersection.getBounds().getBounds().height > trans.getTranslateX()) {
							trans.setToTranslation(0, trans.getTranslateX());
						} else {
							trans.translate(-intersection.getBounds().height, intersection.getBounds().height);
						}
						////System.out.println("Ecke unten");
					} else {
						if (intersection.getBounds().getBounds().height > trans.getTranslateX()) {
							trans.setToTranslation(0, -trans.getTranslateX());
						} else {
							trans.translate(-intersection.getBounds().height, -intersection.getBounds().height);
						}
						////System.out.println("Ecke oben");
					}
				} else {
					trans.translate(-trans.getTranslateX(), 0);
					////System.out.println("Kante");
				}
			}
			if (dir == 270) { //left				|| dir==270){//horizontal
				////System.out.print("moving left - ");
				if (intersection.getBounds().height < oldObject.getCollisionArea().getBounds2D().getHeight() / 2) {
					//ecke
					if (intersection.getBounds().y == oldObject.getCollisionArea().getBounds().y) {
						if (intersection.getBounds().getBounds().height > -trans.getTranslateX()) {
							trans.setToTranslation(0, -trans.getTranslateX());
						} else {
							trans.translate(intersection.getBounds().height, intersection.getBounds().height);
						}
						//	//System.out.println("Ecke unten");
					} else {
						if (intersection.getBounds().getBounds().height > -trans.getTranslateX()) {
							trans.setToTranslation(0, trans.getTranslateX());
						} else {
							trans.translate(intersection.getBounds().height, -intersection.getBounds().height);
						}
						////System.out.println("Ecke oben");
					}
				} else {
					trans.translate(-trans.getTranslateX(), 0);
					////System.out.println("Kante");
				}
			}

			if (dir == 180) { //down				|| dir==270){//horizontal
				////System.out.print("moving down - ");
				if (intersection.getBounds().width < oldObject.getCollisionArea().getBounds2D().getWidth() * 4 / 5) {
					//ecke
					if (intersection.getBounds().x == oldObject.getCollisionArea().getBounds().x) {
						//rechts
						if (intersection.getBounds().getBounds().height > trans.getTranslateX()) {
							trans.setToTranslation(trans.getTranslateY(), 0);
						} else {
							trans.translate(intersection.getBounds().width, -intersection.getBounds().width);
						}

						////System.out.println("Ecke rechts");
					} else {
						if (intersection.getBounds().getBounds().height > trans.getTranslateX()) {
							trans.setToTranslation(-trans.getTranslateY(), 0);
						} else {
							trans.translate(-intersection.getBounds().width, -intersection.getBounds().width);
						}

						////System.out.println("Ecke links");
					}
				} else {
					trans.translate(0, -trans.getTranslateY());
					////System.out.println("Kante");
				}
			}

			if (dir == 0) { //up				|| dir==270){//horizontal
				//(//System.out.print("mmoving up - ");
				if (intersection.getBounds().width < oldObject.getCollisionArea().getBounds2D().getWidth() * 4 / 5) {
					//ecke
					if (intersection.getBounds().x == oldObject.getCollisionArea().getBounds().x) {
						//rechts
						if (intersection.getBounds().getBounds().height > trans.getTranslateX()) {
							trans.setToTranslation(-trans.getTranslateY(), 0);
						} else {
							trans.translate(intersection.getBounds().width, intersection.getBounds().width);
						}
						//	//System.out.println("Ecke rechts");
					} else {
						//links
						if (intersection.getBounds().getBounds().height > trans.getTranslateX()) {
							trans.setToTranslation(trans.getTranslateY(), 0);
						} else {
							trans.translate(-intersection.getBounds().width, intersection.getBounds().width);
						}
						////System.out.println("Ecke links");
					}
				} else {
					trans.translate(0, -trans.getTranslateY());
					//	//System.out.println("Kante");
				}
			}

			//if (!intersection.isEmpty()) //System.out.println("Trans after = x:" + trans.getTranslateX() + " y:" + trans.getTranslateY());
			undone.transform(trans);
			//check references
			oldObject.setCollisionArea(undone);
		}

		return trans;

	}

	public AffineTransform checkCollisionsDirectly2(CollisionObject oldObject, AffineTransform trans) {
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
			// TODO create interface for objects which aren't blocking others
			if (partnerObj != oldObject && !(partnerObj.getPrivot() instanceof Exit) && !(partnerObj.getPrivot() instanceof Explosion) && !(partnerObj.getPrivot() instanceof BombUpItem)&& !(partnerObj.getPrivot() instanceof BombGrowItem)&& !(partnerObj.getPrivot() instanceof Skull)) {
				if (!((partnerObj.getPrivot() instanceof Bomb) && !((Bomb) partnerObj.getPrivot()).playerOut)) {
					if (partnerObj.getCollisionArea().getBounds().intersects(oldObject.getCollisionArea().getBounds())) {
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
		}
		/*
		 * if (xPossible < 0 && posx) xPossible = 0; if (xPossible > 0 && !posx)
		 * xPossible = 0; if (yPossible < 0 && posy) yPossible = 0; if
		 * (yPossible > 0 && !posy) yPossible = 0;
		 */
		trans.setToTranslation(xPossible, yPossible);
		return trans;
	}

	/**
	 * Methode entfernt ein Collisionsobjekt
	 */
	public void DelObject(CollisionObject obj) {
		objects.remove(obj);
	}

	public GameEngine getGameEngine() {
		return gameEngine;
	}

	public void releaseData() {
		this.objects.clear();
	}

	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

}
