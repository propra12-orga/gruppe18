package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * 
 * @author Nadescha
 *Node which has a few childs
 */
public class SGGroup extends SGNode implements IParent {
	private List<SGNode>	childs;

	public SGGroup() {
		childs = Collections.synchronizedList(new ArrayList<SGNode>());
	}

	@Override
	public void addChild(Object child) {
		synchronized (childs) {
			childs.add((SGNode) child);
		}
		((SGNode) child).setParent(this);
	}

	@Override
	public void PaintRecursive(AffineTransform transform, Graphics2D g2d) {
		synchronized (childs) {
			Iterator<SGNode> i = childs.iterator(); // Must be in synchronized
													// block
			while (i.hasNext())
				i.next().PaintRecursive((AffineTransform) transform.clone(), g2d);
		}
	}

	@Override
	public void releaseAll() {
		synchronized (childs) {
			Iterator<SGNode> i = childs.iterator(); // Must be in synchronized
			while (i.hasNext()) {
				SGNode next = i.next();
				next.releaseAll();
				next.setParent(null);
			}
		}
		childs.clear();
	}

	@Override
	public void removeChild(Object child) {
		synchronized (childs) {
			this.childs.remove(child);
		}
	}

}
