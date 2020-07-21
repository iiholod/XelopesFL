package org.omg.java.cwm.objectmodel.core;

/** 
 *  
 *  @author push_king
 */
public class Constraint extends ModelElement implements Cloneable {
	/*
	 * {author=push_king , src_lang=Java}
	 */

	public BooleanExpression body;

	public ModelElement[] constrainedElement;

	public Object clone() {
		Constraint o = null;
		o = (Constraint) super.clone();

		if (body != null)
			o.body = (BooleanExpression) body.clone();

		if (constrainedElement != null) {
			ModelElement[] me = new ModelElement[constrainedElement.length];
			for (int i = 0; i < constrainedElement.length; i++)
				me[i] = (ModelElement) constrainedElement[i].clone();
			o.constrainedElement = me;
		}

		return o;
	}

}