package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by iihol on 02.03.2018.
 */
public abstract class DataMiningBlock extends MiningBlock {
    private MiningInputStream data;

    /**
     * Constructor of algorithm's  block
     *
     * @param settings - settings for build model
     */
    protected DataMiningBlock(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        if(data == null)
            throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Data set for the mining block "+ this +" is not initialized!");

        return execute(data, model);
    }

    protected abstract EMiningModel execute(MiningInputStream data, EMiningModel model) throws MiningException;

    public boolean isDataBlock(){
        return true;
    }

    public MiningInputStream getData() {
        return data;
    }

    public void setData(MiningInputStream data) {
        this.data = data;
    }

}
