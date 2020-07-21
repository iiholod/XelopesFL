package org.eltech.ddm.miningcore.miningdata.assignment;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.PivotAttributeAssignment;

public class EPivotAttributeAssignment extends PivotAttributeAssignment{
	public EPivotAttributeAssignment(){		
	};
	
	public AttributeAssignmentType getAttributeAssignmentType(){
		return AttributeAssignmentType.PivotAttributeAssignment;
	}
}
