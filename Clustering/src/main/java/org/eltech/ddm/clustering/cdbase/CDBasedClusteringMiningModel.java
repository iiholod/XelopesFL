package org.eltech.ddm.clustering.cdbase;

import org.eltech.ddm.clustering.ClusteringFunctionSettings;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;


public class CDBasedClusteringMiningModel extends ClusteringMiningModel{



	public CDBasedClusteringMiningModel(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
	}

	@Override
	public void initModel() throws MiningException {
		super.initModel();
		int numClusters = ((ClusteringFunctionSettings)settings).getMaxNumberOfClusters();
		int numAttributes = ((ClusteringFunctionSettings) settings).getLogicalData().getAttributesNumber();

		for(int i = 0; i < numClusters; i++){
			for(int k =0; k < numAttributes; k++){
				addElement(index(CLUSTERS, i), new Coordinate("a"+k));
			}
		}
	}

	@Override
	public Object clone() {
		CDBasedClusteringMiningModel o = null;
		o = (CDBasedClusteringMiningModel)super.clone();

		return o;
	}

}