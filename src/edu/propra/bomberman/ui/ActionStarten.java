/**
 * 
 */
package edu.propra.bomberman.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.propra.bomberman.graphicengine.SGPanel;

import main.Bomberman;

/**
 * @author Nadescha
 * 
 */
public class ActionStarten extends AbstractAction {

	public ActionStarten() {
		super("OnePlayer starten", null);
		putValue(SHORT_DESCRIPTION, "Hier starten Sie das Programm");

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Bomberman.gameFrame.setVisible(false);
		SGPanel panel = Bomberman.gameFrame.gameEngine.getGraphicEngine().getPanel();
		Bomberman.gameFrame.setContentPane(panel);
		Bomberman.gameFrame.gameEngine.initializeGame();
		Bomberman.gameFrame.addKeyListener(Bomberman.gameFrame.gameEngine.getUserControlEngine());
		Bomberman.gameFrame.invalidate();
		Bomberman.gameFrame.pack();
		Bomberman.gameFrame.setVisible(true);
		Bomberman.gameFrame.gameEngine.startOnePlayer();

	}

}
