package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Attribute;
import org.omg.java.cwm.objectmodel.core.Class;

/**
 * CWM Class
 *
 * Tabular representation of a taxonomy graph. A taxonomy graph consists of zero or
 * more tables. Each table can store the portion of the graph corresponding to a single
 * level or multiple levels. The table has three attributes: Child, parent, and graphid.
 * Superclasses
 *
 * @author Ivan Holod
 *
 */
public class CategoryMapTable extends CategoryMap{

	/**
	 * This represents the child attribute in the graph to appear in the table.
	 */
	protected Attribute childAttribute;

	/**
	 * This represents the parent attribute in the graph to appear in the table.
	 */
	protected Attribute parentAttribute;

	/**
	 * The graphId attribute identifies the graph to which the graph entry belongs and enables
	 * representing multiple taxonomy graphs in the same table.
	 */
	protected Attribute graphIDAttribute;

	/**
	 * This represents the metadata description of the table where the category map is stored.
	 */
	protected Class table;


	protected void CategoryMapTable(Attribute graphID, Attribute parent, Attribute child, Class table){
		this.graphIDAttribute = graphID;
		this.childAttribute = child;
		this.parentAttribute = parent;
		this.table = table;
	}

	/**
	 * @return the childAttribute
	 */
	public Attribute getChildAttribute() {
		return childAttribute;
	}

	/**
	 * @param childAttribute the childAttribute to set
	 */
	public void setChildAttribute(Attribute childAttribute) {
		this.childAttribute = childAttribute;
	}

	/**
	 * @return the parentAttribute
	 */
	public Attribute getParentAttribute() {
		return parentAttribute;
	}

	/**
	 * @param parentAttribute the parentAttribute to set
	 */
	public void setParentAttribute(Attribute parentAttribute) {
		this.parentAttribute = parentAttribute;
	}

	/**
	 * @return the graphIDAttribute
	 */
	public Attribute getGraphIDAttribute() {
		return graphIDAttribute;
	}

	/**
	 * @param graphIDAttribute the graphIDAttribute to set
	 */
	public void setGraphIDAttribute(Attribute graphIDAttribute) {
		this.graphIDAttribute = graphIDAttribute;
	}

	/**
	 * @return the table
	 */
	public Class getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(Class table) {
		this.table = table;
	}

}