package org.omg.java.cwm.objectmodel.core;

/**
 * 
 * @author push_king
 */
public class Package extends Namespace implements Cloneable {
	/*
	 * {author=push_king , src_lang=Java}
	 */

	public ModelElement[] importedElement;

	public Object clone() {
		Package o = null;
		o = (Package) super.clone();

		if (importedElement != null) {
			ModelElement[] me = new ModelElement[importedElement.length];
			for (int i = 0; i < importedElement.length; i++)
				me[i] = (ModelElement) importedElement[i].clone();
			o.importedElement = me;
		}

		return o;
	}
}