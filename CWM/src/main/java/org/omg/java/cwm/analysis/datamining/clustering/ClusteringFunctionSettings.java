package org.omg.java.cwm.analysis.datamining.clustering;

import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;

/**
 * CWM Class
 * 
 * A ClusteringFunctionSettings object is a subclass of MiningFunctionSettings that
 * supports features unique to clustering algorithms, such as self-organizing map and k-means.
 * 
 * @author Ivan Holod
 *
 */
public class ClusteringFunctionSettings extends MiningFunctionSettings {

	/**
	 * This attribute specifies the maximum number of clusters the clustering algorithm should
	 * generate.
	 */
	private int maxNumberOfClusters;
	
	/**
	 * This attribute specifies the minimum number of records (cases) that must be present in a
	 * cluster to establish a cluster. The default value is 1.
	 */
	private int minClusterSize = 1;
	
	/**
	 * This specifies the type of the aggregation function.
	 */
	private AggregationFunction aggregationFunction;
}
