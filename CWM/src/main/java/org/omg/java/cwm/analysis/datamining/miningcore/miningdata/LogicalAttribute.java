package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;


/**
 * CWM Class
 *
 * A LogicalAttribute object is a logical concept that describes a domain of data to be
 * used as input to data mining operations. Logical mining attributes are typically either
 * categorical, ordinal, or numerical. As such, a mining attribute references additional
 * metadata that characterizes the attribute as either catagorical (e.g., a list of the
 * categories) or numerical (e.g., the bounds of the data).
 *
 * @author Ivan Holod
 *
 */
public class LogicalAttribute extends MiningAttribute{
	/**
	 * This indicates that the values of the attribute being specified here are sets, if true. The default
	 * is false.
	 */
	protected boolean isSetValued = false;

	/**
	 * The optional categoricalProperties attribute provides details for categorical values of a
	 * LogicalAttribute.
	 */
	protected CategoricalAttributeProperties categoricalProperties;

	/**
	 * 	The optional numericalProperties attribute provides details for numerical values of a
	 *  LogicalAttribute.
	 */
	protected NumericalAttributeProperties numericalProperties;

	public boolean isSetValued() {
		return isSetValued;
	}

	public void setIsSetValued(boolean new_value) {
		isSetValued = new_value;
	}


	public CategoricalAttributeProperties categoricalProperties() {
		return categoricalProperties;
	}

	public void setCategoricalProperties(CategoricalAttributeProperties new_value) {
		categoricalProperties = new_value;
	}

	public void unset_categorical_properties() {
		categoricalProperties = null;
	}

	public NumericalAttributeProperties numerical_properties() {
		return numericalProperties;
	}

	public void set_numerical_properties(NumericalAttributeProperties new_value) {
		numericalProperties = new_value;
	}

	public void unset_numerical_properties() {
		numericalProperties = null;
	}

}