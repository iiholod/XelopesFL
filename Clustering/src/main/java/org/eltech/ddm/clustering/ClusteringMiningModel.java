package org.eltech.ddm.clustering;

import org.eltech.ddm.clustering.cdbase.Coordinate;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public abstract class ClusteringMiningModel extends EMiningModel//ClusteringModel
{

	static public final int CLUSTERS = 1;
	static public final int[] INDEX_CLUSTERS = {1};

	public ClusteringMiningModel(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		sets.add(CLUSTERS, new ClusterSet("Clusters") {
			@Override
			public void merge(List<MiningModelElement> elements) throws MiningException {

			}
		}) ;

	}


	@Override
	public void initModel() throws MiningException {
		int numClusters = ((ClusteringFunctionSettings)settings).getMaxNumberOfClusters();

		for(int i = 0; i < numClusters; i++){
			addElement(index(CLUSTERS), new Cluster("Cluster "+i));
		}
	}

    public Coordinate getClusterCenterCoordinate(int iCurrentCluster, int iAttr) throws MiningException {
		return  (Coordinate)getElement(index(ClusteringMiningModel.CLUSTERS, iCurrentCluster, iAttr));

	}

	public int getCurrentClusterIndex() throws MiningException {
		return getCurrentElementIndex(EMiningModel.index(ClusteringMiningModel.CLUSTERS));
	}

	public Cluster getCluster(int iCluster) throws MiningException {
		return ((Cluster)getElement(index(CLUSTERS, iCluster)));

	}

	public ClusterSet getClusterSet() throws MiningException {
		return (ClusterSet) getElement(INDEX_CLUSTERS);
	}
}