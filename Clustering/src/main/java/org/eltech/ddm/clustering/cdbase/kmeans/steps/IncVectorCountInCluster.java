package org.eltech.ddm.clustering.cdbase.kmeans.steps;

import org.eltech.ddm.clustering.Cluster;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.KMeansMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.VectorAssignment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * @author iholod
 */
public class IncVectorCountInCluster extends MiningBlock{

    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public IncVectorCountInCluster(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }


    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {

        int iVec = model.getCurrentVectorIndex();
        VectorAssignment v = ((KMeansMiningModel)model).getVectorAssigment(iVec);
        int iCloseCluster = v.getIndexCluster();

        Cluster c = ((ClusteringMiningModel)model).getCluster(iCloseCluster);

        c.incVectorCount();
        v.setDistanceToCenter(Double.MAX_VALUE);
        //System.out.println("Thread-" + Thread.currentThread().getName() + " vector  " + iVec +" in cluster " + iCloseCluster);

        return model;
    }
}
