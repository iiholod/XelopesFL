package org.omg.java.cwm.objectmodel.core;

/** 
 *  
 *  @author push_king
 */
public abstract class StructuralFeature extends Feature {
  /* {author=push_king
 , src_lang=Java}*/


  public ChangeableKind changeability;

  public Multiplicity multiplicity;

  public OrderingKind ordering;

  public ScopeKind targetScope;

  public Classifier type;

}