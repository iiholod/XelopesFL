package org.eltech.ddm.clustering;

import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningFunction;

/**
 * CWM Class
 * 
 * A ClusteringFunctionSettings object is a subclass of MiningFunctionSettings that
 * supports features unique to clustering algorithms, such as self-organizing map and k-means.
 * 
 * @author Ivan Holod
 *
 */
public class ClusteringFunctionSettings extends EMiningFunctionSettings //implements ClusteringSettings 
{
	/**
	 * This attribute specifies the maximum number of clusters the clustering algorithm should
	 * generate.
	 */
	private final String TAG_MAX_NUMBER_CLUSTERS = "maxNumberOfClusters";
	
	/**
	 * This attribute specifies the minimum number of records (cases) that must be present in a
	 * cluster to establish a cluster. The default value is 1.
	 */
	private final String TAG_MIN_CLUSTER_SIZE = "minClusterSize";
	
	/**
	 * This specifies the type of the aggregation function.
	 */
	private final String TAG_AGGREGATION_FUNCTION = "aggregationFunction";

	// Fields added for JDMAPI
	/**
	 * maximum number of levels in a hierarchical model. 
	 * Non-hierarchical models have level attributes to 1.
	 */
	private final String TAG_MAX_LEVELS = "maxLevels";

	/**
	 * maximum number of cases per cluster for the model to be built
	 */
	private final String TAG_MAX_CLUSTER_SIZE = "maxClusterCaseCount";

	
	public ClusteringFunctionSettings(ELogicalData ld) {
		super(ld);
		addTaggedValue(TAG_MAX_NUMBER_CLUSTERS, "3", "int");
		addTaggedValue(TAG_MIN_CLUSTER_SIZE, "1", "int");
		addTaggedValue(TAG_AGGREGATION_FUNCTION, AggregationFunction.euclidian.name(), AggregationFunction.class.getName());
		addTaggedValue(TAG_MAX_LEVELS, "1", "int");
		addTaggedValue(TAG_MAX_CLUSTER_SIZE, "MAX_VALUE","int");

//		comparisonFunction = new HashMap<String, AttributeComparisonFunction>();
//		similarityMatrixMap = new HashMap<String, SimilarityMatrix>();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Returns the aggregation function to be used.
	 * @return
	 */
	public AggregationFunction getAggregationFunction() {
		String v = getTaggedValue(TAG_AGGREGATION_FUNCTION);
		if(v != null)
			return  AggregationFunction.valueOf(v);
		else
			return AggregationFunction.euclidian;
	}

	/**
	 * Returns the maximum number of cases per cluster for the model to be built. 
	 * @return
	 */
	public int getMaxClusterCaseCount() {
		String v = getTaggedValue(TAG_MAX_CLUSTER_SIZE);
		if((v == null) || (v.equals("MAX_VALUE")))
			return Integer.MAX_VALUE;
		else
			return Integer.parseInt(v);
	}

	/**
	 * Returns the maximum number of levels in a hierarchical model. Non-hierarchical models have level attributes to 1.
	 * Level must be between 1 and the maximum number of clusters. 
	 * @return
	 */
	public int getMaxLevels() {
		String v = getTaggedValue(TAG_MAX_LEVELS);
		if(v == null) 
			return 1;
		else
			return Integer.parseInt(v);
	}

	/**
	 * Returns the maximum number of clusters in the model to be built. 
	 * @return
	 */
	public int getMaxNumberOfClusters() {
		String v = getTaggedValue(TAG_MAX_NUMBER_CLUSTERS);
		if(v == null) 
			return 3;
		else
			return Integer.parseInt(v);
	}

	/**
	 * Returns the minimum number of cases per cluster for the model to be built
	 * @return
	 */
	public int getMinClusterCaseCount() {
		String v = getTaggedValue(TAG_MIN_CLUSTER_SIZE);
		if(v == null) 
			return 1;
		else
			return Integer.parseInt(v);
	}

	/**
	 * Sets the aggregation function to be used. 
	 * If null is specified, the default aggregation function is used
	 * @param function - The aggregation function to be used. 
	 */
	public void setAggregationFunction(AggregationFunction function) {
		setTaggedValue(TAG_AGGREGATION_FUNCTION, function.name()); 
		
	}

	/**
	 * Sets the maximum number of cases per cluster in the model to be built. 
	 * Combined with minimum cluster case count, this feature is used to control the size of the clusters in the model. 
	 * The value must be a positive integer. 
	 * @param maxCount - The maximum case count for clusters
	 */
	public void setMaxClusterCaseCount(int maxCount) {
		setTaggedValue(TAG_MAX_CLUSTER_SIZE, String.valueOf(maxCount)); 
	}

	/**
	 * Sets the maximum level for hierarchical clustering. The level must be a positive integer.
	 * @param numberOfLevels - The maximum level allowed in the model.
	 */
	public void setMaxLevels(int numberOfLevels) {
		setTaggedValue(TAG_MAX_LEVELS, String.valueOf(numberOfLevels)); 
	
	}

	/**
	 * Sets the maximum number of clusters in the model to be built. 
	 * The value must be a positive integer. 
	 * @param maxClusters - The maximum number of clusters to be found during model building.
	 */
	public void setMaxNumberOfClusters(int maxClusters) {
		setTaggedValue(TAG_MAX_NUMBER_CLUSTERS, String.valueOf(maxClusters)); 
	}

	/**
	 * Sets the minimum number of cases per cluster in the model to be built. If the value is 0, 
	 * it means there is no minimum cluster count. 
	 * Otherwise, the value must be a positive integer. 
	 * @param minCaseCount - The minimum cluster size in terms of case count. Any cluster must represent at least this many cases. 
	 */
	public void setMinClusterCaseCount(int minCaseCount) {
		setTaggedValue(TAG_MIN_CLUSTER_SIZE, String.valueOf(minCaseCount)); 
	}

	@Override
	public MiningFunction getMiningFunction() {
		// TODO Auto-generated method stub
		return MiningFunction.clustering;
	}


	// methods from JDMAPI
//	/**
//	 * Returns the attribute comparison function to be used
//	 * @param logicalAttributeName 
//	 * @return
//	 * @throws JDMException
//	 */
//	public AttributeComparisonFunction getAttributeComparisonFunction(String logicalAttributeName ) 
//																throws MiningException {
//		return comparisonFunction.get(logicalAttributeName);
//	}
//	
//	/**
//	 * Returns the similarity matrix to be used when the attribute comparison 
//	 * function is similarityMatrix.
//	 * @param logicalAttributeName - 
//	 * @return
//	 * @throws JDMException
//	 */
//	
//	public SimilarityMatrix getSimilarityMatrix(String logicalAttributeName)
//			throws MiningException {
//		return similarityMatrixMap.get(logicalAttributeName);
//	}
//	
//	/**
//	 * Sets the attribute comparison function to be used. 
//	 * Throws an exception on invalid combination of attribute type and comparison function. 
//	 * Valid combinations include absDiff, gaussSim for numerical attributes, 
//	 * delta, equal for categorical attributes, 
//	 * and table for categorical or discrete numerical attributes. 
//	 * @param logicalAttributeName - 
//	 * @param function - The comparison function to be used for the specified attribute.
//	 * @throws JDMException
//	 */
//	public void setAttributeComparisonFunction(String logicalAttributeName,
//			AttributeComparisonFunction function) throws MiningException {
//		comparisonFunction.put(logicalAttributeName,function);
//		
//	}
//	
//	/**
//	 * Sets the similarity matrix to be used. 
//	 * This method automatically changes the attribute comparison function to similarityMatrix
//	 * @param logicalAttributeName - 
//	 * @param matrix - The similarity matrix to be used for the specified attribute
//	 * @throws JDMException
//	 */
//	public void setSimilarityMatrix(String logicalAttributeName, SimilarityMatrix matrix)
//			throws MiningException {
//		similarityMatrixMap.put(logicalAttributeName, matrix);
//		
//	}
}
