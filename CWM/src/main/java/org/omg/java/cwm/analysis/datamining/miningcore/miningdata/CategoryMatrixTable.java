package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Attribute;

import java.util.ArrayList;

/**
 * CWM Calss
 *
 * This is a table representation of CategoryMatrix that contains three columns
 * holding the definition of a category matrix. A category matrix consists of
 * exactly one table. The table has three attributes: The row, column, and value
 * of the entry.
 *
 * @author Ivan Holod
 *
 */
public class CategoryMatrixTable extends CategoryMatrix {
	/**
	 * This represents the column in the table holding the rows of entries.
	 */
	protected Attribute rowAttribute;

	/**
	 * This represents the column in the table holding the columns of entries.
	 */
	protected Attribute columnAttribute;

	/**
	 * This represents the column in the table holding the values of the
	 * entries.
	 */
	protected Attribute valueAttribute;

	/**
	 * This represents the metadata description of the table where the category
	 * matrix is stored.
	 */
	protected ArrayList<Class> source;

	/**
	 * @return the rowAttribute
	 */
	public Attribute getRowAttribute() {
		return rowAttribute;
	}

	/**
	 * @param rowAttribute
	 *            the rowAttribute to set
	 */
	public void setRowAttribute(Attribute rowAttribute) {
		this.rowAttribute = rowAttribute;
	}

	/**
	 * @return the columnAttribute
	 */
	public Attribute getColumnAttribute() {
		return columnAttribute;
	}

	/**
	 * @param columnAttribute
	 *            the columnAttribute to set
	 */
	public void setColumnAttribute(Attribute columnAttribute) {
		this.columnAttribute = columnAttribute;
	}

	/**
	 * @return the valueAttribute
	 */
	public Attribute getValueAttribute() {
		return valueAttribute;
	}

	/**
	 * @param valueAttribute
	 *            the valueAttribute to set
	 */
	public void setValueAttribute(Attribute valueAttribute) {
		this.valueAttribute = valueAttribute;
	}

	/**
	 * @return the source
	 */
	public ArrayList<Class> getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(ArrayList<Class> source) {
		this.source = source;
	}
}