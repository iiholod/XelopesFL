package org.omg.java.cwm.objectmodel.core;

import java.io.Serializable;

/** 
 *  
 *  @author push_king
 */
public class VisibilityKind implements Cloneable, Serializable {
	/*
	 * {author=push_king , src_lang=Java}
	 */

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println(this.getClass().toString() + " can't be cloned");
		}

		return o;
	}

}