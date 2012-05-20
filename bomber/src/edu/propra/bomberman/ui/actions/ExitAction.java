package edu.propra.bomberman.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.propra.bomberman.Singletons.SGameFrame;


public class ExitAction extends AbstractAction {

	
	public ExitAction() {
		super("Beenden");
		putValue(SHORT_DESCRIPTION, "Spiel beenden");}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SGameFrame.get().dispose();
	}

}
