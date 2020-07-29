/**
 *  A LogicalData object contains the attributes of LogicalAttributes that describe the logical
 *  nature of the data used as input to data mining. The LogicalAttributes within a
 *  LogicalData object are uniquely named.
 *
 *  @author Holod Ivan
 */

package org.eltech.ddm.miningcore.miningdata;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.NamedObject;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.LogicalAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.LogicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
public class ELogicalData extends LogicalData implements Cloneable
{
	//protected ArrayList<ELogicalAttribute> feature;

	public ELogicalData() {
		feature = new ArrayList<LogicalAttribute>();
	}

//	public ELogicalData(EPhysicalData data){
//		feature = new ArrayList<LogicalAttribute>();
//
//		// TODO implement
//	}

	public boolean isCompatible(EPhysicalData data) {
		return true;
	}

	public int getAttributesNumber()
	{
		return feature.size();
	}

	/**
	 * Adds the specified LogicalAttribute instance to the logical data. If the name of the attribute is not unique
	 * within the attributes already present in the logical data, an exception is thrown.
	 * @param attribute - A logical attribute to be added.
	 * @throws MiningException
	 */
	public void addAttribute(ELogicalAttribute attribute) throws MiningException {
		String name = attribute.getName();
		ELogicalAttribute attr = getAttribute(name);

		if(attr != null)
			throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "The added logical attribut is not unique.");

		feature.add(attribute);
	}

	/**
	 * Returns the named logical attribute present in the logical data.
	 * @param name - The name of the logical attribute to be returned.
	 * @return - logical attribute with this name
	 * @throws MiningException
	 */
	@Deprecated
	public ELogicalAttribute getAttribute(String name) {
		for (LogicalAttribute attr : feature) {
			if(attr.getName().equals(name))
				return (ELogicalAttribute)attr;
		}

		return null;
	}

	public List<ELogicalAttribute> getAttributes() throws MiningException {
		return (List<ELogicalAttribute>)(List<?>)feature;
	}

		/**
         * Returns a collection of logical attributes of the specified attribute type present in the logical data.
         * @param type - The type of the logical attributes to be returned.
         * @return - collection of logical attributes of the specified attribute type present in the logical data.
         * @throws MiningException
         */
	public Collection<ELogicalAttribute> getAttributes(AttributeType type) throws MiningException {
		ArrayList<ELogicalAttribute> collection = new ArrayList<ELogicalAttribute>();
		for (LogicalAttribute attr : feature) {
			if(attr.getAttributeType() == type)
				collection.add((ELogicalAttribute)attr);
		}

		return collection;
	}

	/**
	 * Removes all logical attributes from the LogicalData instance.
	 */
	public void removeAllAttributes() {
		feature.clear();
	}

	/**
	 * Removes the specified LogicalAttribute instance from the logical data. If the name of the attribute is not found within the attributes present
	 * in the logical data, an exception is thrown.
	 * @param name - The name of the logical attribute to be removed.
	 * @throws MiningException
	 */
	public void removeAttribute(String name) throws MiningException {
		ELogicalAttribute attr = getAttribute(name);
		if(attr == null)
			throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "The removed attribute is not.");

		feature.remove(attr);
	}

	//@Override
	public NamedObject getObjectType() {
		return NamedObject.logicalData;
	}

	/**
	 * Returns the logical attribute present in the logical data for index.
	 * @param index - The index of the logical attribute to be returned.
	 * @return - logical attribute with this index
	 * @throws MiningException
	 */
	public ELogicalAttribute getAttribute(int index) throws MiningException {
		if(feature.size() <= index)
			throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "Index " + index + " is more than array size " + feature.size() + "!");

		return (ELogicalAttribute)feature.get(index);
	}

	/**
	 * Returns the index of logical attribute present in the logical data.
	 * @param attribute - logical attribute.
	 * @return - index of logical attribute
	 * @throws MiningException
	 */
	@Deprecated
	public int getAttributeIndex(ELogicalAttribute attribute) {
		return feature.indexOf(attribute);
	}

    /**
     * Returns string representation of mining data specification.
     *
     * @return string representation
     */
    public String toString()
    {
      String relName = getName();
      if (relName != null) relName = org.eltech.ddm.StringUtils.quote( relName );
      String description = "relation: " + relName + "\n";
      int n = feature.size();
      for( int i = 0; i < n; i++ ) {
          MiningAttribute attribute = (MiningAttribute)feature.get( i );
          description = description + String.valueOf(i) + "." + attribute + "\n";
      }

      return description;
    }

	@Override
	public boolean equals(Object obj) {
    	ELogicalData ld = (ELogicalData)obj;
    	if(this.getAttributesNumber() != ld.getAttributesNumber()) return false;

    	for (int i = 0; i< this.getAttributesNumber(); i++) {
			String firstName = null, secName = null;
			try {
				firstName = this.getAttribute(i).getName();
				secName = ld.getAttribute(i).getName();
			} catch (MiningException e) {
				e.printStackTrace();
			}
			if (!firstName.equals(secName)) return false;
		}

    	return true;
	}

	public Object clone() {
		ELogicalData o = null;
		o = (ELogicalData) super.clone();

		if (feature != null) {
			ArrayList<LogicalAttribute> f = new ArrayList<LogicalAttribute>();
			for (LogicalAttribute la : feature) {
				f.add((ELogicalAttribute) la.clone());
			}
			o.feature = f;
		}

		return o;
	}
}
