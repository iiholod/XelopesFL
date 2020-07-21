package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Attribute;

/**
 * CWM Class
 *
 * This object is used when the input data is in tabular (2-D) form. The sets
 * are represented by enumerating their elements based on the selection
 * functions. For example, if the attribute selection function is isOne and
 * the value selection function is attribute, then we get:
 *
 * A B C D E F 1 0 0 1 0 0 = {A, D} 0 0 0 0 0 1 = {F} 0 0 0 0 0 0 = {} Each of
 * the input attributes (A, B, C, D, E, and F) is a selector attribute in this
 * object. It works best for a small number of members known a priori. In some
 * cases, when the potential number of values is large, but it is also known
 * that the set sizes are all small, e.g., less than 6, then we get the
 * following:
 *
 * A B C D F X Y NULL NULL NULL = {X, Y} Z NULL NULL NULL NULL = {Z} NULL NULL
 * NULL NULL NULL = {}
 *
 * In the example above, the attribute selection function is "IsNotNull" and the
 * value selection function is "value".
 *
 * Constraint: The logicalAttribute must be set valued.
 */
public class ReversePivotAttributeAssignment {

	/**
	 * This represents a non-empty set of attributes to be selected in the
	 * destination logical attribute based on the selection functions.
	 */
	protected Attribute selectorAttribute;

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
	public Attribute getSelectorAttribute() {
		return selectorAttribute;
	}

	/**
	 * @param selectorAttribute
	 *            the selectorAttribute to set
	 */
	public void setSelectorAttribute(Attribute selectorAttribute) {
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
	 *            the attributeSelectionFunction to set
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
	 *            the valueSelectionFunction to set
	 */
	public void setValueSelectionFunction(
			ValueSelectionFunction valueSelectionFunction) {
		this.valueSelectionFunction = valueSelectionFunction;
	}
}