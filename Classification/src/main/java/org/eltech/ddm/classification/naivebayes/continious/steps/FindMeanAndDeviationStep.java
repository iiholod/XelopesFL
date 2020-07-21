package org.eltech.ddm.classification.naivebayes.continious.steps;

import org.eltech.ddm.classification.naivebayes.continious.ContinuousBayesModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.stream.IntStream;


/**
 * Class represents cycle step for Naive Bayes Classifier algorithm.
 * This steps goes through class value range and for each class values
 * executes the following logic:
 * <p>
 * 1) Calculating mean array for attributes for a current cycle step
 * class value
 * 2) Calculating deviation array for all attributes for a current cycle step
 * class value
 * <p>
 *
 * @author Evgenii Titkov
 * @see ContinuousBayesModel
 * @see EMiningModel
 */
public class FindMeanAndDeviationStep extends MiningBlock {

    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public FindMeanAndDeviationStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }


    /**
     * Main loop body execution method
     *
     * @param raw       - mining model for algorithm
     * @return - {<code>true</code>} - condition success , {<code>false</code>} - otherwise
     */
    @Override
    public EMiningModel execute( EMiningModel raw) {
        ContinuousBayesModel model = (ContinuousBayesModel) raw;
        double[][] modelValue = model.getCurrentModelValue();
        IntStream.range(0, modelValue.length).forEach(index -> {
            double[] values = calculateMeanAndDeviation(modelValue[index][0], modelValue[index][1], model.getClassLength(model.getCurrentClassValue()));
            modelValue[index] = values;
        });
        return model;
    }

    /**
     * Calculate mean and deviation in 1 pass by sub-sum from previous steps
     *
     * @param sum1   - simple attr value sum
     * @param sum2   - quadratic attr sum
     * @param length - sample data length
     * @return - 2-values array with the next structure [mean,dev]
     */
    private double[] calculateMeanAndDeviation(double sum1, double sum2, int length) {
        double mean = sum1 / length;
        double deviation = Math.sqrt((sum2 - length * mean * mean) / (length - 1));
        return array(mean, deviation);
    }


    /**
     * Syntax sugar
     *
     * @param values - multiple items
     * @return - array of values
     */
    private double[] array(double... values) {
        return values;
    }

    @Override
    public boolean isDataBlock() {
        return false;
    }
}
