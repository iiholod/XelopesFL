package org.eltech.ddm.miningcore.miningdata;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoricalAttributeProperties;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CWM Class
 *
 * A CategoricalAttributeProperties object is used to describe properties of a categorical
 * attribute. It lists the specific categories that are recognized in the attribute, as well as a
 * taxonomy, or CategorizationGraph, that organizes the categories into a hierarchy.
 * This metadata may or may not be used by the underlying algorithm. It may be
 * leveraged to determine if data being supplied as input to a mining operation is
 * sufficiently similar to the data used to build the model.
 *
 * @author Ivan Holod
 *
 */
public class ECategoricalAttributeProperties extends CategoricalAttributeProperties implements Cloneable, Serializable// CategorySet
{

	// features for JDMAPI

	private AttributeDataType dataType = null;

	private CategoryProperty defaultProperty = CategoryProperty.valid;


	public ECategoricalAttributeProperties(){
		category = new ArrayList<Category>();
	}

	/**
	 * Adds a categorical value to the attributes and returns an index that is to be used to refer to the value.
	 *
	 * @param categoryValue - A categorical value to be added.
	 * @param property
	 * @return
	 */
	public int addCategory(Object categoryValue, CategoryProperty property) throws MiningException{
		ECategory cat = new ECategory(categoryValue);

		if(dataType != null){
			if(dataType != cat.getDataType())
				throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "Type of added category not equalents type of CategoricalAttributeProperties.");
		}
		else // add first category
			dataType = cat.getDataType();

		cat.setProperty(property);
		category.add(cat);
		cat.setIndex(category.indexOf(cat));

		return cat.getIndex();
	}

	/**
	 * Adds a category to the attributes and returns an index that is to be used to refer to the value.
	 *
	 * @param cat - A category  to be added.
	 * @return
	 */
	private int addCategory(ECategory cat) throws MiningException{
		if(dataType != null){
			if(dataType != cat.getDataType())
				throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "Type of added category not equalents type of CategoricalAttributeProperties.");
		}
		else // add first category
			dataType = cat.getDataType();

		category.add(cat);

		return category.indexOf(cat);
	}

	/**
	 * Returns the data type of the categorical values in the attributes.
	 * @return
	 */
	public AttributeDataType getDataType() {
		return dataType;
	}

	/**
	 * Returns the default property of unspecified categorical values that are not contained in the attributes.
	 * The initial default is valid.
	 * @return
	 */
	public CategoryProperty getDefaultProperty() {
		return defaultProperty;
	}

	/**
	 * Returns the index of the categorical value. Returns null if the value is not included in the attributes.
	 * @param categoryValue - A categorical value whose index is to be returned.
	 * @return
	 */
	@Deprecated
	public Integer getIndex(Object categoryValue){
		ECategory cat = null;
		for (int i = 0; i < category.size(); i++) {
			cat = (ECategory)category.get(i);
			if(cat.getValue().equals(categoryValue))
				return new Integer(i);
		}
		return null;
	}

	/**
	 * Returns the name of the categorical value with the specified index.
	 * Returns null if no name is associated with the specified categorical value.
	 * Names need not be unique within the attributes.
	 * @param index - The index of the categorical value whose name is to be returned.
	 * @return
	 */
	public String getName(int index) {
		return getCategory(index).getDisplayName();
	}

	/**
	 * Returns the property of the categorical value with the specified index.
	 * @param index - The index of the categorical value whose property is to be returned.
	 * @return
	 */
	public CategoryProperty getProperty(int index) {
		return getCategory(index).getProperty();
	}

	/**
	 * Returns the number of categories in the attributes.
	 * @return
	 */
	public int getSize() {
		return category.size();
	}

	/**
	 * Returns the category with the specified index.
	 * @param index - The index of the categorical value to be returned.
	 * @return
	 */
	public ECategory getCategory(int index) {
		return (ECategory)category.get(index);
	}

	/**
	 * Returns the categorical value with the specified index.
	 * @param index - The index of the categorical value to be returned.
	 * @return
	 */
	public Object getValue(int index) {
		return getCategory(index).getValue();
	}

	/**
	 * Returns all categorical values contained in the attributes.
	 * @return - all categorical values contained in the attributes.
	 */
	public List<Category> getCategories() {
		return category;
	}

	/**
	 * Returns all categorical values contained in the attributes.
	 * @return - all categorical values contained in the attributes.
	 */
	@Deprecated
	public Object[] getValues() {
		ArrayList<Object> values = new ArrayList<Object>();
		for (Category cat : category) {
			values.add(((ECategory)cat).getValue());
		}

		return values.toArray();
	}

	/**
	 * Returns the list of all values with the given property.
	 * The indexes of the returned list need not to be identical to the source indexes.
	 * @param property - given property
	 * @return - list of all values with the given property.
	 * @throws MiningException
	 */
	@Deprecated
	public Object[] getValues(CategoryProperty property) throws MiningException {
		ArrayList<Object> values = new ArrayList<Object>();
		for (Category cat : category) {
			if(((ECategory)cat).getProperty() == property)
				values.add(((ECategory)cat).getValue());
		}

		return values.toArray();
	}

	/**
	 * Inserts the given category value and property before the given index in the attributes.
	 * All categories with indexes J>=I before the call have a shifted index of J+1 after the call.
	 * @param categoryValue -
	 * @param property -
	 * @param beforeIndex -
	 * @throws MiningException
	 */
	public void insertCategory(Object categoryValue, CategoryProperty property, int beforeIndex)
			throws MiningException {

		if(category.size() <= beforeIndex)
			throw new MiningException(MiningErrorCode.INVALID_INDEX);

		ECategory cat = new ECategory(categoryValue);

		if(dataType != null){
			if(dataType != cat.getDataType())
				throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "Type of added category not equalents type of CategoricalAttributeProperties.");
		}
		else // add first category
			dataType = cat.getDataType();

		cat.setProperty(property);
		cat.setIndex(beforeIndex+1);
		category.add(beforeIndex+1, cat);

	}

	/**
	 * Removes the categorical value refered to by the specified index.
	 * All higher indexes are lowered by one. Ordering is maintained after removal.
	 * A subsequent call of getValue(index) or getProperty(index) will
	 * return the value delivered by getValue(index+1) or getProperty(index+1), respectively.
	 * @param index - The index of the categorical value to be removed.
	 * @throws MiningException
	 */
	public void removeCategory(int index) throws MiningException {
		if(category.size() <= index)
			throw new MiningException(MiningErrorCode.INVALID_INDEX);

		category.remove(index);

	}

	/**
	 * Sets the default property of unspecified categorical values that are not contained in the attributes.
	 * A null value indicates that no default property has to be used.
	 * @param  	property - The default property of the unknown categorical values.
	 */
	public void setDefaultProperty(CategoryProperty property) {
		defaultProperty = property;

	}

	/**
	 * Sets the name of the category specified by the index.
	 * The name can be used as the label (or display name) for the category.
	 * @param     index -
	 * @param     categoryName -
	 */
	public void setName(int index, String categoryName) throws MiningException {
		if(category.size() <= index)
			throw new MiningException(MiningErrorCode.INVALID_INDEX);

		((ECategory)category.get(index)).setDisplayName(categoryName);
	}

	/**
	 * @param taxonomy the taxonomy to attributes
	 */
	public void setTaxonomy(ECategoryTaxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}

	/**
	 * @return the taxonomy
	 */
	public ECategoryTaxonomy getTaxonomy() {
		return (ECategoryTaxonomy)taxonomy;
	}

	public Object clone() {
		ECategoricalAttributeProperties o = null;
		try {
			o = (ECategoricalAttributeProperties) super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println(this.getClass().toString() + " can't be cloned");
		}

		if (category != null) {
			ArrayList<Category> ac = new ArrayList<Category>();
			for (Category c : category) {
				ac.add((ECategory)((ECategory) c).clone());
			}
			o.category = ac;
		}

		if (taxonomy != null)
			o.taxonomy = (ECategoryTaxonomy)((ECategoryTaxonomy) taxonomy).clone();

		return o;
	}



}