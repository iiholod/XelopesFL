package org.eltech.ddm.classification.ruleset.onerule;


import org.eltech.ddm.classification.ruleset.RuleSetModelTest;
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;

import static org.junit.Assert.fail;

public class OneRuleCountAlgorithmVerParallelTest extends RuleSetModelTest {

	private final int NUMBER_HANDLERS = 2;

	protected MiningAlgorithm algorithm;

	protected EMiningAlgorithmSettings algorithmSettings;

	@Before
	public void setUp() throws Exception {
		algorithmSettings = new EMiningAlgorithmSettings();
		// Create and tuning algorithm settings
		algorithmSettings.setName(OneRuleCountAlgorithm.class.getSimpleName());
		algorithmSettings.setClassname(OneRuleCountAlgorithm.class.getName());
		algorithmSettings.setAlgorithm("1R");
	}

	@org.junit.Test
	public void test4WeatherNominal() {
		try {
			setInputData4WeatherNominal();
			setMiningSettings4WeatherNominal(algorithmSettings);

			EMiningBuildTask buildTask = createBuidTask();

			model = (OneRuleMiningModel) buildTask.execute();

			verifyModel4WeatherNominal();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	private EMiningBuildTask createBuidTask() throws MiningException{
		MiningAlgorithm algorithm = new OneRuleCountAlgorithm(miningSettings);
		ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(NUMBER_HANDLERS, inputData);

		EMiningBuildTask buildTask = new EMiningBuildTask();
		buildTask.setMiningAlgorithm(algorithm);
		buildTask.setMiningSettings(miningSettings);
		buildTask.setExecutionEnvironment(environment);

		return buildTask;
	}



}
