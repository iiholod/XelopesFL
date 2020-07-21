package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Attribute;

/**
 * CWM Class
 *
 * Constraint: The logicalAttribute must be set valued. This object provides a
 * mapping between a set-valued logical attribute and a set of attributes in the
 * physical data. setIdAttribute is the set identifier of the set being mapped,
 * and memberAttribute represents a set of attributes being mapped to the
 * setvalued logical attribute.
 *
 * @author Ivan Holod
 *
 */
public class SetAttributeAssignment {
	/**
	 * This is a source attribute to be used to identify input records.
	 */
	protected Attribute setIdAttribute;

	/**
	 * This specifies the member attribute for the set being described.
	 */
	protected Attribute memberAttribute;

	/**
	 * @return the setIdAttribute
	 */
	public Attribute getSetIdAttribute() {
		return setIdAttribute;
	}

	/**
	 * @param setIdAttribute
	 *            the setIdAttribute to set
	 */
	public void setSetIdAttribute(Attribute setIdAttribute) {
		this.setIdAttribute = setIdAttribute;
	}

	/**
	 * @return the memberAttribute
	 */
	public Attribute getMemberAttribute() {
		return memberAttribute;
	}

	/**
	 * @param memberAttribute
	 *            the memberAttribute to set
	 */
	public void setMemberAttribute(Attribute memberAttribute) {
		this.memberAttribute = memberAttribute;
	}

}