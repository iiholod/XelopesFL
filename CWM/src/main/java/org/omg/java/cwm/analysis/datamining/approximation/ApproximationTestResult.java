package org.omg.java.cwm.analysis.datamining.approximation;

import org.omg.java.cwm.analysis.datamining.supervised.MiningTestResult;

/**
 * CWM Class
 * 
 * This represents the result of a test task applied to an approximation model.
 * 
 * @author Ivan Holod
 *
 */
public class ApproximationTestResult extends MiningTestResult {
	
	/**
	 * Mean of the predicted values for test data. Null if not computed
	 */
	private double meanPredictedValue;
	
	/**
	 * Mean of the actual values in the target attribute for test data. Null if not computed
	 */
	private double meanActualValue;
	
	/**
	 * Mean of the absolute values of the prediction error on the test data. Null if not computed.
	 */
	private double meanAbsoluteError;
	
	/**
	 * Root of the mean squared errors on the test data. Null if not computed.
	 */
	private double rmsError;
	
	/**
	 * The squared Pearson correlation coefficient computed on the test data. Null if not computed.
	 */
	private double rSquared;
}
