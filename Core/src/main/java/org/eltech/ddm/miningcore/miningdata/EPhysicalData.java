package org.eltech.ddm.miningcore.miningdata;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.PhysicalData;
import org.omg.java.cwm.objectmodel.core.Class;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A PhysicalData object specifies the layout of the physical data to be used for mining,
 * and if appropriate, the roles the various data columns play, via subclassing. The data
 * referenced by a physical data object can be used in many capacities: model building,
 * scoring, lift computation, statistical analysis, etc.
 * PhysicalData supports specification of any data definable through a Class or attributes of
 * Attributes (e.g., files, tables, and star schema).
 *
 * @author Ivan Holod
 *
 */
public class EPhysicalData extends PhysicalData implements Cloneable //PhysicalDataSet
{
	// features for JDMAPI
	private ArrayList<PhysicalAttribute> attributes;

	private String uri;


	public EPhysicalData(){
		attributes = new ArrayList<PhysicalAttribute>();
	}
	/**
	 * Adds a physical attribute to the physical data. The attribute is appended to the end of the list.
	 * @param attribute - A physical attribute to be added.
	 * @throws MiningException
	 */
	public void addAttribute(PhysicalAttribute attribute) throws MiningException {
		attributes.add(attribute);

	}

	/**
	 * Adds an array of physical attributes to the physical data. The attributes are appended to the list.
	 * @param attributeArray - An array of physical attributes to be added.
	 * @throws MiningException
	 */
	public void addAttributes(PhysicalAttribute[] attributeArray) throws MiningException {
		for (PhysicalAttribute physicalAttribute : attributeArray) {
			attributes.add(physicalAttribute);
		}
	}

	/**
	 * Returns an instance of PhyisicalAttribute with the specified name present in the physical data.
	 * @param attributeName - The name of the physical attribute to be returned.
	 * @return
	 * @throws MiningException
	 */
	public PhysicalAttribute getAttribute(String attributeName) throws MiningException {
		for (PhysicalAttribute physicalAttribute : attributes) {
			if(physicalAttribute.getName().equals(attributeName))
				return physicalAttribute;
		}
		return null;
	}

	/**
	 * Returns the PhysicalAttribute that corresponds to the specified index.
	 * @param index - The index of the physical attribute to be returned.
	 * @return
	 */
	public PhysicalAttribute getAttribute(int index) {
		return attributes.get(index);
	}

	/**
	 * Returns the number of PhysicalAttribute objects contained.
	 * @return
	 */
	public int getAttributeCount() {
		return attributes.size();
	}

	/**
	 * Returns the index of the specified PhysicalAttribute name.
	 * @param attributeName - The name of the physical attribute whose index is to be returned.
	 * @return
	 * @throws MiningException
	 */
	public Integer getAttributeIndex(String attributeName) throws MiningException {
		if(attributeName == null)
			throw new MiningException(MiningErrorCode.INVALID_ARGUMENT);

		for (int i = 0; i < attributes.size(); i++) {
			if(attributes.get(i).getName().equals(attributeName))
				return new Integer(i);
		}
		return null;
	}

	/**
	 * Returns a collection of names of the physical attributes of the specified role present in the physical data.
	 * The attributes are ordered by the index, i.e., the order by which each attribute was put into PhysicalData.
	 * The first attribute (i.e., the one specified by the name) in the collection was put ahead of the second one in the PhysicalData.
	 * @param role -
	 * @return
	 * @throws MiningException
	 */
	public Collection<String> getAttributeNames(PhysicalAttributeRole role)
			throws MiningException {
		ArrayList<String> collection = new ArrayList<String>();
		for (PhysicalAttribute physicalAttribute : attributes) {
			if(physicalAttribute.getRole() == role)
				collection.add(physicalAttribute.getName());
		}


		return collection;
	}

	/**
	 * Returns a collection of names of the physical attributes of the specified data type present in the physical data.
	 * The attributes are ordered by the index, i.e., the order by which each attribute was put into PhysicalData.
	 * The first attribute (i.e., the one specified by the name) in the collection was put ahead of the second one in the PhysicalData.
	 * @param dataType -
	 * @return
	 * @throws MiningException
	 */
	public Collection<String> getAttributeNames(AttributeDataType dataType)
			throws MiningException {
		ArrayList<String> collection = new ArrayList<String>();
		for (PhysicalAttribute physicalAttribute : attributes) {
			if(physicalAttribute.getDataType() == dataType)
				collection.add(physicalAttribute.getName());
		}


		return collection;
	}

	/**
	 * Returns the AttributeStatisticsSet instance if any statistics were computed for the PhysicalData.
	 * Returns null if the data statistics is not available.
	 * @return
	 * @throws MiningException
	 */
//	public AttributeStatisticsSet getAttributeStatistics() throws MiningException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 *     Returns a collection of PhyisicalAttribute instances present in the physical data.
	 *     The attributes are ordered by the index, i.e., the order by which each attribute is put into PhysicalDataSet.
	 *     The first attribute in the collection is the attribute that was put first.
	 */
	public Collection getAttributes() throws MiningException {
		return attributes;
	}

	/**
	 * Returns the URI of the physical data.
	 * @return
	 */
	public String getURI() {
		return uri;
	}

	/**
	 * Imports the metadata of the physical data to populate the physical attributes.
	 * After import, the roles are all initialized to data, which can be overridden later.
	 * Metadata import provides a snapshot of the metadata at the time of invocation.
	 * Invocation of this method effectively removes all physical attributes that are currently
	 * present in the object. If this method is not invoked and no physical attribute is added
	 * manually, i.e., the physical data attributes is empty, then the DME tries to obtain the metadata
	 * from the physical data specified by the URI when this physical data attributes is used.
	 * Statistics may be populated automatically if they are available, this is vendor-specific.
	 * Statistics at this level may be approximate or exact.
	 * If the data contains attributes that are not string, double, or integer, the vendor determines
	 * the mapping from the data source type to the JDM attribute type.
	 * If a data type is not supported or mappable to one of the standard types, it has attribute
	 * data type of unknown.
	 *
	 * @throws MiningException
	 */
	public void importMetaData() throws MiningException {
		// TODO Auto-generated method stub

	}

	/**
	 * Removes all physical attributes currently associated with the PhysicalDataSet instance.
	 */
	public void removeAllAttributes() {
		attributes.clear();
	}

	/**
	 * Removes the specified attribute from the physical data. The remaining attributes are shifted
	 * towards the beginning of the list, i.e., the index values are decreased.
	 *  If name not found, an exception is thrown.
	 * @param name - The name of the physical attribute to be removed.
	 * @throws MiningException
	 */
	public void removeAttribute(String name) throws MiningException {
		for (PhysicalAttribute physicalAttribute : attributes) {
			if(physicalAttribute.getName().equals(name)){
				attributes.remove(physicalAttribute);
				return;
			}
		}
		throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "Physical data has not attribute with name " + name);
	}

	/**
	 * @param source the source to attributes
	 */
	public void setSource(Class source) {
		this.source = source;
	}

	/**
	 * @return the source
	 */
	public Class getSource() {
		return source;
	}

	public Object clone() {
		EPhysicalData o = null;
		o = (EPhysicalData) super.clone();

		if (source != null)
			o.source = (Class) source.clone();

		if (attributes != null) {
			ArrayList<PhysicalAttribute> apa = new ArrayList<PhysicalAttribute>();
			for (PhysicalAttribute pa : attributes) {
				apa.add((PhysicalAttribute) pa.clone());
			}
			o.attributes = apa;
		}

		return o;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}
}