package org.omg.java.cwm.objectmodel.core;

/** 
 *  
 *  @author push_king
 */
public class Stereotype extends ModelElement implements Cloneable {
  /* {author=push_king
 , src_lang=Java}*/


  protected Name baseClass;

  protected Constraint[] stereotypeConstraint;

  protected ModelElement[] extendedElement;

  protected TaggedValue[] requiredTag;
  
  public Stereotype(java.lang.String type) {
	  baseClass = new Name(type);
  }

  public Object clone() {
		Stereotype o = null;
		o = (Stereotype) super.clone();

		if (baseClass != null)
			o.baseClass = (Name) baseClass.clone();

		if (stereotypeConstraint != null) {
			Constraint[] c = new Constraint[stereotypeConstraint.length];
			for (int i = 0; i < stereotypeConstraint.length; i++)
				c[i] = (Constraint) stereotypeConstraint[i].clone();
			o.stereotypeConstraint = c;
		}

		if (extendedElement != null) {
			ModelElement[] e = new ModelElement[extendedElement.length];
			for (int i = 0; i < extendedElement.length; i++)
				e[i] = (ModelElement) extendedElement[i].clone();
			o.extendedElement = e;
		}

		if (requiredTag != null) {
			TaggedValue[] t = new TaggedValue[requiredTag.length];
			for (int i = 0; i < requiredTag.length; i++)
				t[i] = (TaggedValue) requiredTag[i].clone();
			o.requiredTag = t;
		}

		return o;
	}

}