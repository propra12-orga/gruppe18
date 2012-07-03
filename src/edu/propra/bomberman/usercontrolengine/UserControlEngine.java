package edu.propra.bomberman.usercontrolengine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import edu.propra.bomberman.gameengine.GameEngine;

public class UserControlEngine implements KeyListener {
	private GameEngine								gameEngine;

	private HashMap<Integer, Integer>				keysDown;

	private HashMap<Integer, ArrayList<Listener>>	listener;

	public UserControlEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		keysDown = new HashMap<Integer, Integer>();
		listener = new HashMap<Integer, ArrayList<Listener>>();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		/*
		 * if(listener.get(e.getKeyCode())!=null){ for(Listener listen :
		 * listener.get(e.getKeyCode())){ listen.keyTypedEvent(e.getKeyCode());
		 * } }
		 */
		return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysDown.put(e.getKeyCode(), e.getKeyCode());
		if (listener.containsKey(e.getKeyCode())) {
			for (Listener listen : listener.get(e.getKeyCode())) {
				gameEngine.addAction(listen.keyDownEvent(keysDown, e.getKeyCode()),true);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysDown.remove(e.getKeyCode());
		if (listener.containsKey(e.getKeyCode())) {
			for (Listener listen : listener.get(e.getKeyCode())) {
				gameEngine.addAction(listen.keyUpEvent(keysDown, e.getKeyCode()),true);
			}
		}
	}

	public void addListener(int key, PlayerListener listener) {
		if (!this.listener.containsKey(key)) {
			this.listener.put(key, new ArrayList<Listener>());
		}
		this.listener.get(key).add(listener);
	}

	public void releaseData() {
		this.listener.clear();
	}

}
