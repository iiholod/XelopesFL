package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.clustering.ClusteringAlgorithmSettings;

public class KMeansAlgorithmSettings extends ClusteringAlgorithmSettings {

	private final String TAG_MAX_NUMBER_CLUSTERS = "maxNumberOfIterations";
	
	private final String TAG_EPS = "eps";


	public KMeansAlgorithmSettings(){
		addTaggedValue(TAG_MAX_NUMBER_CLUSTERS, "MAX_VALUE", "int");
		addTaggedValue(TAG_EPS, "0.1", "double");
	}
	
	public double getEps() {
		String v = getTaggedValue(TAG_EPS);
		if(v == null) 
			return 1;
		else
			return Double.parseDouble(v);
	}

	public void setEps(double eps) {
		setTaggedValue(TAG_EPS, String.valueOf(eps));
	}

	/**
	 * @param numberOfIterations the numberOfIterations to attributes
	 */
	public void setMaxNumberOfIterations(int numberOfIterations) {
		setTaggedValue(TAG_MAX_NUMBER_CLUSTERS, String.valueOf(numberOfIterations));
	}

	/**
	 * @return the numberOfIterations
	 */
	public int getMaxNumberOfIterations() {
		String v = getTaggedValue(TAG_MAX_NUMBER_CLUSTERS);
		if((v == null) || (v.equals("MAX_VALUE")))
			return Integer.MAX_VALUE;
		else
			return Integer.parseInt(v);
	}
}
