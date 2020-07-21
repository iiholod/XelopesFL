package org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeUsageSet;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.LogicalData;
import org.omg.java.cwm.objectmodel.core.ModelElement;

/**
 * A MiningFunctionSettings object captures the high level specification input for
 * building a data mining model. The intent of mining function settings is to allow a user
 * to specify the type of the desired result without having to specify a particular
 * algorithm.
 * Although mining function settings allow for the specification of algorithm, if this is
 * omitted, the underlying data mining system is responsible for selecting the algorithm
 * based on basic user-provided parameters.
 *  
 * Subclasses throw exceptions if invalid algorithm-function pairs are supplied.
 * 
 * @author Holod Ivan
 */
public abstract class MiningFunctionSettings extends ModelElement {

	public MiningFunctionSettings(){

	}
	/**
	 * This attribute indicates the maximum execution time (in minutes) allowed for model building.
	 * If NULL, the algorithm determines for how long the model will build. This is to serve as a
	 * hint to the algorithm to adjust model building to meet time constraint. Vendor
	 * implementations map support this to varying degrees, e.g., terminate model build if exceeds
	 * this limit, intelligently adjust algorithm parameters to meet this constaints, or dynamically
	 * distribute or parallelize the operation.
	 */
	protected int desiredExecutionTimeInMinutes;

	/**
	 * The optional algorithm settings attribute provides information on the algorithm to be used as
	 * well as specific values for the parameters used by the algorithm.
	 */
	protected MiningAlgorithmSettings algorithmSettings;

	/**
	 * This specifies how each attribute as input should be treated by the algorithm. The
	 * 	LogicalAttribute referenced by AttributeUsage objects must be those in the LogicalData
	 * objects used in the same settings.
	 */
	protected AttributeUsageSet attributeUsageSet;

	/**
	 * This reference indicates the logical nature of the data to be used for model building with the
	 * function. The information provided by this attribute can be used to validate that the algorithm
	 * is correct for the function.
	 */
	protected LogicalData logicalData;

}