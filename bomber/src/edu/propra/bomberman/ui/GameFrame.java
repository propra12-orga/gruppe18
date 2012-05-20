package edu.propra.bomberman.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.propra.bomberman.Singletons.SMap;
import edu.propra.bomberman.data.Map;

public class GameFrame extends JFrame {
	private StartPanel sp;
	private GamePanel gp;
	private Map map;
	public GameFrame() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension( 800, 600));
		this.startScreen();
		this.pack();
		this.setVisible(true);
	}

	public void startScreen() {
		if(sp==null)this.sp=new StartPanel();
		this.setContentPane(sp);
		this.pack();
	}

	public void startGame() {
		if(gp==null)this.gp=new GamePanel();
		this.setContentPane(gp);
		this.map=SMap.get();
		this.pack();
		this.gp.start();
		this.gp.pause(false);
	}
}
