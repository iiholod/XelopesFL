package org.omg.java.cwm.analysis.datamining.attributeimportance;

import org.omg.java.cwm.analysis.datamining.supervised.SupervisedFunctionSettings;

/**
 * CWM Class
 * 
 * This is a subclass of MiningFunctionSettings that supports features unique to attribute
 * importance identification, also known as feature selection.
 * 
 * @author Ivan Holod
 *
 */
public class AttributeImportanceSettings extends SupervisedFunctionSettings {

	/**
	 * The attribute maximumResultSize indicates to return the top N most important attributes. It
	 * may return fewer if the total number of attributes is less than this number.
	 */
	private int maximumResultSize;
	
	/**
	 * If true, returns the most important attributes. If false, it returns the least important. The default
	 * value is true.
	 */
	private boolean returnTop;
}
