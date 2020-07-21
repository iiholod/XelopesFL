package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Attribute;

/**
 * CWM Class
 *
 * This object provides a mapping where the input data is in transactional
 * format. Each of the logical attributes occurring in a pivoted table is mapped
 * to three physical columns, presumably the same ones every time. If the data
 * types don't match, the value column may be different in that case
 *
 * @author Ivan Holod
 *
 */
public class PivotAttributeAssignment extends AttributeAssignment {
	/**
	 * This is a source attribute to be used to identify input records.
	 */
	protected Attribute setIdAttribute;

	/**
	 * This is a source attribute that contains the names of the items in the
	 * input data.
	 */
	protected Attribute name_attribute;

	/**
	 * This is a source attribute that contains the values of the items
	 * specified in the name attribute.
	 */
	protected Attribute value_attribute;

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
	 * @return the name_attribute
	 */
	public Attribute getNameAttribute() {
		return name_attribute;
	}

	/**
	 * @param nameAttribute
	 *            the name_attribute to set
	 */
	public void setNameAttribute(Attribute nameAttribute) {
		name_attribute = nameAttribute;
	}

	/**
	 * @return the value_attribute
	 */
	public Attribute getValueAttribute() {
		return value_attribute;
	}

	/**
	 * @param valueAttribute
	 *            the value_attribute to set
	 */
	public void setValueAttribute(Attribute valueAttribute) {
		value_attribute = valueAttribute;
	}
}