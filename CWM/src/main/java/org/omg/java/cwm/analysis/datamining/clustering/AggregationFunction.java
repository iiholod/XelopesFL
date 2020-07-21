package org.omg.java.cwm.analysis.datamining.clustering;

/** CWM Class
 * 
 * This specifies the type of the aggregation function.
 *  
 * @author Ivan Holod
 *
 */
public enum AggregationFunction {
	euclidian,
	squaredEuclidian,
	chebychev,
	cityBlock,
	minkovski,
	simpleMatching,
	jaccard,
	tanimoto,
	binarySimilarity
}
