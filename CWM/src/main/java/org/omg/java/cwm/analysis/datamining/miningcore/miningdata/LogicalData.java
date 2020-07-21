package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Class;

import java.util.ArrayList;

/**
 *  A LogicalData object contains the set of LogicalAttributes that describe the logical
 *  nature of the data used as input to data mining. The LogicalAttributes within a
 *  LogicalData object are uniquely named.
 *
 *  @author Holod Ivan
 */
public abstract class LogicalData extends Class {

	protected ArrayList<LogicalAttribute> feature;


}