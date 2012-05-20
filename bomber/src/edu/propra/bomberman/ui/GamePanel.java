package edu.propra.bomberman.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;

import javax.swing.JPanel;

import edu.propra.bomberman.Singletons.SMap;
import edu.propra.bomberman.data.GameObject;

public class GamePanel extends JPanel {
	private  DrawThread dt;
	
	public GamePanel() {
		super();
		this.setPreferredSize(new Dimension(800,600));
		
	}
	
	public void start(){
		this.dt=new DrawThread(this);
		new Thread(this.dt).start();
	}
	
	public void pause(Boolean doPause){
		this.dt.setRunning(!doPause);
	}
	
	public void stop(){
		this.dt.setLiving(false);	
	}
	
	public void redraw() {
		Graphics g = this.getGraphics();
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {	
		//super.paintComponent(g);
		Rectangle viewport=new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		g.setColor(new Color(0,0,0));
		g.fillRect(viewport.x,viewport.y,viewport.width,viewport.height);
		Iterator<GameObject> it= SMap.get().getObjects();
		GameObject next;
		while(it.hasNext()){
			next=it.next();
			if(next instanceof IDrawable && ((IDrawable) next).intersectsDraw(viewport)){
				((IDrawable)next).draw(g);
			}
		}
	}
	
}
