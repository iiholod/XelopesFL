package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

/**
 * CWM Class
 *
 * This is the common superclass of CategoryMapObject and CategoryMapTable
 * supporting the CategorizationGraph class.
 *
 * @author Ivan Holod
 *
 */
public class CategoryMap {

	/**
	 * This indicates that this table or object represents multiple levels of the categorization graph, if
	 * true. The default value is false.
	 */
	protected boolean isMultiLevel = false;

	/**
	 * This indicates that this is a grouping of items to categories, if true. The default value is
	 * false.
	 */
	protected boolean isItemMap = false;

	/**
	 * @return the isMultiLevel
	 */
	public boolean isMultiLevel() {
		return isMultiLevel;
	}

	/**
	 * @param isMultiLevel the isMultiLevel to set
	 */
	public void setMultiLevel(boolean isMultiLevel) {
		this.isMultiLevel = isMultiLevel;
	}

	/**
	 * @return the isItemMap
	 */
	public boolean isItemMap() {
		return isItemMap;
	}

	/**
	 * @param isItemMap the isItemMap to set
	 */
	public void setItemMap(boolean isItemMap) {
		this.isItemMap = isItemMap;
	}


	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println(this.getClass().toString() + " can't be cloned");
		}

		return o;
	}

}