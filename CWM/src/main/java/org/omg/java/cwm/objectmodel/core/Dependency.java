package org.omg.java.cwm.objectmodel.core;

/** 
 *  
 *  @author push_king
 */
public class Dependency extends ModelElement {
  /* {author=push_king
 , src_lang=Java}*/


  public String kind;

  public ModelElement[] client;

  public ModelElement[] supplier;

}