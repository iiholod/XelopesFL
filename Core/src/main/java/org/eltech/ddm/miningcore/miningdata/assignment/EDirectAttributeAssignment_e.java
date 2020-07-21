package org.eltech.ddm.miningcore.miningdata.assignment;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.DirectAttributeAssignment;
import org.omg.java.cwm.objectmodel.core.Attribute;

public class EDirectAttributeAssignment_e extends DirectAttributeAssignment{
	public EDirectAttributeAssignment_e() {
		//assignmentType = AttributeAssignmentType.DirectAttributeAssignment;
	};
	
	public AttributeAssignmentType getAttributeAssignmentType(){
		return AttributeAssignmentType.DirectAttributeAssignment;
	}
	
	public void setAttribute(Attribute new_value) {
		attribute = new_value;
	}

	public Attribute getAttribute() {
		return attribute;
	}
}
