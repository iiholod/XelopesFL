package org.eltech.ddm.classification.naivebayes;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm;
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class NaiveBayesAlgorithmTest extends NaiveBayesModelTest {

	protected MiningAlgorithm algorithm;

	protected EMiningAlgorithmSettings algorithmSettings;
	
	@Before
	public void setUp() throws Exception {

		// Create and tuning algorithm settings
		algorithmSettings = new EMiningAlgorithmSettings();
		algorithmSettings.setName("Naive Bayes");
		algorithmSettings.setClassname("org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm");

	}


	@Test
	public void test4WeatherNominal() {
		try {

			setInputData4WeatherNominal();
			setMiningSettings4WeatherNominal(algorithmSettings);

			EMiningBuildTask buildTask = createBuildTask();

			model = (ClassificationMiningModel) buildTask.execute();

			verifyModel4WeatherNominal();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

/*	@Test
	public void test4Iris() {
		try {

			setInputData4Iris();
			setMiningSettings4Iris(algorithmSettings);

			EMiningBuildTask buildTask = createBuildTask();

			model = (ClassificationMiningModel) buildTask.execute();

			verifyModel4Iris();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}*/

	private EMiningBuildTask createBuildTask() throws MiningException {
		MiningAlgorithm algorithm = new NaiveBayesAlgorithm(miningSettings);
		ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(inputData);

		EMiningBuildTask buildTask = new EMiningBuildTask();
		buildTask.setMiningAlgorithm(algorithm);
		buildTask.setMiningSettings(miningSettings);
		buildTask.setExecutionEnvironment(environment);

		return buildTask;
	}
	
}
