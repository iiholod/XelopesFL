package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

/**
 * CWM Class
 *
 * This object defines the value of a single cell in a CategoryMatrix.
 *
 * @author Ivan Holod
 *
 */
public class CategoryMatrixEntry {

	/**
	 * This points to the row of the cell.
	 */
	protected Category rowIndex;

	/**
	 * This points to the column of the cell.
	 */
	protected Category columnIndex;

	/**
	 * The value of a cell. It overwrites any default value in CategoryMatrix.
	 * For cost matrix, value is intended to be a double. For confusion matrix,
	 * the value can be either a count which is an integer value, or a
	 * percentage, which is a double value. This is left up to the
	 * implementation.
	 */
	private double value;

	/**
	 * @return the rowIndex
	 */
	public Category getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param rowIndex
	 *            the rowIndex to set
	 */
	public void setRowIndex(Category rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @return the columnIndex
	 */
	public Category getColumnIndex() {
		return columnIndex;
	}

	/**
	 * @param columnIndex
	 *            the columnIndex to set
	 */
	public void setColumnIndex(Category columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

}