/**
 * 
 */
package edu.propra.bomberman.graphicengine;

/**
 * @author Nadescha
 * Not in use
 * should have paint the picture every 300000ms
 */
public class GraphicsEngineThread implements Runnable {

	public GraphicEngine	ge;
	public boolean			running;

	public GraphicsEngineThread(GraphicEngine ge) {
		this.ge = ge;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		running = true;
		while (running) {
			ge.getPanel().repaint();
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void stopRunning() {
		this.running = false;
	}

}
