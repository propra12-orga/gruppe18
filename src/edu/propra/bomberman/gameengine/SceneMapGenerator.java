package edu.propra.bomberman.gameengine;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import main.Bomberman;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.propra.bomberman.gameengine.objects.Bomb;
import edu.propra.bomberman.gameengine.objects.Exit;
import edu.propra.bomberman.gameengine.objects.Explosion;
import edu.propra.bomberman.gameengine.objects.FixedBlock;
import edu.propra.bomberman.gameengine.objects.GameObject;
import edu.propra.bomberman.gameengine.objects.GameObjectGroup;
import edu.propra.bomberman.gameengine.objects.IceBlock;
import edu.propra.bomberman.gameengine.objects.Wall;

public class SceneMapGenerator {
	
	
	
	public static GameObject loadMap(String filename,GameEngine gE) {
		//System.out.println("SceneMapGenerator.loadMap()");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(Bomberman.class.getClassLoader().getResourceAsStream(filename));
			Node root = doc.getFirstChild();
			Node act = root;
			GameObjectGroup actGO = null;
			GameObjectGroup GOroot = null;
			do {
				if (act.getNodeType() == Node.ELEMENT_NODE) {
					if (act.getNodeName().equals("geGroup")) {
						if (actGO == null) {
							actGO = (GameObjectGroup) SceneMapGenerator.getNewGO(act);
							GOroot = actGO;
							gE.addObject(actGO, null, true);
						} else {
							GameObjectGroup actGO2 = (GameObjectGroup) SceneMapGenerator.getNewGO(act);
							gE.addObject(actGO2, actGO, true);
							actGO = actGO2;
						}
					} else {
						actGO.addChild(SceneMapGenerator.getNewGO(act));
						gE.addObject(SceneMapGenerator.getNewGO(act), actGO, true);
					}

				}
				if (act.hasChildNodes()) {
					act = act.getFirstChild();
					continue;
				}
				if (act.getNextSibling() != null) {
					act = act.getNextSibling();
					continue;
				} else {
					if (act.getNodeName().equals("geGroup")) actGO = (GameObjectGroup) actGO.getParent();
					act = act.getParentNode().getNextSibling();
					continue;
				}
			} while (act != root && act != null && act != doc);
			return GOroot;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	private static GameObject getNewGO(Node node) {
		if (node.getNodeName().equals("geGroup")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			GameObjectGroup group = new GameObjectGroup(x, y, "oid" + SGameEngine.get().ObjectCounter);
			return group;
		}
		if (node.getNodeName().equals("geFixedBlock")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			FixedBlock fb = new FixedBlock(x, y, "oid" + SGameEngine.get().ObjectCounter);
			return fb;
		}
		if (node.getNodeName().equals("geWall")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			Wall wall = new Wall(x, y, "oid" + SGameEngine.get().ObjectCounter);
			return wall;
		}
		if (node.getNodeName().equals("geExit")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			Exit exit = new Exit(x, y, "oid" + SGameEngine.get().ObjectCounter);
			return exit;
		}
		if (node.getNodeName().equals("geBomb")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			Bomb bomb = new Bomb(null, x, y, "oid" + SGameEngine.get().ObjectCounter);
			return bomb;
		}

		if (node.getNodeName().equals("geExplosion")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			int size = Integer.parseInt(node.getAttributes().getNamedItem("size").getNodeValue());
			Explosion explosion = new Explosion(x, y, size, "oid" + SGameEngine.get().ObjectCounter);
			return explosion;
		}

		if (node.getNodeName().equals("geIceBlock")) {
			int x = Integer.parseInt(node.getAttributes().getNamedItem("x").getNodeValue());
			int y = Integer.parseInt(node.getAttributes().getNamedItem("y").getNodeValue());
			IceBlock iceblock = new IceBlock(x, y, "oid" + SGameEngine.get().ObjectCounter, -1);
			return iceblock;
		}
		System.err.println("Unknown Type of Node when parsing XML");
		return null;
	}

}
