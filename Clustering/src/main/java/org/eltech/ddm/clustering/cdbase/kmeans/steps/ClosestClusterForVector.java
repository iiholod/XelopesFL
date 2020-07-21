package org.eltech.ddm.clustering.cdbase.kmeans.steps;

import org.eltech.ddm.clustering.ClusteringFunctionSettings;
import org.eltech.ddm.clustering.Distance;
import org.eltech.ddm.clustering.cdbase.CDBasedClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.KMeansMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.VectorAssignment;
import org.eltech.ddm.clustering.cdbase.kmeans.VectorCluster;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class ClosestClusterForVector extends MiningBlock {

	protected final Distance distance;

	public ClosestClusterForVector(EMiningFunctionSettings settings) throws MiningException {
		super(settings);

		distance = new Distance(((ClusteringFunctionSettings) settings).getAggregationFunction());
	}


	@Override
	protected EMiningModel execute(EMiningModel model) throws MiningException {
		int iVec = model.getCurrentVectorIndex();
		int iCluster = model.getCurrentElementIndex(EMiningModel.index(CDBasedClusteringMiningModel.CLUSTERS));

		VectorAssignment v = ((KMeansMiningModel)model).getVectorAssigment(iVec);
		VectorCluster vc = ((KMeansMiningModel)model).getVectorsCluster(iVec, iCluster);

		if(v.getDistanceToCenter() > vc.getDistance()){
			v.setIndexCluster(iCluster);
			v.setDistanceToCenter(vc.getDistance());
			//System.out.println("Thread-" + Thread.currentThread().getName() + " vector  " + iVec +" in cluster " + iCluster + " d=" + v.getDistanceToCenter());
		}
		//System.out.println("Thread-" + Thread.currentThread().getName() + " v=" + v);
		vc.setDistance(0);

		return model;
	}

}
