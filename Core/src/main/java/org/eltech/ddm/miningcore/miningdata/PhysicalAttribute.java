/**
 *
 */
package org.eltech.ddm.miningcore.miningdata;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;

/**
 * The interface PhysicalDataSet serves as a common interface for describing data to be used
 * as input to data mining. Through the mapping of attributes, attributes of the physical data
 * are mapped to logical attributes of a model?s logical data. The data referenced by a physical
 * data object can be used in, for example, model building, model application (scoring), and test.
 *
 * The physical attributes are ordered and can be accessed individually by index, which ranges
 * between 0 and n-1, where n is the number of physical attributes.
 *
 * @author Ivan Holod
 *
 */
public class PhysicalAttribute extends MiningAttribute //implements javax.datamining.data.PhysicalAttribute
{
	private AttributeDataType dataType;

	private PhysicalAttributeRole role;

	public PhysicalAttribute(String attrName, AttributeType attrType, AttributeDataType dataType) {
		setName(attrName);
		this.dataType = dataType;
		this.attributeType = attrType;
	}

	public PhysicalAttribute() {
	}


	/**
	 * Returns the data type of the attribute.
	 * @return
	 */
	public AttributeDataType getDataType() {
		return dataType;
	}

	/**
	 * Returns the attribute role.
	 * @return
	 */
	public PhysicalAttributeRole getRole() {
		return role;
	}

	/**
	 * Sets the representation of the values contained in the attribute.
	 * @param dataType - The data type of the attribute.
	 */
	public void setDataType(AttributeDataType dataType) {
		this.dataType = dataType;

	}

	/**
	 * Sets the description of the attribute. The description may be used for display name of the attribute.
	 * Display name is typically used for graphic presentation to make the name of the attribute more understandable to the viewer.
	 * @param description
	 */
	public void setDescription(String description) {
		displayName.setString(description);

	}

	/**
	 * Sets the role of the attribute.
	 * @param role - The role of the physical attribute that specifies the kind of data the attribute represents.
	 */
	public void setRole(PhysicalAttributeRole role) {
		this.role = role;

	}

	/**
	 * Returns the name of the attribute. The returned name must not be null.
	 */
	public String getDescription() {
		return displayName.getString();
	}


}
