package org.eltech.ddm.classification.naivebayes.continious.steps;

import org.eltech.ddm.classification.naivebayes.continious.ContinuousBayesModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class represent logic for separating training sample by
 * class value for further calculations steps
 *
 * @author Evgenii Titkov
 */
public class CalculateSumStep extends DataMiningBlock {

    private static final Logger LOGGER = Logger.getLogger(CalculateSumStep.class.getName());
    private double[] currentVector;

    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public CalculateSumStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    /**
     * Main execution method
     *
     * @param inputData - mining algorithm input data
     * @param model     - mining model
     * @return - mining model
     */
    @Override
    public EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        try {
            if (model.getCurrentAttributeIndex() == 0) {
                currentVector = inputData.getVector(model.getCurrentVectorIndex()).getValues();
            }
            ContinuousBayesModel algModel = (ContinuousBayesModel) model;
            algModel.putValue(model.getCurrentAttributeIndex(), array(currentVector), currentVector[currentVector.length - 1]);
            return algModel;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Exception occurred while parsing the record, but we intently just missing this record. " +
                    "Check the input file, it might contains syntax error which parser can't solve on it's own. ");
            return model;
        }

    }

    /**
     * Simple syntax sugar for generating array from the set of values
     *
     * @param args - passed arguments
     * @return - created array from passed args
     */
    private double[] array(double... args) {
        return Arrays.copyOfRange(args, 0, args.length - 1);
    }
}