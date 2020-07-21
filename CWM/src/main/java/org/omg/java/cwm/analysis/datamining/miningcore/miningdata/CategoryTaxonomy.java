package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * A CategoryTaxonomy supports the specification of taxonomy or category hierarchy as
 * required by data mining in the form of a directed acyclic graph. It enables two
 * representations: 1) Explicit specification of the graph through the referenced node
 * class, and 2) referencing a table with specific attributes (columns) that store the data in
 * tabular form.
 * A CategoryTaxonomy can contain multiple "root" nodes, in a sense being a single
 * representation for several possibly strict hierarchies.
 *
 *
 * @author Ivan Holod
 *
 */
public class CategoryTaxonomy {

	/**
	 * This references to the CategoryMap which can be either an object or table
	 * representation of the CategoryTaxonomy.
	 */
	protected ArrayList<CategoryMap> categoryMap;

	/**
	 * A CategoryTaxonomy can have multiple roots. The root nodes references the
	 * corresponding Category objects.
	 */
	protected ArrayList<Category> rootCategory;

}