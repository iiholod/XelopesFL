package org.eltech.ddm.classification.ruleset.onerule.steps;

import org.eltech.ddm.classification.ruleset.onerule.OneRuleCountMiningModel;
import org.eltech.ddm.classification.ruleset.onerule.OneRuleMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeElement;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeValueElement;

/**
 * @author iholod
 */
public class SummCorrectVectorsCount extends MiningBlock {

     public SummCorrectVectorsCount(EMiningFunctionSettings settings)
            throws MiningException {
        super(settings);
    }

    protected EMiningModel execute(EMiningModel model) throws MiningException {
        LogicalAttributeElement lae = (LogicalAttributeElement)model.getElement(EMiningModel.INDEX_CURRENT_ATTRIBUTE);
        int indexAttr = lae.getIndex();

        LogicalAttributeValueElement lave = (LogicalAttributeValueElement)model.getElement(EMiningModel.INDEX_CURRENT_ATTRIBUTE_CURRENT_VALUE);
        int indexValue = lave.getIndex();

        int indexTargetValue = ((OneRuleCountMiningModel)model).getCountMatrix(indexAttr, indexValue).getMaxIndex();

        int count = ((OneRuleCountMiningModel)model).getCountMatrix(indexAttr, indexValue, indexTargetValue).getNumberCorrectVectors();

        ((OneRuleMiningModel)model).getCandidateRules().addNumberCorrectVectors(count);
        System.out.println(Thread.currentThread() + " Candidate rule set = " + ((OneRuleMiningModel)model).getCandidateRules().getNumberCorrectVectors() );

        return model;
    }

}
