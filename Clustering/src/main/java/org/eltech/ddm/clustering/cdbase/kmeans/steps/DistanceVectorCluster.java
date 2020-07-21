package org.eltech.ddm.clustering.cdbase.kmeans.steps;

import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.KMeansMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.VectorCluster;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by iihol on 13.02.2018.
 */
public class DistanceVectorCluster extends MiningBlock {

    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public DistanceVectorCluster(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        int iVec = model.getCurrentVectorIndex();
        int iCluster = ((ClusteringMiningModel)model).getCurrentClusterIndex();
        VectorCluster vc = ((KMeansMiningModel)model).getVectorsCluster(iVec, iCluster);

        //System.out.println("Thread-" + Thread.currentThread().getName() + " vc  " + vc);
        vc.setDistance(Math.sqrt(vc.getDistance()));

        return model;
    }

}
