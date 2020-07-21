package org.eltech.ddm.classification.naivebayes;

import org.eltech.ddm.classification.ClassificationMiningModelTest;
import org.eltech.ddm.classification.naivebayes.category.NaiveBayesModel;

import static org.junit.Assert.assertEquals;

public class NaiveBayesModelTest extends ClassificationMiningModelTest {

	
	public void verifyModel4WeatherNominal() throws Exception {
        // Verify builded model
		System.out.println(model);
		
		verifyModel(inputData, model);

		assertEquals(2, ((NaiveBayesModel)model).getOutput().size());
        assertEquals(5, ((NaiveBayesModel)model).getOutputTargetValueCount(1).getCount());
		assertEquals(9, ((NaiveBayesModel)model).getOutputTargetValueCount(0).getCount());

        assertEquals(2, ((NaiveBayesModel)model).getInputTargetValueCount(0, 0, 0).getCount());// outlook = sunny => play = yes
		assertEquals(3, ((NaiveBayesModel)model).getInputTargetValueCount(0, 0, 1).getCount());// outlook = sunny => play = no
	}

}
