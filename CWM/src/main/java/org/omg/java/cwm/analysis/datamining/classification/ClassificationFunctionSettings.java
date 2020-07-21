package org.omg.java.cwm.analysis.datamining.classification;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryMatrix;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;

/**
 * CWM Class
 * 
 * A ClassificationFunctionSettings object is a subclass of SupervisedFunctionSettings
 * that supports features unique to the classification mining function and corresponding
 * algorithms, specifically CostMatrix. The CostMatrix must be associated with the target
 * LogicalAttribute.
 * 
 * @author Ivan Holod
 *
 */
public class ClassificationFunctionSettings extends MiningFunctionSettings {

	/**
	 * The optional CostMatrix attribute specifies a two-dimensional, NxN matrix that defines the
	 * cost associated with a prediction versus the actual value. A cost matrix is typically used in
	 * classification models, where N is the number of classes in the target, and the columns and
	 * rows are labeled with class values.
	 */
	private CategoryMatrix costMatrix;
	
	
	
}
