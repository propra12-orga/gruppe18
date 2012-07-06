//Author Nadescha


package edu.propra.bomberman.graphicengine;



public class GraphicEngine {
	private GraphicsEngineThread	geT;
	private SGPanel					panel;

	public GraphicEngine() {
		panel = new SGPanel();
		this.geT = new GraphicsEngineThread(this);
	}

	public void addSGNode(SGNode newNode, SGNode parent) {
		if (parent == null) parent = this.getScene();
		if (parent instanceof IParent) {
			((IParent) parent).addChild(newNode);
		}
	}

	/**
	 * @return the panel
	 */
	public SGPanel getPanel() {
		return panel;
	}

	public SGScene getScene() {
		return panel.getScene();
	}

	public void releaseData() {
		this.panel.ended = true;
		this.geT.stopRunning();
		this.getScene().releaseAll();
		this.panel.ended = false;
	}

	public void removeSGNode(SGNode node) {
		SGNode parent = node.getParent();
		if (parent == null) return;
		if (parent instanceof IParent) {
			((IParent) parent).removeChild(node);
		}
	}

	/**
	 * @param panel
	 *            the panel to set
	 */
	public void setPanel(SGPanel panel) {
		this.panel = panel;
	}

	public void startDrawing() {
		if (this.panel.isVisible()) {
			Thread t = new Thread(geT);
			t.start();
		}
	}
}
