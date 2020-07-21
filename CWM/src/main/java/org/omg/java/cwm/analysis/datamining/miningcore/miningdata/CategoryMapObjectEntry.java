package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

/**
 * CWM Class
 *
 * Object representation of an edge in the taxonomy graph. This is analogous to a record
 * in the CategoryMapTable. Each entry consists of child, parent, level and graphId
 * attributes. If isItemMap is true, then the child attribute corresponds to item values.
 *
 * @author Ivan Holod
 *
 */
public class CategoryMapObjectEntry {

	/**
	 * This represents the child Category being pointed to by this graph entry.
	 */
	protected Category child;

	/**
	 * This represents the parent Category being pointed to by this graph entry.
	 */
	protected Category parent; // TODO In CWM 1.1 this feature is Array, but I think it is error (see comment to this class)

	/**
	 * The graphId attribute identifies the graph to which this entry belongs and enables representing
	 * multiple categorization graphs in the same table.
	 */
	protected Object graphID;


	public CategoryMapObjectEntry(Object graphID, Category parent, Category child){
		this.graphID = graphID;
		this.child = child;
		this.parent = parent;
	}

	/**
	 * @return the child
	 */
	public Category getChild() {
		return child;
	}

	/**
	 * @param child the child to set
	 */
	public void setChild(Category child) {
		this.child = child;
	}

	/**
	 * @return the parent
	 */
	public Category getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Category parent) {
		this.parent = parent;
	}

	/**
	 * @return the graphID
	 */
	public Object getGraphID() {
		return graphID;
	}

	/**
	 * @param graphID the graphID to set
	 */
	public void setGraphID(Object graphID) {
		this.graphID = graphID;
	}

}