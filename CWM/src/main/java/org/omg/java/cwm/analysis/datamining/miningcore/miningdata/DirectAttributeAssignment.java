package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

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
public class DirectAttributeAssignment extends AttributeAssignment {


	/**
	 * This points to a physical attribute being assigned to a logical attribute.
	 */
	protected Attribute attribute;

}