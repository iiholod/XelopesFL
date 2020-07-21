package org.eltech.ddm.clustering.cdbase.kmeans.steps;

import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.KMeansMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.VectorAssignment;
import org.eltech.ddm.clustering.cdbase.kmeans.VectorCluster;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * @author iholod
 */
public class AccumulateDistanceVectorCluster extends DataMiningBlock {

    public AccumulateDistanceVectorCluster(EMiningFunctionSettings settings)
            throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        int iCurrentVector = model.getCurrentVectorIndex();
        int iAttr = model.getCurrentAttributeIndex();
        int iCurrentCluster = ((ClusteringMiningModel)model).getCurrentClusterIndex();

        MiningVector mv = inputData.getVector(iCurrentVector);
        double cv = mv.getValue(iAttr);
        double cc = ((ClusteringMiningModel)model).getClusterCenterCoordinate(iCurrentCluster, iAttr).getValue();

        VectorAssignment vec = ((KMeansMiningModel)model).getVectorAssigment(iCurrentVector);
        if(vec ==null){
            vec = new VectorAssignment("v" + iCurrentVector);
            ((KMeansMiningModel)model).addVectorAssigment(vec);
        }


        VectorCluster vc = ((KMeansMiningModel)model).getVectorsCluster(iCurrentVector, iCurrentCluster);
        if(vc ==null){
            vc = new VectorCluster("v" +iCurrentVector +":c" + iCurrentCluster);
            ((KMeansMiningModel)model).addVectorsCluster(iCurrentVector, vc);
        }

        double v = Math.abs(cv-cc);
        vc.addDistance(v*v);
        //System.out.println("Thread-" + Thread.currentThread().getName() + " vc = " + vc);


        return model;
    }

}
