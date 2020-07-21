package org.eltech.ddm.miningcore.miningdata.assignment;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignment;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeSelectionFunction;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.ValueSelectionFunction;
import org.omg.java.cwm.objectmodel.core.Attribute;

import java.util.ArrayList;

public class ReversePivotAttributeAssignment_e extends AttributeAssignment {
	/**
	 * This represents a non-empty attributes of attributes to be selected in the
	 * destination logical attribute based on the selection functions.
	 */
	//protected Attribute selectorAttribute;
	ArrayList<Attribute> selectorAttribute;
	
	/**
	 * This describes how the selector attributes are selected based on their
	 * values
	 */
	protected AttributeSelectionFunction attributeSelectionFunction;

	/**
	 * This describes whether the value or the name of a selector attribute to
	 * appear in the destination logical attribute when the selector attribute
	 * satisfies the specified AttributeSelectionFunction.
	 */
	protected ValueSelectionFunction valueSelectionFunction;

	/**
	 * @return the selectorAttribute
	 */
//	public Attribute getSelectorAttribute() {
//		return selectorAttribute;
//	}
	public ArrayList<Attribute> getSelectorAttribute() {
		return selectorAttribute;
	}

	/**
	 * @param selectorAttribute
	 *            the selectorAttribute to attributes
	 */
//	public void setSelectorAttribute(Attribute selectorAttribute) {
//		this.selectorAttribute = selectorAttribute;
//	}
	public void setSelectorAttribute(ArrayList<Attribute> selectorAttribute) {
		this.selectorAttribute = selectorAttribute;
	}

	/**
	 * @return the attributeSelectionFunction
	 */
	public AttributeSelectionFunction getAttributeSelectionFunction() {
		return attributeSelectionFunction;
	}

	/**
	 * @param attributeSelectionFunction
	 *            the attributeSelectionFunction to attributes
	 */
	public void setAttributeSelectionFunction(
			AttributeSelectionFunction attributeSelectionFunction) {
		this.attributeSelectionFunction = attributeSelectionFunction;
	}

	/**
	 * @return the valueSelectionFunction
	 */
	public ValueSelectionFunction getValueSelectionFunction() {
		return valueSelectionFunction;
	}

	/**
	 * @param valueSelectionFunction
	 *            the valueSelectionFunction to attributes
	 */
	public void setValueSelectionFunction(
			ValueSelectionFunction valueSelectionFunction) {
		this.valueSelectionFunction = valueSelectionFunction;
	}
}
