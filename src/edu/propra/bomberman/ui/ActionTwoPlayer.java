/**
 * 
 */
package edu.propra.bomberman.ui;
/**
 * Action to start the twoplayer
 */
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.Bomberman;
import edu.propra.bomberman.graphicengine.SGPanel;

/**
 * @author Nadescha
 * 
 */
public class ActionTwoPlayer extends AbstractAction {
	public ActionTwoPlayer() {
		super("Twoplayer starten", null);
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
		Bomberman.gameFrame.gameEngine.startTwoPlayer();

	}

}
