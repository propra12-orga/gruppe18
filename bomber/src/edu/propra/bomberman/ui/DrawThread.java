package edu.propra.bomberman.ui;

public class DrawThread implements Runnable {
	
	private Boolean running;
	private Boolean living;
	private Integer fps;
	private GamePanel gp;
	
	public DrawThread(GamePanel gp) {
		this.gp=gp;
		this.living=true;
		this.running=false;
		fps=30;
	}
	
	@Override
	public void run() {
		while(living){
			while(living && running){
				gp.redraw();
				try {
					Thread.sleep(1000/fps);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
	}

	public Boolean getRunning() {
		return running;
	}

	public void setRunning(Boolean running) {
		this.running = running;
	}

	public Boolean getLiving() {
		return living;
	}

	public void setLiving(Boolean living) {
		this.living = living;
	}

}
