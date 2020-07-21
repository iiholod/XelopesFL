package org.eltech.ddm.clustering.cdbase.kmeans.steps;

import org.eltech.ddm.clustering.ClusterSet;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.KMeansAlgorithmSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningLoop;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class WhileChangClustersLoop extends MiningLoop{

    final private int maxNumberOfIterations;

    public WhileChangClustersLoop(EMiningFunctionSettings settings, MiningBlock... steps) throws MiningException {
        super(settings, steps);
        maxNumberOfIterations = ((KMeansAlgorithmSettings) this.functionSettings.getAlgorithmSettings()).getMaxNumberOfIterations();
    }


    @Override
    protected boolean conditionLoop(EMiningModel model) throws MiningException {
        ClusterSet cs = ((ClusteringMiningModel)model).getClusterSet();
        return (cs.isChanged() && cs.getNumberOfIterations() < maxNumberOfIterations);
    }

    @Override
    protected EMiningModel initLoop(EMiningModel model) throws MiningException {
        ClusterSet cs = ((ClusteringMiningModel)model).getClusterSet();
        cs.setNumberOfIterations(0);
        cs.setChanged(true);

        return model;
    }

    @Override
    protected EMiningModel afterIteration(EMiningModel model) throws MiningException {
        ClusterSet cs = ((ClusteringMiningModel)model).getClusterSet();
        cs.incNumberOfIterations();
        return model;
    }

    @Override
    protected EMiningModel beforeIteration(EMiningModel model) throws MiningException {
        ClusterSet cs = ((ClusteringMiningModel)model).getClusterSet();
        cs.setChanged(false);

        return model;
    }


}
