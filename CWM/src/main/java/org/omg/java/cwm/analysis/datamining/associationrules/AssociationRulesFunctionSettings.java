package org.omg.java.cwm.analysis.datamining.associationrules;

/**
 * CWM Class
 * 
 * An AssociationRulesFunctionSettings is a subclass of
 * FrequentItemSetFunctionSettings that supports features that are unique to association
 * rules algorithms.
 * 
 * @author Ivan Holod
 *
 */
public class AssociationRulesFunctionSettings extends
		FrequentItemSetFunctionSettings {
	
	/**
	 * This specifies the minimum confidence value for each association rule to be found.
	 */
	private double minimumConfidence;
	
	/**
	 * This is the maximum length of the antecedent and consequent item set sizes.
	 */
	private int  maximumRuleLength;

}
