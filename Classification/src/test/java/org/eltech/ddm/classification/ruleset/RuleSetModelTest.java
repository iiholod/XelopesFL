package org.eltech.ddm.classification.ruleset;

import org.eltech.ddm.classification.ClassificationMiningModelTest;
import org.eltech.ddm.miningcore.MiningException;
import org.junit.Assert;

public class RuleSetModelTest extends ClassificationMiningModelTest{

	/**
	 * result: if [outlook = 0.0] then no;
	 *         if [outlook = 1.0] then yes;
	 *         if [outlook = 2.0] then yes;
	 * @throws MiningException
	 */
	protected void verifyModel4WeatherNominal() throws MiningException {
        // Verify builded model
		System.out.println(model);
		
        verifyModel(inputData, model);
        
		Assert.assertEquals(3, ((RuleSetModel)model).getRuleSet().size());
		Assert.assertEquals(10, ((RuleSetModel)model).getRuleSet().getNumberCorrectVectors());

//		Assert.assertEquals("outlook equals 0.0", model.getProperty(EMiningModel.index(RuleSetModel.RULE_SET[0], 0), SimpleRule.ID));
//		Assert.assertEquals("outlook equals 1.0", model.getProperty(EMiningModel.index(RuleSetModel.RULE_SET[0], 1), SimpleRule.ID));
//		Assert.assertEquals("outlook equals 2.0", model.getProperty(EMiningModel.index(RuleSetModel.RULE_SET[0], 2), SimpleRule.ID));
 	}
	
	
}
