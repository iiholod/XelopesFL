package org.eltech.ddm.miningcore.miningdata;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.OrderType;

/**
 * CWM Class
 *
 * An OrdinalAttributeProperties object is used to describe properties of the ordinal
 * attribute. Ordinal attributes can use the �ordered� constraint on the MiningCategory
 * class to use the �asIs� OrderType. The �asIs� allows the list ordering to imply a �less
 * than� relationship between categories N and N+1. In addition, ordinals may be cyclic
 * (e.g., days of the week).
 *
 * @author Ivan Holod
 *
 */
public class EOrdinalAttributeProperties extends ECategoricalAttributeProperties{

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

	/**
	 * @return the orderType
	 */
	public OrderType getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to attributes
	 */
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the isCyclic
	 */
	public boolean isCyclic() {
		return isCyclic;
	}

	/**
	 * @param isCyclic the isCyclic to attributes
	 */
	public void setCyclic(boolean isCyclic) {
		this.isCyclic = isCyclic;
	}


}