package org.eltech.ddm.clustering.cdbase;

import org.eltech.ddm.clustering.Cluster;
import org.eltech.ddm.clustering.ClusterSet;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.ClusteringMiningModelTest;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.junit.Assert;

public class CDBaseModelTest extends ClusteringMiningModelTest {

    protected void verifyModel4Iris(ClusteringMiningModel model) throws MiningException {
        // Show the clusters:
        showClusters((CDBasedClusteringMiningModel) model);

        Assert.assertEquals(3, model.getElement(CDBasedClusteringMiningModel.INDEX_CLUSTERS).size());
        Assert.assertTrue(((Cluster)model.getElement(EMiningModel.index(CDBasedClusteringMiningModel.CLUSTERS, 0))).getVectorCount() > 35);
        Assert.assertTrue(((Cluster)model.getElement(EMiningModel.index(CDBasedClusteringMiningModel.CLUSTERS, 1))).getVectorCount() > 35);
        Assert.assertTrue(((Cluster)model.getElement(EMiningModel.index(CDBasedClusteringMiningModel.CLUSTERS, 2))).getVectorCount() > 35);

    }

    protected void verifyModel4AzureIris(ClusteringMiningModel model) throws MiningException {
        // Show the clusters:
        showClusters((CDBasedClusteringMiningModel) model);

//        Assert.assertEquals(2, ((CDBasedClusteringMiningModel) model).getNumberOfClusters());
//        Assert.assertTrue(((CDBasedClusteringMiningModel) model).getClusters()[0].getVectorCount() > 30);
//        Assert.assertTrue(((CDBasedClusteringMiningModel) model).getClusters()[1].getVectorCount() > 30);

    }

    protected void verifyModel4TelescopeData(ClusteringMiningModel model) throws MiningException {
        // Show the clusters:
        showClusters((CDBasedClusteringMiningModel) model);

//		Assert.assertEquals(2, ((CDBasedClusteringMiningModel)model).getNumberOfClusters());
//		Assert.assertTrue(((CDBasedClusteringMiningModel)model).getClusters()[0].getVectorCount() > 30);
//		Assert.assertTrue(((CDBasedClusteringMiningModel)model).getClusters()[1].getVectorCount() > 30);

    }

    protected void verifyModel4MovieRatings(ClusteringMiningModel model) throws MiningException {
        // Show the clusters:
        showClusters((CDBasedClusteringMiningModel) model);

//		Assert.assertEquals(2, ((CDBasedClusteringMiningModel)model).getNumberOfClusters());
//		Assert.assertTrue(((CDBasedClusteringMiningModel)model).getClusters()[0].getVectorCount() > 30);
//		Assert.assertTrue(((CDBasedClusteringMiningModel)model).getClusters()[1].getVectorCount() > 30);

    }

    protected void verifyModel4FlightOnTimePerformance(ClusteringMiningModel model) throws MiningException {
        // Show the clusters:
        showClusters((CDBasedClusteringMiningModel) model);

//		Assert.assertEquals(2, ((CDBasedClusteringMiningModel)model).getNumberOfClusters());
//		Assert.assertTrue(((CDBasedClusteringMiningModel)model).getClusters()[0].getVectorCount() > 30);
//		Assert.assertTrue(((CDBasedClusteringMiningModel)model).getClusters()[1].getVectorCount() > 30);

    }


    protected void verifyModel4FlightDelaysData(ClusteringMiningModel model) throws MiningException {
        // Show the clusters:
        showClusters((CDBasedClusteringMiningModel) model);

//		Assert.assertEquals(2, ((CDBasedClusteringMiningModel)model).getNumberOfClusters());
//		Assert.assertTrue(((CDBasedClusteringMiningModel)model).getClusters()[0].getVectorCount() > 30);
//		Assert.assertTrue(((CDBasedClusteringMiningModel)model).getClusters()[1].getVectorCount() > 30);

    }

    public void showClusters(CDBasedClusteringMiningModel clustModel)
            throws MiningException {

        System.out.println("number of iterations: " + ((ClusterSet)model.getElement(CDBasedClusteringMiningModel.INDEX_CLUSTERS)).getNumberOfIterations());
//        MiningModelElement set =  model.getElement(KMeansMiningModel.INDEX_CLUSTER_VECTOR);
//        for(int i = 0; i < set.size(); i++){
//            MiningModelElement elem = set.getElement(i);
//            System.out.println(elem);
//        }
        super.showClusters(clustModel);
    }


}
