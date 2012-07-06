package edu.propra.bomberman.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @author Nadescha
 *Class to define Menu in strip
 */
public class StartMenu extends JMenuBar {

	public StartMenu() {
		super();
		JMenu programm = new JMenu("Programm");
		this.add(programm);

		JMenuItem starten = new JMenuItem(new ActionStarten());
		programm.add(starten);
		
		JMenuItem twoplayer = new JMenuItem(new ActionTwoPlayer());
		programm.add(twoplayer);

		JMenuItem netzwerk = new JMenuItem(new ActionNetzwerk());
		programm.add(netzwerk);


		JMenuItem beenden = new JMenuItem(new ActionBeenden());
		programm.add(beenden);
	}

}
