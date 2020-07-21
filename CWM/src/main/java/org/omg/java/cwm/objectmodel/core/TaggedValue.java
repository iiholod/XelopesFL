package org.omg.java.cwm.objectmodel.core;

import java.io.Serializable;

/**
 * 
 * @author push_king
 */
public class TaggedValue extends Element implements Cloneable, Serializable {
	/*
	 * {author=push_king , src_lang=Java}
	 */

	protected Name tag;

	protected String value;

	protected Stereotype stereotype;
	
	protected ModelElement modelElement;

	public Object clone() {
		TaggedValue o = null;
		o = (TaggedValue) super.clone();

		if (tag != null)
			o.tag = (Name)tag.clone();
		
		if (stereotype != null)
			o.stereotype = (Stereotype)stereotype.clone();
		
		return o;
	}

}