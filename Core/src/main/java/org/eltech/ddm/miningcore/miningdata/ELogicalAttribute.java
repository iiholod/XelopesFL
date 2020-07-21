package org.eltech.ddm.miningcore.miningdata;

import org.eltech.ddm.StringUtils;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.LogicalAttribute;


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
public class ELogicalAttribute extends LogicalAttribute  //javax.datamining.data.LogicalAttribute
{

	// features added from JDMAPI
	private String description;

	//private DataPreparationStatus dataPreparationStatus;


	public ELogicalAttribute() {
		super();
	}

	public ELogicalAttribute(String name, AttributeType type) {
		this();
		setName(name);
		setAttributeType(type);
	}


	/**
	 * @param attributeType the attributeType to attributes
	 */
	public void setAttributeType(AttributeType attributeType) {
		switch(attributeType){
			case categorical:
				categoricalProperties = new ECategoricalAttributeProperties();
				break;
			case ordinal:
				categoricalProperties = new EOrdinalAttributeProperties();
				break;
			case numerical:
				numericalProperties = new ENumericalAttributeProperties();
				break;
			case notSpecified:
				numericalProperties = null;
				categoricalProperties = null;
				break;
		}

		super.setAttributeType(AttributeType.valueOf(attributeType.toString()));
	}

	/**
	 * @return the categoricalProperties
	 */
	public ECategoricalAttributeProperties getCategoricalProperties() {
		return (ECategoricalAttributeProperties)categoricalProperties;
	}


	/**
	 * Returns true if the domain of the numerical attribute is discrete. The default is false.
	 * @return true if attribute is discreted
	 */
	public boolean isDiscrete() {

		return ((ENumericalAttributeProperties)numericalProperties).isDiscrete();
	}

	/**
	 * Specifies whether the attribute contains discrete values.
	 * @param isDiscrete - An indicator that the numerical domain is consisted of discrete values, if true.
	 */
	public void isDiscrete(boolean isDiscrete) {
		((ENumericalAttributeProperties)numericalProperties).setDiscrete(isDiscrete);

	}

	/**
	 * @return the numericalProperties
	 */
	public ENumericalAttributeProperties getNumericalProperties() {
		return (ENumericalAttributeProperties)numericalProperties;
	}

	/**
	 * @return the dataPreparationStatus
	 */
//	public DataPreparationStatus getDataPreparationStatus() {
//		return dataPreparationStatus;
//	}

	/**
	 * @param isSetValued the isSetValued to attributes
	 */
	public void setSetValued(boolean isSetValued) {
		this.isSetValued = isSetValued;
	}

	/**
	 * @return the isSetValued
	 */
	public boolean isSetValued() {
		return isSetValued;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to attributes
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param dataPreparationStatus the dataPreparationStatus to attributes
	 */
//	public void setDataPreparationStatus(DataPreparationStatus dataPreparationStatus) {
//		this.dataPreparationStatus = dataPreparationStatus;
//	}

    /**
     * Returns string representation of mining data specification.
     *
     * @return string representation
     */
    public String toString()
    {
		String description = StringUtils.quote(getName()) + " ";
		if(getAttributeType() == AttributeType.categorical){
			description = description  + "{";
			for (Object value : ((ECategoricalAttributeProperties) categoricalProperties).getValues()) {
				description = description  + value + ",";
			}
			description = description.substring(0, description.length()-1) + "}";
		}

		else
			description = description + AttributeType.numerical;

		return description;
    }

	@Override
    public Object clone() {
    	ELogicalAttribute o = null;
	    o = (ELogicalAttribute)super.clone();

	    if (categoricalProperties != null)
	    	o.categoricalProperties = (ECategoricalAttributeProperties)((ECategoricalAttributeProperties)categoricalProperties).clone();

	    if (numericalProperties != null)
	    	o.numericalProperties = (ENumericalAttributeProperties)((ENumericalAttributeProperties)numericalProperties).clone();

	    return o;
	}
    
    @Override
    public boolean equals(Object obj) {
    	ELogicalAttribute la = (ELogicalAttribute)obj;
    	return attributeType.equals(la.getAttributeType()) &&
    			getName().equals(la.getName());
    }
    
    @Override
    public int hashCode() {
    	return name.hashCode();
    }
}
