package edu.propra.bomberman.gameengine;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import edu.propra.bomberman.collisionengine.CollisionEngine;
import edu.propra.bomberman.graphicengine.GraphicEngine;
import edu.propra.bomberman.networkengine.NetworkEngine;
import edu.propra.bomberman.usercontrolengine.UserControlEngine;

public class GameEngine {
	private GraphicEngine gE;
	private CollisionEngine cE;
	private NetworkEngine nE;
	private UserControlEngine ucE;
	
	
	
	
	public GameEngine() {
		// initialize Engines
		gE=new GraphicEngine();
		cE=new CollisionEngine();
		nE=new NetworkEngine();
		ucE=new UserControlEngine();
	}
	
	public void loadMap(String filename){
		//TODO load from file
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("scene1.xml");
			doc.getFirstChild();
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
		
	}
	
	
	
	public void collisionBetween(Object a,Object b){
		
	}

}
