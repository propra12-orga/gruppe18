package edu.propra.bomberman.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class StartMenu extends JMenuBar {

	public StartMenu() {
		super();
		JMenu programm = new JMenu("Programm");
		this.add(programm);

		JMenuItem beenden = new JMenuItem(new ActionBeenden());
		programm.add(beenden);

		JMenuItem starten = new JMenuItem(new ActionStarten());
		programm.add(starten);

		JMenuItem netzwerk = new JMenuItem(new ActionNetzwerk());
		programm.add(netzwerk);

	}

}
