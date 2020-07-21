package org.omg.java.cwm.analysis.datamining.classification;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryMatrix;
import org.omg.java.cwm.analysis.datamining.supervised.MiningTestResult;

/**
 * CWM Class
 * 
 * This represents the result of a test task applied to a classification model.
 * 
 * @author Ivan Holod
 *
 */
public class ClassificationTestResult extends MiningTestResult {
	
	/**
	 * This represents the absolute number or the percentage (between 0 and 100) of correct
	 * predictions on the test data applied to a classification model.
	 */
	private double accuracy;
	
	/**
	 * This references to a matrix holding the absolute numbers of wrong predictions. A cell entry
	 * c(A,B)=n indicates that n test records had class label A in the target field, but class B was
	 * predicted by the model.
	 */
	private CategoryMatrix confusionMatrix;

}
