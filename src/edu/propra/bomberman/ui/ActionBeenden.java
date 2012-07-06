/**
 * Author Nadescha
 * 
 * Action to exit game
 */
package edu.propra.bomberman.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ActionBeenden extends AbstractAction {

	public ActionBeenden() {
		super("Beenden", null);
		putValue(SHORT_DESCRIPTION, "Hier beenden Sie das Programm");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);

	}

}
