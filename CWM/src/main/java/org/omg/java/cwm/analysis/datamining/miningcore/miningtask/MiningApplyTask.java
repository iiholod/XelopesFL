package org.omg.java.cwm.analysis.datamining.miningcore.miningtask;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignmentSet;

/**
 * CWM Class
 *
 * This describes a task that computes the result of an application of a data mining model
 * to (new) data.
 *
 * @author Ivan Holod
 *
 */
public class MiningApplyTask extends MiningTask{

	/**
	 * This contains the specification of the apply output as the result of MiningApplyTask.
	 */
	protected MiningApplyOutput applyOutput;

	/**
	 * This maps the apply output items to the destination attributes.
	 */
	protected AttributeAssignmentSet outputAssignment;

	/**
	 * This specifies how the apply output is created. The default is createNew, which means the
	 * output is created as a new file/table.
	 */
	protected ApplyOutputOption outputOption;

}