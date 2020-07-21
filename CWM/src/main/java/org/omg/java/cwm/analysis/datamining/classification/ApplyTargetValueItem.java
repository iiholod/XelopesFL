package org.omg.java.cwm.analysis.datamining.classification;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.analysis.datamining.miningcore.miningtask.ApplyOutputItem;

/**
 * CWM Class
 * 
 * This indicates that the probability value of the given target value is to appear in the
 * output.
 * 
 * @author Ivan Holod
 *
 */
public class ApplyTargetValueItem extends ApplyOutputItem {

	/**
	 * Associates the target value whose probability value is to appear in the apply output with
	 * ApplyTargetValueItem.
	 */
	private Category targetValue;
}
