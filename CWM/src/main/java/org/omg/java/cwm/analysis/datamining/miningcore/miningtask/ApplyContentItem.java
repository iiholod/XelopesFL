package org.omg.java.cwm.analysis.datamining.miningcore.miningtask;

/**
 * CWM Class
 *
 * This is an abstract class that describes an item to appear in the output based on the
 *
 *
 * @author Ivan Holod
 *
 */
public class ApplyContentItem  extends ApplyOutputItem{

	/**
	 * This indicates the rank of the prediction whose associated values (score, probability, and rule
	 * id) appear in the output as specified by the subclass. The default value is 1, which means the
	 * top prediction.
	 */
	protected int topNthIndex = 1;


}