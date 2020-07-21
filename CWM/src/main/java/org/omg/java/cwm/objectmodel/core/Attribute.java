package org.omg.java.cwm.objectmodel.core;

/**
 *
 * @author push_king
 */
public class Attribute extends StructuralFeature implements Cloneable {
	/*
	 * {author=push_king , src_lang=Java}
	 */

	protected Expression initialValue;

	public Object clone() {
		Attribute o = null;
		o = (Attribute) super.clone();

		if (initialValue != null)
			o.initialValue = (Expression) initialValue.clone();

		return o;
	}

}