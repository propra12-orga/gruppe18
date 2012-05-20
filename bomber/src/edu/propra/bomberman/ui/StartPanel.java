package edu.propra.bomberman.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.propra.bomberman.ui.actions.ExitAction;
import edu.propra.bomberman.ui.actions.StartAction;

public class StartPanel extends JPanel {

	public StartPanel() {
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(800,600));

		this.addStartButton();
		this.addExitButton();
	}
	

	private void addStartButton(){
		JButton start= new JButton(new StartAction());
		this.add(start, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 5, 5));
	}

	private void addExitButton() {
		JButton exit= new JButton(new ExitAction());
		this.add(exit, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 5, 5));
	}
	
}
