package org.omg.java.cwm.analysis.datamining.classification;

import org.omg.java.cwm.objectmodel.core.ModelElement;

import java.util.ArrayList;

/**
 * CWM Class
 * 
 * This represents a set of prior probabilities of the categories in a mining attribute.
 * Mostly applies to a target LogicalAttribute used for classification.
 * 
 * The sum of the probabilities in all priorsEntries must not exceed 1.
 * 
 * @author Ivan Holod
 *
 */
public class PriorProbabilities extends ModelElement {

	/**
	 * This represents a set of priors for the target values.
	 */
	private ArrayList<PriorProbabilitiesEntry> prior;
}
