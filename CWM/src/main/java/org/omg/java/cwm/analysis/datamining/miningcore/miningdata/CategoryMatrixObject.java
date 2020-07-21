package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * The object representation of CategoryMatrix. Each object references a set of
 * CategoryMatrixEntry objects.
 *
 * @author Ivan Holod
 *
 */

public class CategoryMatrixObject extends CategoryMatrix {

	/**
	 * This represents a set of object entries in the CategoryMatrix.
	 */
	protected ArrayList<CategoryMatrixEntry> entry;

	public double getValue(Category rowIndex, Category columnIndex) {
		return 0;
	}

	public void addEntry(Category rowIndex, Category columnIndex, double value) {

	}

	public void dropEntry(Category rowIndex, Category columnIndex) {

	}

}