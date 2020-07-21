package org.omg.java.cwm.analysis.datamining.associationrules;

/**
 * CWM Class
 * 
 * A SequenceFunctionSettings is a subclass of FrequentItemSetFunctionSettings that
 * supports features that are unique to sequence algorithms.
 * 
 * @author Ivan Holod
 *
 */
public class SequenceFunctionSettings extends FrequentItemSetFunctionSettings {

	/**
	 * This specifies the size of the window to be considered when executing sequence algorithm in
	 * terms of the number of items.
	 */
	private int windowSize;
}
