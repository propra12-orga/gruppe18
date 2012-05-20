package edu.propra.bomberman.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.propra.bomberman.Singletons.SGameFrame;


public class StartAction extends AbstractAction {

	public StartAction() {
		super("Starten");
		putValue(SHORT_DESCRIPTION, "Spiel starten");
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SGameFrame.get().startGame();

	}

}
