package org.eltech.ddm.miningcore.miningdata.assignment;

import org.eltech.ddm.miningcore.miningdata.EAttributeAssignmentSet;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.PhysicalAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignment;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeSelectionFunction;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.ValueSelectionFunction;
import org.omg.java.cwm.objectmodel.core.Attribute;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class AssignmentManager {
	
	private final AttributeAssignmentType attributeAssignmentType;
	private final EAttributeAssignmentSet attributeAssignmentSet;
	private AttributeAssignment attributeAssignment;
	
//	private EDirectAttributeAssignment_e directAttributeAssignment;
//	private ESetAttributeAssignment setAttributeAssignment;
//	private EPivotAttributeAssignment pivotAttributeAssignment;
//	private EReversePivotAttributeAssignment reverseAttributeAssignment;
	
	public AssignmentManager(AttributeAssignmentType assignmentType){
		attributeAssignmentSet = new EAttributeAssignmentSet();
		attributeAssignmentType = assignmentType;
		switch (attributeAssignmentType) {
		case DirectAttributeAssignment:
			//directAttributeAssignment = new EDirectAttributeAssignment_e();
			attributeAssignment = new EDirectAttributeAssignment_e();
			break;
		case SetAttributeAssignment:
			//setAttributeAssignment = new ESetAttributeAssignment();
			attributeAssignment = new ESetAttributeAssignment();
			break;
		case PivotAttributeAssignment:
			//pivotAttributeAssignment = new EPivotAttributeAssignment();
			attributeAssignment = new EPivotAttributeAssignment();
			break;
		case ReversePivotAttributeAssignment:
			//reverseAttributeAssignment = new EReversePivotAttributeAssignment();
			attributeAssignment = new EReversePivotAttributeAssignment();	
		default:
			break;
		}
	}
	
	public EAttributeAssignmentSet getAttributeAssignmentSet() {
		return attributeAssignmentSet;
	}
	
	public AttributeAssignmentType getAttributeAssignmentType(){
		return attributeAssignmentType;
	}
	
	//direct
	public void setAttributeSettings(Map<PhysicalAttribute, ELogicalAttribute> attributes){
		if(attributeAssignmentType == AttributeAssignmentType.DirectAttributeAssignment){
			attributeAssignmentSet.removeAllAssignments();
			for( Entry<PhysicalAttribute, ELogicalAttribute> entry : attributes.entrySet() ){			
				EDirectAttributeAssignment_e directAttributeAssignment = new EDirectAttributeAssignment_e();
				directAttributeAssignment.setAttribute(entry.getKey());
				directAttributeAssignment.addLogicalAttribute(entry.getValue());
				attributeAssignmentSet.setAssignment(directAttributeAssignment);
			}
		}
	}
	
	//reverse pivot
	public void setAttributeSettings(ArrayList<PhysicalAttribute> selectorAttributes,
			AttributeSelectionFunction attributeSelectionFunction,
			ValueSelectionFunction valueSelectionFunction){
		if(attributeAssignmentType == AttributeAssignmentType.ReversePivotAttributeAssignment){
			attributeAssignmentSet.removeAllAssignments();
			ArrayList<Attribute> selectorAttributeWithoutPhysical = new ArrayList<Attribute>();
			for (PhysicalAttribute physicalAttribute : selectorAttributes) {
				selectorAttributeWithoutPhysical.add(physicalAttribute);
			}
			((EReversePivotAttributeAssignment)attributeAssignment).setSelectorAttribute(selectorAttributeWithoutPhysical);
			((EReversePivotAttributeAssignment)attributeAssignment).setValueSelectionFunction(valueSelectionFunction);
			((EReversePivotAttributeAssignment)attributeAssignment).setAttributeSelectionFunction(attributeSelectionFunction);
			attributeAssignmentSet.setAssignment(attributeAssignment);
		}
	}
	
	//pivot
//	public void setAttributesSettings(ArrayList<PhysicalAttribute> selectorAttributes,
//			AttributeSelectionFunction attributeSelectionFunction,
//			ValueSelectionFunction valueSelectionFunction){
//			if(attributeAssignmentType == AttributeAssignmentType.ReversePivotAttributeAssignment){
//				attributeAssignmentSet.removeAllAssignments();
//				ArrayList<Attribute> selectorAttributeWithoutPhysical = new ArrayList<Attribute>();
//				for (PhysicalAttribute physicalAttribute : selectorAttributes) {
//					selectorAttributeWithoutPhysical.add((Attribute)physicalAttribute);
//				}
//				((EReversePivotAttributeAssignment)attributeAssignment).setSelectorAttribute(selectorAttributeWithoutPhysical);
//				((EReversePivotAttributeAssignment)attributeAssignment).setValueSelectionFunction(valueSelectionFunction);
//				((EReversePivotAttributeAssignment)attributeAssignment).setAttributeSelectionFunction(attributeSelectionFunction);
//				attributeAssignmentSet.setAssignment(attributeAssignment);
//			}
//		}
	
	
	// Set
	public void setAttributeSettings(ArrayList<SetSetting> userSets){
		if(attributeAssignmentType == AttributeAssignmentType.SetAttributeAssignment){
			attributeAssignmentSet.removeAllAssignments();
			for (SetSetting setSetting : userSets) {
				ESetAttributeAssignment setAttributeAssignment = new ESetAttributeAssignment();
				setAttributeAssignment.addLogicalAttribute(setSetting.logicalAttribute);
				ArrayList<Attribute> memberAttribute = new ArrayList<Attribute>();
				for (PhysicalAttribute physicalAttribute : setSetting.e_physicalData) {
					memberAttribute.add(physicalAttribute);
				}
				setAttributeAssignment.setMemberAttribute(memberAttribute);
				attributeAssignmentSet.setAssignment(setAttributeAssignment);
			}
		}
	}
	
	
}
