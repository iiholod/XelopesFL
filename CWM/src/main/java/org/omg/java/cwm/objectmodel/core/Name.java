package org.omg.java.cwm.objectmodel.core;

import java.io.Serializable;

/**
 * Name defines a token which is used for naming ModelElements and similar usages.
 * Each Name has a corresponding String representation. For purposes of exchange a
 * name should be represented as a String.
 *
 *
 *
 * The default for the Name data type is an empty string.
 */
public class Name implements Cloneable, Serializable {

	   private java.lang.String string;
	
	   /**
	    * Empty constructor.
	    */
	   public Name()
	   {
		   string = "";
	   }
	
	   public Name(java.lang.String name) {
		   string = name;
	   }

	   public java.lang.String getString() {
	     return string;
	   }

	   public void setString(java.lang.String string) {
	     this.string = string;
	   }

	   public java.lang.String toString() {
	     return string.toString();
	   }
	   
	   public Object clone() {
			Object o = null;
			try {
				o = super.clone();
			} catch (CloneNotSupportedException e) {
				System.err.println(this.getClass().toString() + " can't be cloned");
			}

			return o;
		}
	   
	   @Override
	public int hashCode() {
		return string.hashCode();
	}
	   

}