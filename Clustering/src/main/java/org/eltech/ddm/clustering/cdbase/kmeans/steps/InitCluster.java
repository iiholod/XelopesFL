package org.eltech.ddm.clustering.cdbase.kmeans.steps;

import org.eltech.ddm.clustering.Cluster;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by iihol on 16.02.2018.
 */
public class InitCluster extends MiningBlock {
    public InitCluster(EMiningFunctionSettings miningSettings) throws MiningException {
        super(miningSettings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        int iCluster = ((ClusteringMiningModel)model).getCurrentClusterIndex();
        Cluster c = ((ClusteringMiningModel)model).getCluster(iCluster);

        c.setVectorCount(0);

        return model;
    }
}
