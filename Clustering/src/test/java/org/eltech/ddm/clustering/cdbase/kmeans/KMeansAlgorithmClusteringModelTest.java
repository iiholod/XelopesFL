//package org.eltech.ddm.clustering.cdbase.kmeans;
//
//import org.eltech.ddm.clustering.AggregationFunction;
//import org.eltech.ddm.clustering.ClusteringMiningModel;
//import org.eltech.ddm.clustering.cdbase.CDBaseModelTest;
//import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
//import org.eltech.ddm.miningcore.MiningException;
//import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
//import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.fail;
//
//public class KMeansAlgorithmClusteringModelTest extends CDBaseModelTest{
//
//	protected KMeansAlgorithmSettings miningAlgorithmSettings;
//	protected MiningAlgorithm algorithm;
//
//	@Before
//	public void setUp() throws Exception {
//
//		// Create mining algorithm settings
//		miningAlgorithmSettings = new KMeansAlgorithmSettings();
//		miningAlgorithmSettings.setAlgorithm("KMeans");
//		miningAlgorithmSettings.setMaxNumberOfIterations(50);
//		miningAlgorithmSettings.setEps(0.01);
//	}
//
//	@Test
//	public void test4Iris() {
//
//		try {
//			setInputData4Iris();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(3);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//
//			MiningAlgorithm algorithm = new KMeansAlgorithm(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(inputData);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4Iris(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
//
//
////	@Test
////	public void test4AzureIris() {
////		// Open data source and get metadata:
////
////		try {
////			setInputData4AzureIris();
////			setMiningSettings4Iris(miningAlgorithmSettings);
////
////			// Assign settings:
////			miningSettings.setMaxNumberOfClusters(2);
////			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
////			miningSettings.verify();
////
////			// Create algorithm object with default values:
////			algorithm = new KMeansAlgorithm(miningSettings);
////
////			//algorithm.verify();
////
////			// Build the mining model:
////			model = (ClusteringMiningModel) algorithm.buildModel(inputData);
////
////			verifyModel4AzureIris(model);
////
////		} catch (MiningException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////
////	@Test
////	public void test4TelescopeData() {
////		// Open data source and get metadata:
////
////		try {
////			setInputData4TelescopeData();
////			setMiningSettings4Iris(miningAlgorithmSettings);
////
////			// Assign settings:
////			miningSettings.setMaxNumberOfClusters(2);
////			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
////			miningSettings.verify();
////
////			// Create algorithm object with default values:
////			algorithm = new KMeansAlgorithm(miningSettings);
////
////			//algorithm.verify();
////
////			// Build the mining model:
////			model = (ClusteringMiningModel) algorithm.buildModel(inputData);
////
////			verifyModel4TelescopeData(model);
////
////		} catch (MiningException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
////
////	@Test
////	public void test4BreastCancerInfo() {
////		// Open data source and get metadata:
////
////		try {
////			setInputData4BreastCancerInfo();
////			setMiningSettings4Iris(miningAlgorithmSettings);
////
////			// Assign settings:
////			miningSettings.setMaxNumberOfClusters(2);
////			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
////			miningSettings.verify();
////
////			// Create algorithm object with default values:
////			algorithm = new KMeansAlgorithm(miningSettings);
////
////			//algorithm.verify();
////
////			// Build the mining model:
////			model = (ClusteringMiningModel) algorithm.buildModel(inputData);
////
////			verifyModel4TelescopeData(model);
////
////		} catch (MiningException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
//
//
//}
