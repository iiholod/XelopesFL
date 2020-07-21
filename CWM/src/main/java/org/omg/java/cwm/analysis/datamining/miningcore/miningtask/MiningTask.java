package org.omg.java.cwm.analysis.datamining.miningcore.miningtask;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignmentSet;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.PhysicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
import org.omg.java.cwm.objectmodel.core.ModelElement;

/**
 * CWM Class
 *
 * This is an abstract class that describes an executable data mining task operating on
 * data.
 *
 * @author Ivan Holod
 *
 */
public abstract class MiningTask extends ModelElement {

	/**
	 * A description (metadata) of the mining model used (not generated) by the task. For example,
	 * this model could be refined by the task. The usage of this model is defined by the task using
	 * it.
	 */
	protected MiningModel inputModel;

	/**
	 * A description (metadata) of the physical data used as input for the task.
	 */
	protected PhysicalData inputData;

	/**
	 * A mapping between the attributes of the inputData and those of the inputModel.
	 */
	protected AttributeAssignmentSet modelAssignment;


}