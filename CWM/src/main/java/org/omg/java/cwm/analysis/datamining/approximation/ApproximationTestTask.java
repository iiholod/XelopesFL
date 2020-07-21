package org.omg.java.cwm.analysis.datamining.approximation;

import org.omg.java.cwm.analysis.datamining.supervised.MiningTestTask;

/**
 * CWM Class
 * 
 * This represents a task to check the quality of a regression model. A comparison of
 * mean predicted values and mean actual values can be done and a number of numerical
 * error measures can be computed. Null values mean that the model did not compute the
 * value.
 * 
 * @author Ivan Holod
 *
 */
public class ApproximationTestTask extends MiningTestTask {
	
	/**
	 * Associates the test result with the approximation test task.
	 */
	private ApproximationTestResult testResult;

}
