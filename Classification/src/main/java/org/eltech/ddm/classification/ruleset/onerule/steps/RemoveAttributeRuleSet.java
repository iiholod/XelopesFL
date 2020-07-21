package org.eltech.ddm.classification.ruleset.onerule.steps;

import org.eltech.ddm.classification.ruleset.onerule.OneRuleMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * @author iholod
 */
public class RemoveAttributeRuleSet extends MiningBlock {


    public RemoveAttributeRuleSet(EMiningFunctionSettings miningSettings) throws MiningException {
        super(miningSettings);
    }

    protected EMiningModel execute(EMiningModel model) throws MiningException {
        ((OneRuleMiningModel)model).cleareCandidateRules();
        System.out.println(Thread.currentThread() + ": Model:  " + ((OneRuleMiningModel)model).getCandidateRules());

        return model;
    }

}
