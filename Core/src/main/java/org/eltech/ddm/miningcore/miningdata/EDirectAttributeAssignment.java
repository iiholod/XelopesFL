package org.eltech.ddm.miningcore.miningdata;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.DirectAttributeAssignment;
import org.omg.java.cwm.objectmodel.core.Attribute;

/**
 * CWM Class
 *
 * This object maps a pair of attributes from two different sources, for example, a table
 * column and a LogicalAttribute, which is an internal representation of the column to be
 * used in a mining operation.
 *
 * @author Ivan Holod
 *
 */
public class EDirectAttributeAssignment extends DirectAttributeAssignment {


	public EDirectAttributeAssignment() {
	};

	public void setAttribute(Attribute new_value) {
		attribute = new_value;
	}

	public Attribute getAttribute() {
		return attribute;
	}

}