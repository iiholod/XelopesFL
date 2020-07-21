package org.omg.java.cwm.objectmodel.core;

/**
 * 
 * @author Nakidkin Alexey
 */
public abstract class Feature extends ModelElement implements Cloneable {
	/*
	 * {author=Nakidkin Alexey , src_lang=Java}
	 */

	public ScopeKind ownerScope;

	public Classifier owner;

	public Object clone() {
	  Feature o = null;
	  o = (Feature) super.clone();

	  if (ownerScope != null)
		  o.ownerScope = (ScopeKind)ownerScope.clone();
	  
	  if (owner != null)
		  o.owner = (Classifier)owner.clone();
	  
	  return o;
	}
	
}