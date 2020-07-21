package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * A CategoricalAttributeProperties object is used to describe properties of a categorical
 * attribute. It lists the specific categories that are recognized in the attribute, as well as a
 * taxonomy, or CategorizationGraph, that organizes the categories into a hierarchy.
 * This metadata may or may not be used by the underlying algorithm. It may be
 * leveraged to determine if data being supplied as input to a mining operation is
 * sufficiently similar to the data used to build the model.
 *
 * @author Ivan Holod
 *
 */
public class CategoricalAttributeProperties {


	/**
	 * The set of categories listed for the attribute. If only the missing categories are listed, all
	 * other categories are considered valid. If valid categories are listed, all other non-missing
  	 * categories are considered invalid. If only invalid categories are listed, all other non-missing
	 * categories are considered valid. If both valid and invalid categories are listed, all other
	 * non-missing categories encountered result in an exception.
	 */
	protected ArrayList<Category> category;

	/**
	 * The taxonomy describes a hierarchical organization of the valid categories among the attribute
	 * data. There may be zero or more specified for a given attribute. If more than one are specified,
	 * the Data Mining System is supposed to produce one model per taxonomy as a single model
	 * result.
	 */
	protected CategoryTaxonomy taxonomy;


}