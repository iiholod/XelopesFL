package org.eltech.ddm.clustering;

/**
 * CWM Enumeration
 * 
 * When two records are compared then either the distance of similarity is of interest. In both
 * cases, the measures can be computed by a combination of inner function and an outer
 * function. The inner function compares two single field values and the outer function computes
 * an aggregation over all fields. 
 * 
 * @author Ivan Holod
 *
 */
public enum AttributeComparisonFunction {
	absDiff,
	gaussSim,
	delta,
	equal,
	table
}
