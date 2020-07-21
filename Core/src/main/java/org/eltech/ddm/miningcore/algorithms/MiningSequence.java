package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MiningSequence extends MiningBlock implements Serializable {

    protected List<MiningBlock> sequence;

    public MiningSequence(){

    }

    public MiningSequence(EMiningFunctionSettings settings, MiningBlock... blocks) throws MiningException {
        super(settings);
        sequence = new ArrayList<>();
        for (MiningBlock block : blocks)
            sequence.add(block);
    }

    public MiningSequence(EMiningFunctionSettings settings)
            throws MiningException {
        super(settings);
        sequence = new ArrayList<MiningBlock>();
    }


    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        EMiningModel result = model;
        int l = sequence.size();
        for (int i = 0; i < l; i++) {
            MiningBlock block = sequence.get(i);
            result = block.run(result);
        }

        return result;
    }

    public boolean isDataBlock(){
        boolean flag = false;
        for (MiningBlock block: sequence)
            flag = flag ||  block.isDataBlock();

        return flag;
    }

    public List<MiningBlock> getSequence() {
        return sequence;
    }

    public Object clone() {
        MiningSequence o = null;
        o = (MiningSequence) super.clone();

        if (sequence != null) {
            o.sequence = new ArrayList<MiningBlock>();
            for (MiningBlock st : sequence) {
                MiningBlock nst = (MiningBlock) st.clone();
                  o.sequence.add(nst);
            }
        }

        return o;
    }


}
