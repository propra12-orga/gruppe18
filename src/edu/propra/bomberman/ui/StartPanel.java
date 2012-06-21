package edu.propra.bomberman.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StartPanel extends JPanel {

	public StartPanel() {
		super();
		
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(800,600));
		JButton spielen = new JButton(new ActionStarten());
		spielen.setPreferredSize(new Dimension(300, 30));
		JButton optionen = new JButton("Optionen");
		optionen.setPreferredSize(new Dimension(300, 30));
		JButton beenden  = new JButton(new ActionBeenden());
		beenden.setPreferredSize(new Dimension(300, 30));
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
		this.add(spielen, gbc);
		gbc.gridy=1;
		this.add(optionen, gbc);
		gbc.gridy=2;
		this.add(beenden, gbc);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}

}
