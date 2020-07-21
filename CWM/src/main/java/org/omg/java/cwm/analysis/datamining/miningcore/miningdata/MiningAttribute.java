package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Attribute;
import org.omg.java.cwm.objectmodel.core.String;

/**
 *  CWM Class
 *
 *  This is an abstract class that describes the generic attribute to be used in mining
 *  operations
 *
 *  @author Ivan Holod
 */
public abstract class MiningAttribute extends Attribute {
  /* {author=push_king
 , src_lang=Java}*/

	/**
	 * The optional displayName of an attribute indicates a name that an application may use as a
	 * substitute for the actual MiningAttribute name, which may be cryptic.
	 */
	protected String displayName;

	/**
	 * The attribute type indicates if the attribute is categorical, ordinal, numerical, or not specified.
	 * If either categoricalProperties or numericalProperties are specified, a constraint exists to
	 * ensure the attributeType value is consistent with these attributes. This attribute allows a
	 * MiningAttribute to be identified with a particular type even if no additional properties are
	 * specified. If ordinal, then the OrdinalAttributeProperties must be specified to indicate the
	 * ordering of the categories.
	 */
	protected AttributeType attributeType;


	/**
	 * @return the Name
	 */
	public java.lang.String getName() {
		return name.toString();
	}

	/**
	 * @param name the displayName to set
	 */
	public void setName(java.lang.String name) {
		this.name.setString(name);
		if(displayName == null)
		{
			displayName = new String();
			displayName.setString(this.name.getString());
		}
	}

	public AttributeType getAttributeType() {
		return attributeType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setAttributeType(AttributeType new_value) {
		attributeType = new_value;
	}

	public void setDisplayName(String new_value) {
		displayName = new_value;
	}


    /**
     * Returns string representation of mining data specification.
     *
     * @return string representation
     */
    public java.lang.String toString()
    {
		return displayName.getString() + " " + attributeType;
    }


}