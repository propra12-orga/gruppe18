/**
 * Action to start a network game (Declaration of what you can do) 
 * 
 */
package edu.propra.bomberman.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.Bomberman;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.graphicengine.SGPanel;

/**
 * @author Nadescha
 * 
 */
public class ActionNetzwerk extends AbstractAction {

	public ActionNetzwerk() {
		super("Netzwerk", null);
		putValue(SHORT_DESCRIPTION, "Hier entsteht ein Netzwerk");

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Bomberman.gameFrame.setVisible(false);
		SGPanel panel = SGameEngine.get().getGraphicEngine().getPanel();
		Bomberman.gameFrame.setContentPane(panel);
		SGameEngine.get().initializeGame();
		SGameEngine.get().startTwoPlayerNetwork();
	}

}