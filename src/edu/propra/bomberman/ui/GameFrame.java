package edu.propra.bomberman.ui;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import edu.propra.bomberman.gameengine.GameEngine;
import edu.propra.bomberman.gameengine.SGameEngine;

public class GameFrame extends JFrame {
	GameEngine	gameEngine	= SGameEngine.get();

	public GameFrame() throws HeadlessException {
		this.setTitle("Bomberman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(new StartMenu());
		this.setContentPane(new StartPanel());
		this.pack();
		this.setVisible(true);
	}

	public void gameEnd() {
		this.setVisible(false);
		this.setContentPane(new StartPanel());
		this.pack();
		this.setVisible(true);
	}

}
