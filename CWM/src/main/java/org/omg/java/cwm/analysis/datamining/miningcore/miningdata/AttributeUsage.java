package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Feature;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * An AttributeUsage object specifies how a MiningAttribute is to be used for
 * mining operations.
 *
 */
public class AttributeUsage extends Feature {

	/**
	 * The usage attribute indicates if and how the MiningAttribute should be
	 * used by the model.
	 */
	protected UsageOption usage = UsageOption.active;

	/**
	 * The attribute weight indicates the weight the algorithm should assign to
	 * an attribute. The default is 1.0, indicating no effect. The particular
	 * vendor defines what effect a given weight greater or less than one has on
	 * an attribute for a particular algorithm.
	 */
	protected double weight = 1.0;

	/**
	 * This reference specifies the LogicalAttribute to which an instance of
	 * AttributeUsage refers.
	 */
	protected ArrayList<LogicalAttribute> attribute;

	/**
	 * This suppresses discretization to be performed on the attribute being
	 * specified, if true. The default is false.
	 */
	protected boolean suppressDiscretization = false;

	/**
	 * This suppresses normalization to be performed on the attribute being
	 * specified, if true. The default is false.
	 */
	protected boolean suppressNormalization = false;
}