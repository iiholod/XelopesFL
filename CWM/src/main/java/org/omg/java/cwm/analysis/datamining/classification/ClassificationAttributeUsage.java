package org.omg.java.cwm.analysis.datamining.classification;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeUsage;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;

import java.util.ArrayList;

/**
 * CWM Class
 * 
 * As a subclass of AttributeUsage, ClassificationAttributeUsage provides additional
 * specification for categorical attributes only, in particular, a set of the positive attributes.
 * 
 * @author Ivan Holod
 *
 */
public class ClassificationAttributeUsage extends AttributeUsage {

	/**
	 * This represents a list of categories to be treated as positive values for model building or
	 * usage.
	 */
	private ArrayList<Category> positiveCategory;
	
	/**
	 * This represents the prior probabilities for the target values.
	 */
	private PriorProbabilities priors;
}
