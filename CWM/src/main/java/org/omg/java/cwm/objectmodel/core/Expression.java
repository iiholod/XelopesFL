package org.omg.java.cwm.objectmodel.core;

/** 
 *  
 *  @author push_king
 */
public class Expression extends Element implements Cloneable {
  /* {author=push_king
 , src_lang=Java}*/


  public String body;

  public Name language;
  
  public Object clone() {
		Expression o = null;
		o = (Expression) super.clone();

		if (language != null)
			o.language = (Name) language.clone();
		
		return o;
	}

}