package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.clustering.AggregationFunction;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.CDBaseModelTest;
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class KMeansAlgorithmVerParallelTest extends CDBaseModelTest{

	private final int NUMBER_HANDLERS = 4;
	
	protected KMeansAlgorithmSettings miningAlgorithmSettings;
	protected MiningAlgorithm algorithm;
	
	@Before
	public void setUp() throws Exception {
		// Create mining algorithm settings
		miningAlgorithmSettings = new KMeansAlgorithmSettings();
		miningAlgorithmSettings.setAlgorithm("KMeans");
		miningAlgorithmSettings.setMaxNumberOfIterations(50);
		miningAlgorithmSettings.setEps(0.05);
	}

	@Test
	public void test4Iris() {

		try {
			setInputData4Iris();
			setMiningSettings4Iris(miningAlgorithmSettings);

			// Assign settings:
			miningSettings.setMaxNumberOfClusters(3);
			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
			miningSettings.verify();
			
			MiningAlgorithm algorithm = new KMeansAlgorithm(miningSettings);
			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(NUMBER_HANDLERS, inputData);

			EMiningBuildTask buildTask = new EMiningBuildTask();
			buildTask.setMiningAlgorithm(algorithm);
			buildTask.setMiningSettings(miningSettings);
			buildTask.setExecutionEnvironment(environment);
			model = (ClusteringMiningModel) buildTask.execute();
			
			verifyModel4Iris(model);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

//	@Test
//	public void test4TimeSeries() {
//
//		try {
//			setInputData4TimeSeries();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(3);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
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
//	@Test
//	public void test4AzureIris() {
//
//		try {
//			setInputData4AzureIris();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(2);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4AzureIris(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
//
//	@Test
//	public void test4AzureWeather() {
//
//		try {
//			setInputData4AzureWeather();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(2);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4AzureIris(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
//
//
//
//	@Test
//	public void test4TelescopeData() {
//
//		try {
//			setInputData4TelescopeData();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(2);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4TelescopeData(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
//
//	@Test
//	public void test4BreastCancerInfo() {
//
//		try {
//			setInputData4BreastCancerInfo();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(2);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4TelescopeData(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
// 	}
//
//
//	@Test
//	public void test4BreastCancerFeatures() {
//
//		try {
//			setInputData4BreastCancerFeatures();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(2);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4TelescopeData(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
//
//
//
//	@Test
//	public void test4MovieRatings() {
//
//		try {
//			setInputData4MovieRatings();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(2);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//			//miningAlgorithmSettings.setMaxNumberOfIterations(2);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4MovieRatings(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
//
//	@Test
//	public void test4FlightOnTimePerformance() {
//
//		try {
//			setInputData4FlightOnTimePerformance();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(2);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//			//miningAlgorithmSettings.setMaxNumberOfIterations(2);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4FlightOnTimePerformance(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}
//
//	@Test
//	public void test4FlightDelaysData() {
//
//		try {
//			setInputData4FlightDelaysData();
//			setMiningSettings4Iris(miningAlgorithmSettings);
//
//			// Assign settings:
//			miningSettings.setMaxNumberOfClusters(2);
//			miningSettings.setAggregationFunction(AggregationFunction.euclidian);
//			miningSettings.verify();
//
//			miningAlgorithmSettings.setDataSplitType(DataSplitType.block);
//			miningAlgorithmSettings.setDataProcessingStrategy(DataProcessingStrategy.SeparatedDataSet );
//			miningAlgorithmSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
//			//miningAlgorithmSettings.setMaxNumberOfIterations(2);
//			miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
//
//			ExecutionEnvironmentSettings executionSettings = new ThreadSettings();
//			executionSettings.setNumberHandlers(NUMBER_HANDLERS);
//			executionSettings.setDataSet(inputData);
//
//			MiningAlgorithm algorithm = new KMeansAlgorithmHorParallel(miningSettings);
//			ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(executionSettings, algorithm);
//			miningAlgorithmSettings.setEnvironment(environment);
//
//			EMiningBuildTask buildTask = new EMiningBuildTask();
//			buildTask.setInputStream(inputData);
//			buildTask.setMiningAlgorithm(algorithm);
//			buildTask.setMiningSettings(miningSettings);
//			buildTask.setExecutionEnvironment(environment);
//			model = (ClusteringMiningModel) buildTask.execute();
//
//			verifyModel4TelescopeData(model);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//
//	}



}
