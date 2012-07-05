//Author Nadescha


package edu.propra.bomberman.graphicengine;

public class GraphicEngine {
	private SGPanel					panel;
	private GraphicsEngineThread	geT;

	public GraphicEngine() {
		panel = new SGPanel();
		this.geT = new GraphicsEngineThread(this);
	}

	public void startDrawing() {
		if (this.panel.isVisible()) {
			Thread t = new Thread(geT);
			t.start();
		}
	}

	public void addSGNode(SGNode newNode, SGNode parent) {
		if (parent == null) parent = this.getScene();
		if (parent instanceof IParent) {
			((IParent) parent).addChild(newNode);
		}
	}

	public void removeSGNode(SGNode node) {
		SGNode parent = node.getParent();
		if (parent == null) return;
		if (parent instanceof IParent) {
			((IParent) parent).removeChild(node);
		}
	}

	public SGScene getScene() {
		return panel.getScene();
	}

	/**
	 * @return the panel
	 */
	public SGPanel getPanel() {
		return panel;
	}

	/**
	 * @param panel
	 *            the panel to set
	 */
	public void setPanel(SGPanel panel) {
		this.panel = panel;
	}

	public void releaseData() {
		this.panel.ended=true;
		this.geT.stopRunning();
		this.getScene().releaseAll();
		this.panel.ended=false;
	}
}
