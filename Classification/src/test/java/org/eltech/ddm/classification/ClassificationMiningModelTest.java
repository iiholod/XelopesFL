package org.eltech.ddm.classification;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategory;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;

import static org.junit.Assert.fail;


public class ClassificationMiningModelTest {
	protected MiningInputStream inputData;
	protected ClassificationFunctionSettings  miningSettings;
	protected ClassificationMiningModel  model;

	// ==== Methods for data attributes Weather Nominal ===============
	protected void setInputData4WeatherNominal() throws MiningException {
		// Load input data
		inputData = new MiningArffStream("..\\data\\arff\\weather-nominal.arff");
	}
	
	
	protected void setMiningSettings4WeatherNominal(EMiningAlgorithmSettings algorithmSettings) throws MiningException {
		ELogicalData logicalData = inputData.getLogicalData();
		ELogicalAttribute targetAttribute = logicalData.getAttribute("play");
		
		//Create settings for classification
		miningSettings = new ClassificationFunctionSettings(logicalData);
		miningSettings.setTarget(targetAttribute);
		miningSettings.setAlgorithmSettings(algorithmSettings);
   		miningSettings.verify();
		
	}


	
	// ==== Methods for data attributes Weather Nominal ===============
	protected double verifyModel(MiningInputStream inputData, ClassificationMiningModel model) {
		try
		{
			//System.out.println(model);

           // Verify builded model
			ELogicalAttribute targetAttribute = model.getTarget();
			inputData.reset();
            double wrong = 0;
            int i = 0;
            System.out.println(" Prediction:");
            MiningVector vector = inputData.next();
            while (vector != null) {
                    // Classify each vector
                    double predicted    = model.apply(vector);

                    double realTarCat   = vector.getValue( targetAttribute.getName() );
                    ECategory tarCat     = targetAttribute.getCategoricalProperties().getCategory((int)realTarCat);
                    ECategory predTarCat = targetAttribute.getCategoricalProperties().getCategory((int)predicted);
                    i++;
                    //System.out.println(" " + i +": " + vector.toVector() + " -> " + predTarCat.getName());

                    if (! predTarCat.equals( tarCat) )
                            wrong = wrong + 1;
                    vector = inputData.next();
            };
            double rate = (100.0 - ((double) wrong / i)*100.0);
            System.out.println("\n classification rate = " +  rate);

            return rate;

		}
		catch( Exception ex )
		{
 		  ex.printStackTrace();
 		  fail();
		}

		return 0;
	}

}
