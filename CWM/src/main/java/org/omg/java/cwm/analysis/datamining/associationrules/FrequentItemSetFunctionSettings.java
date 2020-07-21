package org.omg.java.cwm.analysis.datamining.associationrules;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;

import java.util.ArrayList;

/**
 * CWM Class
 * 
 * This is a subclass of MiningFunctionSettings that specifies the parameters specific to
 * frequent itemset algorithms.
 * 
 * @author Ivan Holod
 *
 */
public class FrequentItemSetFunctionSettings extends MiningFunctionSettings {

	/**
	 * This specifies the minimum support of each frequent itemset to be found.
	 */
	private double minimumSupport;
	
	/**
	 * This specifies the maximum number of items to be included in any frequent itemset to be
	 * found.
	 */
	private int maximumSetSize;
	
	/**
	 * This represents a set of items to be excluded from consideration during the execution of
	 * frequent itemset algorithm.
	 */
	private ArrayList<Category> exclusion;
}
