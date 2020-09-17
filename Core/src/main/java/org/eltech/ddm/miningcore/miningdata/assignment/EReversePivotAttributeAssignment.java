package org.eltech.ddm.miningcore.miningdata.assignment;

import org.omg.java.cwm.objectmodel.core.Attribute;

import java.util.ArrayList;

public class EReversePivotAttributeAssignment extends ReversePivotAttributeAssignment_e{
	public EReversePivotAttributeAssignment() {
		selectorAttribute = new ArrayList<Attribute>();
	}

    public AttributeAssignmentType getAttributeAssignmentType(){
		return AttributeAssignmentType.ReversePivotAttributeAssignment;
	}
}