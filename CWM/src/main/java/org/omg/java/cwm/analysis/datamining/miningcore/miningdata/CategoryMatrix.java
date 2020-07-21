package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * A CategoryMatrix assigns numeric values to pairs of categories. It is either represented
 * as a set of CategoryMatrixEntry objects or as a table.
 *
 * @author Ivan Holod
 *
 */
public class CategoryMatrix {

	/**
	 * If a matrix cell in the diagonal is not specified, then this value is used.
	 * The default value is 1.0
	 */
	protected double diagonalDefault = 1.0;

	/**
	 * If a matrix cell not in the diagonal is not specified, then this value is used.
	 * The default value 0
	 */
	protected double offDiagonalDdefault = 0;

	/**
	 * This specifies the type of matrix: Diagonal, symmetric, or any. If diagonal, then all
	 * values outside the diagonal are 0. If symmetric, then value(i,j)=value(j,i).
	 */
	protected MatrixProperty kind;

	/**
	 * This enumerates the categories spanning the matrix.
	 */
	protected ArrayList<Category> category;

	/**
	 * @return the diagonalDefault
	 */
	public double getDiagonalDefault() {
		return diagonalDefault;
	}

	/**
	 * @param diagonalDefault the diagonalDefault to set
	 */
	public void setDiagonalDefault(double diagonalDefault) {
		this.diagonalDefault = diagonalDefault;
	}

	/**
	 * @return the offDiagonalDdefault
	 */
	public double getOffDiagonalDdefault() {
		return offDiagonalDdefault;
	}

	/**
	 * @param offDiagonalDdefault the offDiagonalDdefault to set
	 */
	public void setOffDiagonalDdefault(double offDiagonalDdefault) {
		this.offDiagonalDdefault = offDiagonalDdefault;
	}

	/**
	 * @return the kind
	 */
	public MatrixProperty getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(MatrixProperty kind) {
		this.kind = kind;
	}

	/**
	 * @return the category
	 */
	public ArrayList<Category> getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(ArrayList<Category> category) {
		this.category = category;
	}


}