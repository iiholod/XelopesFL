package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

/**
 * CWM Class
 * 
 * An OrdinalAttributeProperties object is used to describe properties of the ordinal
 * attribute. Ordinal attributes can use the ordered constraint on the MiningCategory
 * class to use the asIs OrderType. The asIs allows the list ordering to imply a less
 * than relationship between categories N and N+1. In addition, ordinals may be cyclic
 * (e.g., days of the week).
 * 
 * @author Ivan Holod
 *
 */
public class OrdinalAttributeProperties {

	/**
	 * This indicates how the sequences of categories should be interpreted as ordinal (potentially
	 * mapped to integers).
	 */
	private OrderType orderType;
  
	/**
	 * This indicates whether the values of the attributes are cyclic (i.e., the next value of the ending
	 * value is the starting value). The default is �false.�
	 */
	private boolean isCyclic;
  

  public void set_order_type(OrderType new_value) {
  }

 

  public void set_is_cyclic(Boolean new_value) {
  }

  public void unset_is_cyclic() {
  }

}