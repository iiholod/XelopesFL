package org.omg.java.cwm.analysis.datamining.classification;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.objectmodel.core.ModelElement;

/**
 * CWM Class
 * 
 * This represents the probability of a category in the original data (i.e., before
 * performing biases sampling to enrich individual values).
 * 
 * @author Ivan Holod
 *
 */
public class PriorProbabilitiesEntry extends ModelElement {

	/**
	 * This represents the probability of the targetValue in the original data.
	 */
	private double priorProbability;
	
	/**
	 * This indicates a target value as a category, for which the probability is provided.
	 */
	private Category targetValue;
}
