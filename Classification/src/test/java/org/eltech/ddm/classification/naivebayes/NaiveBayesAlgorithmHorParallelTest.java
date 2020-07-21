package org.eltech.ddm.classification.naivebayes;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.classification.naivebayes.category.NaiveBayesAlgorithm;
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;

import static org.junit.Assert.fail;

public class NaiveBayesAlgorithmHorParallelTest extends NaiveBayesModelTest {

	private final int NUMBER_HANDLERS = 2;

	protected EMiningAlgorithmSettings algorithmSettings;

	@Before
	public void setUp() throws Exception {

		algorithmSettings = new EMiningAlgorithmSettings();
		// Create and tuning algorithm settings
		algorithmSettings.setName(NaiveBayesAlgorithm.class.getSimpleName());
		algorithmSettings.setClassname(NaiveBayesAlgorithm.class.getName());
		algorithmSettings.setAlgorithm("Naive Bayes");
	}

	@org.junit.Test
	public void test4WeatherNominal() {
		try {
			setInputData4WeatherNominal();
			setMiningSettings4WeatherNominal(algorithmSettings);

			EMiningBuildTask buildTask = createBuidTask();

			model = (ClassificationMiningModel) buildTask.execute();

			verifyModel4WeatherNominal();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	private EMiningBuildTask createBuidTask() throws MiningException{
		MiningAlgorithm algorithm = new NaiveBayesAlgorithm(miningSettings);
		ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(NUMBER_HANDLERS, inputData);

		EMiningBuildTask buildTask = new EMiningBuildTask();
		buildTask.setMiningAlgorithm(algorithm);
		buildTask.setMiningSettings(miningSettings);
		buildTask.setExecutionEnvironment(environment);

		return buildTask;
	}

}
