package org.omg.java.cwm.analysis.datamining.supervised;

import org.omg.java.cwm.objectmodel.core.ModelElement;
/**
 * CWM Class
 * 
 * This represents the lift result for a quantile of the input data specified in this object.
 * 
 * @author Ivan Holod
 *
 */
public class LiftAnalysisPoint extends ModelElement {

	/**
	 * The number of records for which this lift (sum of target predictions or actual target values) is
	 * specifying.
	 */
	private int subsetOfRecords;
	
	/**
	 * The lift (i.e., the sum of actual positive targets for classification or the sum of the actual
	 * values for approximation) for the specified subset of records.
	 */
	private double aggregateTarget;
}
