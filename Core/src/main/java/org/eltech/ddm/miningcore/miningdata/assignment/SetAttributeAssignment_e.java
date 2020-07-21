package org.eltech.ddm.miningcore.miningdata.assignment;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignment;
import org.omg.java.cwm.objectmodel.core.Attribute;

public class SetAttributeAssignment_e extends AttributeAssignment{
	/**
	 * This is a source attribute to be used to identify input records.
	 */
	protected Attribute setIdAttribute;

	/**
	 * This specifies the member attribute for the attributes being described.
	 */
	protected Attribute memberAttribute;
}
