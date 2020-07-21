package org.eltech.ddm.miningcore.miningdata;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;
import org.omg.java.cwm.objectmodel.core.Any;

import java.io.Serializable;

/**
 * CWM Class
 *
 * This represents a discrete value. A collection of Category instances defines the values
 * that may or may not be annotated with a mining attribute.
 *
 * @author Ivan Holod
 *
 */

public class ECategory extends Category implements Serializable {
	final static public int VALUE = 1;

	// features for JDMAPI
	private AttributeDataType dataType = AttributeDataType.unknownType;


	private int index;


	public ECategory(Object valueCategory) {
		if(valueCategory instanceof Integer)
			setDataType(AttributeDataType.integerType);
		else if(valueCategory instanceof Double)
			setDataType(AttributeDataType.doubleType);
		else if	(valueCategory instanceof String)
			setDataType(AttributeDataType.stringType);

		value = new Any(valueCategory);
		displayName = value.toString();
	}

	public String getName() {
		// TODO Auto-generated method stub
		return value.getObject().toString();
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value.getObject();
	}

	/**
	 * @param value the value to attributes
	 */
	public void setValue(Object value) {
		this.value.setObject(value);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the isNullCategory
	 */
	public boolean isNullCategory() {
		return isNullCategory;
	}

	/**
	 * @param isNullCategory the isNullCategory to attributes
	 */
	public void setNullCategory(boolean isNullCategory) {
		this.isNullCategory = isNullCategory;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to attributes
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the property
	 */
	public CategoryProperty getProperty() {
		return property;
	}

	/**
	 * @param property the property to attributes
	 */
	public void setProperty(CategoryProperty property) {
		this.property = property;
	}

	/**
	 * @return the prior
	 */
	public double getPrior() {
		return prior;
	}

	/**
	 * @param prior the prior to attributes
	 */
	public void setPrior(double prior) {
		this.prior = prior;
	}

	/**
	 * @param dataType the dataType to attributes
	 */
	public void setDataType(AttributeDataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the dataType
	 */
	public AttributeDataType getDataType() {
		return dataType;
	}

	public Object clone() {
    	ECategory o = null;
	    try {
	      o = (ECategory)super.clone();
	    } catch(CloneNotSupportedException e) {
	      System.err.println(this.getClass().toString() + " can't be cloned");
	    }

	    if (value != null)
	    	o.value = (Any)value.clone();

	    return o;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ECategory)
			return value.equals(((ECategory)obj).getValue());
		
		return false;
	}
	
	@Override
	public int hashCode() {
		if(value != null){
			
			return value.hashCode();
		}
		
		return super.hashCode();
	}

}