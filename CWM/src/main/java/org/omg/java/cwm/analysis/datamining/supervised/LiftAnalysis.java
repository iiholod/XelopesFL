package org.omg.java.cwm.analysis.datamining.supervised;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.objectmodel.core.ModelElement;

/**
 * CWM Class
 * 
 * This represents the result of lift computation applied to a supervised model.
 * 
 * @author Ivan Holod
 *
 */
public class LiftAnalysis extends ModelElement {
	
	/**
	 * This represents the name of the target attribute.
	 */
	private String targetAttributeName;
	
	/**
	 * This represents a set of quantiles for which the lift values are computed.
	 */
	private LiftAnalysisPoint point;
	
	/**
	 * This represents a set of positive target values for which this lift result is computed.
	 */
	private Category positiveTargetCategory;

}
