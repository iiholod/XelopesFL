package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Any;


/**
 * CWM Class
 *
 * This represents a discrete value. A collection of Category instances defines the values
 * that may or may not be annotated with a mining attribute.
 *
 * @author Ivan Holod
 *
 */

public class Category {

	/**
	 * Constraint on value: DataType must define equality operator.
	 */
	protected Any value;

	/**
	 * This optional attribute is set to true if the Category being specified is the null category. This
	 * special category value can be used to represent unknown prediction by a model. The default
	 * value is false.
	 */
	protected  boolean isNullCategory = false;

	/**
	 * The displayName is a string that may be used by applications to refer to this category.
	 */
	protected String displayName;

	/**
	 * This identifies the role of this Category instance.
	 */
	protected CategoryProperty property;

	/**
	 * This contains the prior probability associated with this Category, if any.
	 */
	protected double prior;


}