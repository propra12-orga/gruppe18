
package edu.propra.bomberman.graphicengine;
/**
 * 
 * @author Nadescha
 *Interface which declare that the Knot is a fatherknot and can have a child
 */
public interface IParent {
	public void addChild(Object child);

	public void removeChild(Object child);
}
