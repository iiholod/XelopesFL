package org.eltech.ddm.classification.neuralnet.steps;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class TensorFlowBlock extends DataMiningBlock {
    /**
     * Constructor of algorithm's  block
     *
     * @param settings - settings for build model
     */
    protected TensorFlowBlock(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream data, EMiningModel model) throws MiningException {
        return null;
    }
}
