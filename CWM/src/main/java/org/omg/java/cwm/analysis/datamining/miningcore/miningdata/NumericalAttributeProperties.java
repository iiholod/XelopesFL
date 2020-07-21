package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

/**
 * CWM Class
 *
 * A NumericalAttributeProperties object is used to describe properties of the numerical
 * attribute.
 * This metadata may or may not be used by the underlying algorithm. It may be
 * leveraged to determine if data being supplied as input to a mining operation is
 * sufficiently similar to the data used to build the model.
 *
 * @author Ivan Holod
 *
 */
public class NumericalAttributeProperties {

  /**
   * This indicates the lower bound (the smallest) of the values in the attribute
   */
	protected double lowerBound;

	/**
	 * This indicates the upper bound (the largest) of the values in the attribute
	 */
	protected double upperBound;

	/**
	 * This indicates whether the values are discrete. The default is false.
	 */
	protected Boolean isDiscrete;

	/**
	 * This indicates whether the values of the attributes are cyclic (i.e., the next value of the ending
	 * value is the starting value). The default is false.
	 */
	protected Boolean isCyclic;

	/**
	 * This specifies the value of the anchor
	 */
	protected Double anchor;

	/**
	 * This specifies the starting value of the cycle.
	 */
	protected Double cycleBegin;

	/**
	 * This specifies the ending value of the cycle.
	 */
	protected Double cycleEnd;

	/**
	 * This specifies the interval value between two adjacent discrete values when the attribute is
	 * discrete.
	 */
	protected Double discreteStepSize;

}