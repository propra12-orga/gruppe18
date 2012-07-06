/** 
 * Declaration of the "Optionen" Button at Menu
 * 
 */
package edu.propra.bomberman.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.Bomberman;

/**
 * @author Nadescha
 *
 */
public class ActionOptions extends AbstractAction {

	public ActionOptions() {
		super("Optionen", null);
		putValue(SHORT_DESCRIPTION, "Hier finden Sie weitere Optionen");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		OptionsDialog opts = new OptionsDialog(Bomberman.gameFrame);
		opts.setVisible(true);

	}
}
