package org.eltech.ddm.miningcore.algorithms;

import com.opencsv.exceptions.CsvException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElementRule;

import java.io.IOException;

/**
 * Created by Ivan on 20.11.2016.
 */
public class MiningDecisionElement extends MiningDecision {

    protected final MiningModelElementRule rule;

    protected final int[] indexSet;

//    protected MiningSequence trueBranch;
//
//    protected MiningSequence falseBranch;

    public MiningDecisionElement(EMiningFunctionSettings settings, MiningModelElementRule rule, int[] index, MiningBlock... trueBlocks) throws MiningException {
        super(settings);
        this.rule = rule;
        this.indexSet = index;
        trueBranch = new MiningSequence(settings, trueBlocks);
    }

    public MiningDecisionElement(EMiningFunctionSettings settings, MiningModelElementRule rule, int[] index, MiningSequence trueBlocks, MiningBlock... falseBlocks) throws MiningException {
        super(settings);
        this.rule = rule;
        this.indexSet = index;
        trueBranch = new MiningSequence(settings, trueBlocks);
        falseBranch = new MiningSequence(settings, falseBlocks);
    }

    public MiningDecisionElement(EMiningFunctionSettings settings, MiningModelElementRule rule, int[] index, MiningSequence trueBlocks, MiningSequence falseBlocks) throws MiningException {
        super(settings);
        this.rule = rule;
        this.indexSet = index;
        trueBranch = new MiningSequence(settings, trueBlocks);
        falseBranch = new MiningSequence(settings, falseBlocks);
    }


    protected boolean condition(EMiningModel model) throws MiningException {
        MiningModelElement element = model.getElement(indexSet);

        return rule.verify(element);
    }

    @Override
    public EMiningModel execute(EMiningModel model) throws MiningException, IOException, CsvException {

        EMiningModel result = model;
        if (rule.verify(model.getElement(indexSet))){
            if(trueBranch != null)
                result = trueBranch.run(model);
        } else{
            if(falseBranch != null)
                result = falseBranch.run(model);
        }

        return result;
    }
}
