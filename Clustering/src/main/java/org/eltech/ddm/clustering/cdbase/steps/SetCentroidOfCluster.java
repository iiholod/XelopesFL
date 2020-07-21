package org.eltech.ddm.clustering.cdbase.steps;

import org.eltech.ddm.clustering.Cluster;
import org.eltech.ddm.clustering.ClusterSet;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.Coordinate;
import org.eltech.ddm.clustering.cdbase.kmeans.KMeansAlgorithmSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


public class SetCentroidOfCluster extends MiningBlock {
	private final double eps;

	public SetCentroidOfCluster(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		eps = ((KMeansAlgorithmSettings)((EMiningFunctionSettings)settings).getAlgorithmSettings()).getEps();

	}

	@Override
	protected EMiningModel execute(EMiningModel model) throws MiningException {
		int iAttr = model.getCurrentAttributeIndex();
		int iCluster = ((ClusteringMiningModel)model).getCurrentClusterIndex();

		Coordinate cc = ((ClusteringMiningModel)model).getClusterCenterCoordinate(iCluster, iAttr);
		Cluster c = ((ClusteringMiningModel)model).getCluster(iCluster);
		ClusterSet cs = ((ClusteringMiningModel)model).getClusterSet();

		double oldv = cc.getValue();
		double v = cc.getMass()/c.getVectorCount();
		if(Math.abs(oldv - v) > eps)
			cs.setChanged(true);

		cc.setValue(v);
		//System.out.println("Thread-" + Thread.currentThread().getName() + "Cluster " + iCluster + "coord=" + cc.getValue());
		cc.setMass(0);
		return model;
	}

}