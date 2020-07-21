package org.eltech.ddm.classification.naivebayes.continious.steps;

import org.eltech.ddm.classification.naivebayes.continious.ContinuousBayesModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningLoop;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Class represent cycle which contains step to calculate
 * mean and deviation array.
 *
 * @see FindMeanAndDeviationStep
 *
 * @author Evgenii Titkov
 */
public class FindMeanAndDeviationCycle extends MiningLoop {


    public FindMeanAndDeviationCycle(EMiningFunctionSettings settings, MiningBlock... blocks) throws MiningException {
        super(settings, blocks);
    }

    /**
     * Loop initiation method. Initiates range of execution for loop
     *
     * @param model     - model for algorithm
     * @return - model for algorithm
     */
    @Override
    protected EMiningModel initLoop(EMiningModel model) {
        ContinuousBayesModel bayesModel = (ContinuousBayesModel) model;
        bayesModel.initIterator();
        return model;
    }

    /**
     * Provides condition implementation for loop. In current version
     * there is just a basic check that current index is less than the
     * max one.
     *
     * @param model     - mining model for algorithm
     * @return - {<code>true</code>} - condition success , {<code>false</code>} - otherwise
     */
    @Override
    protected boolean conditionLoop(EMiningModel model) {
        ContinuousBayesModel bayesModel = (ContinuousBayesModel) model;
        return bayesModel.hasNext();
    }

    @Override
    protected EMiningModel beforeIteration(EMiningModel model) {
        ContinuousBayesModel bayesModel = (ContinuousBayesModel) model;
        bayesModel.next();
        return model;
    }

    /**
     * Method executes after step iteration and increases
     * current loop index value
     *
     * @param model     - mining model for algorithm
     * @return - model for algorithm
     */
    @Override
    protected EMiningModel afterIteration(EMiningModel model) {
        return model;
    }

    @Override
    public boolean isDataBlock() {
        return false;
    }

}
