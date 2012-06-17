package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class SGGroup extends SGNode {
	private ArrayList<SGNode> childs;
	public SGGroup() {
		childs=new ArrayList<SGNode>();
	}

	public SGGroup(SGNode parent) {
		super(parent);
		childs=new ArrayList<SGNode>();
	}
	public void addChild(SGNode child){
		childs.add(child);
		child.setParent(this);
	}
	public void removeChild(SGNode child){
		this.childs.remove(child);
	}
	@Override
	public void PaintRecursive(AffineTransform transform, Graphics2D g2d) {
		for (SGNode child : this.childs) {
			child.PaintRecursive((AffineTransform)transform.clone(), g2d);
		}
	}

}
