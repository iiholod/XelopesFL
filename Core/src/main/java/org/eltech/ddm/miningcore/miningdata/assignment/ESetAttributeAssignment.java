package org.eltech.ddm.miningcore.miningdata.assignment;

import org.omg.java.cwm.objectmodel.core.Attribute;

import java.util.ArrayList;

public class ESetAttributeAssignment extends SetAttributeAssignment_e{

	ArrayList<Attribute> memberAttribute;
	
	public ESetAttributeAssignment() {
		memberAttribute = new ArrayList<Attribute>();
	};
	
	public AttributeAssignmentType getAttributeAssignmentType(){
		return AttributeAssignmentType.SetAttributeAssignment;
	}
	
	/**
	 * @return the setIdAttribute
	 */
	public Attribute getSetIdAttribute() {
		return setIdAttribute;
	}

	/**
	 * @param setIdAttribute
	 *            the setIdAttribute to attributes
	 */
	public void setSetIdAttribute(Attribute setIdAttribute) {
		this.setIdAttribute = setIdAttribute;
	}

	/**
	 * @return the memberAttribute
	 */
//	public Attribute getMemberAttribute() {
//		return memberAttribute;
//	}
	public ArrayList<Attribute> getMemberAttribute() {
		return memberAttribute;
	}

	/**
	 * @param memberAttribute
	 *            the memberAttribute to attributes
	 */
//	public void setMemberAttribute(Attribute memberAttribute) {
//		this.memberAttribute = memberAttribute;
//	}
	public void setMemberAttribute(ArrayList<Attribute> memberAttribute) {
		this.memberAttribute = memberAttribute;
	}
	
}