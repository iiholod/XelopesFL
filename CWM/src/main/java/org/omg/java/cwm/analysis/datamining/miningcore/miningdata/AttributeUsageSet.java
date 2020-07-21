package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import java.util.ArrayList;

/**
 *  An AttributeUsageSet object contains a collection of AttributeUsage objects. This
 *  specifies how MiningAttributes are to be used or manipulated by a model. The
 *  specification may contain at most one AttributeUsage object of each MiningAttribute
 *  in the LogicalDataSpecification. The default usage is active for an attribute if no
 *  entry for a MiningAttribute is present.An AttributeUsageSet object contains a collection of AttributeUsage objects. This
 *  specifies how MiningAttributes are to be used or manipulated by a model. The
 *  specification may contain at most one AttributeUsage object of each MiningAttribute
 *  in the LogicalDataSpecification. The default usage is active for an attribute if no
 *  entry for a MiningAttribute is present.
 *
 *  @author push_king
 */
public class AttributeUsageSet extends org.omg.java.cwm.objectmodel.core.Class {

	protected ArrayList<AttributeUsage> feature;




}