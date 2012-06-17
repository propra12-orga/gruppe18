package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SGTransform extends SGNode {
	private SGNode child ;
	private AffineTransform transform;
	
	public SGTransform() {
		transform=new AffineTransform();
	}
	
	public SGTransform(SGNode parent) {
		super(parent);
		transform=new AffineTransform();
	}

	@Override
	public void PaintRecursive(AffineTransform transform, Graphics2D g2d) {
		transform.concatenate(this.transform);
		synchronized (child) {

			if(child!=null)child.PaintRecursive(transform, g2d);
			
		}
	}
	public SGNode getChild() {
		return child;
	}

	public void setChild(SGNode child) {
		this.child = child;
		this.child.setParent(this);
	}

	public AffineTransform getTransform() {
		return transform;
	}

	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}

	public void removeChild() {
		this.child.setParent(null);
		this.child=null;		
	}

}
