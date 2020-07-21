package org.eltech.ddm.clustering;

import org.eltech.ddm.miningcore.miningdata.EAttributeUsage;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryMatrix;

/**
 * CWM Class
 *
 * A ClusteringAttributeUsage is a subclass of AttributeUsage to support attribute usages
 * that are specific to clustering algorithms.
 *
 * @author Ivan Holod
 *
 */
public class ClusteringAttributeUsage extends EAttributeUsage {



	/**
	 * When two records are compared then either the distance of similarity is of interest. In both
	 * cases, the measures can be computed by a combination of inner function and an outer
	 * function. The inner function compares two single field values and the outer function computes
	 * an aggregation over all fields.
	 */
	private AttributeComparisonFunction attributeComparisonFunction;

	/**
	 * If the attributeComparisonFunction admits a value, then similarityScale is that value. Only
	 * valid for numerical attributes. Null otherwise.
	 */
	private double similarityScale;

	/**
	 * The ComparisonMatrix attribute specifies a matrix used by a clustering algorithm. There are
	 * several kinds of matrices used within clustering models; for example, to describe covariances
	 * and similarities.
	 */
	private CategoryMatrix comparisonMatrix;

	public ClusteringAttributeUsage(ELogicalAttribute attr) {
		super(attr);
		// TODO Auto-generated constructor stub
	}
}
