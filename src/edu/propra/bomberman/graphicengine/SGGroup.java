package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class SGGroup extends SGNode implements IParent {
	private ArrayList<SGNode> childs;

	public SGGroup() {
		childs = new ArrayList<SGNode>();
	}

	@Override
	public void addChild(Object child) {
		childs.add((SGNode) child);
		((SGNode) child).setParent(this);
	}

	@Override
	public void removeChild(Object child) {
		this.childs.remove(child);
	}

	@Override
	public void PaintRecursive(AffineTransform transform, Graphics2D g2d) {
		for (SGNode child : this.childs) {
			child.PaintRecursive((AffineTransform) transform.clone(), g2d);
		}
	}

}
