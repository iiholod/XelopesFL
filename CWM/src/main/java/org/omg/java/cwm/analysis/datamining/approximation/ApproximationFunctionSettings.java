package org.omg.java.cwm.analysis.datamining.approximation;

import org.omg.java.cwm.analysis.datamining.supervised.SupervisedFunctionSettings;

/**
 * CWM Class
 * 
 * An ApproximationFunctionSettings is a subclass of SupervisedFunctionSettings that
 * supports features that are unique to approximation function that finds approximates of
 * numerical values.
 * 
 * @author Ivan Holod
 *
 */
public class ApproximationFunctionSettings extends SupervisedFunctionSettings {
	
	/**
	 * The tolerated error is defined in terms of R-squared.
	 */
	private double toleratedError;
	
	
}
