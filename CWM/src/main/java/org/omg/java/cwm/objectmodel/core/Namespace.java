package org.omg.java.cwm.objectmodel.core;

/**
 * 
 * @author push_king
 */
public abstract class Namespace extends ModelElement implements Cloneable {
	/*
	 * {author=push_king , src_lang=Java}
	 */

	public ModelElement[] ownedElement;

	public Object clone() {
		Namespace o = null;
		o = (Namespace) super.clone();

		if (ownedElement != null) {
			ModelElement[] me = new ModelElement[ownedElement.length];
			for (int i = 0; i < ownedElement.length; i++)
				me[i] = (ModelElement) ownedElement[i].clone();
			o.ownedElement = me;
		}

		return o;
	}

}