package org.omg.java.cwm.analysis.datamining.miningcore.miningtask;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * This describes the output Specification for a MiningApplyTask.
 * It contains a set of attributes (represented as ApplyOutputItem objects) holding the
 * output information. These attributes can hold the score or other computed information,
 * or else be copied from input columns for reference.
 *
 * @author Ivan Holod
 *
 */
public class MiningApplyOutput {

	/**
	 * This represents a set of ApplyContentItem objects contained in this specification of apply
	 * output.
	 */
	protected  ArrayList<ApplyOutputItem> item;


}