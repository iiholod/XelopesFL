package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Class;
import org.omg.java.cwm.objectmodel.core.ModelElement;

/**
 * A PhysicalData object specifies the layout of the physical data to be used for mining,
 * and if appropriate, the roles the various data columns play, via subclassing. The data
 * referenced by a physical data object can be used in many capacities: model building,
 * scoring, lift computation, statistical analysis, etc.
 * PhysicalData supports specification of any data definable through a Class or set of
 * Attributes (e.g., files, tables, and star schema).
 *
 * @author Ivan Holod
 *
 */
public class PhysicalData extends ModelElement {
	/**
	 * The source attribute identifies the specific data to be used for data mining. It typically consists
	 * of a Table (from the Relational Package) or a File. Unless a specific subset of attributes is
	 * listed, all source attributes are taken as the usable physical data. If source is NULL, the
	 * attributes may be associated with one or more Class instances.
	 */
	protected Class source;

}