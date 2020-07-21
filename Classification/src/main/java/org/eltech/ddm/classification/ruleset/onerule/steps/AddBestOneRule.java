package org.eltech.ddm.classification.ruleset.onerule.steps;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.SimplePredicate;
import org.eltech.ddm.classification.ruleset.SimpleRule;
import org.eltech.ddm.classification.ruleset.onerule.OneRuleCountMiningModel;
import org.eltech.ddm.classification.ruleset.onerule.OneRuleMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.Operator;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeElement;

/**
 * @author iholod
 */
public class AddBestOneRule extends MiningBlock {

    private final ELogicalAttribute target;

    public AddBestOneRule(EMiningFunctionSettings settings)
            throws MiningException {
        super(settings);
        target = ((ClassificationFunctionSettings) settings).getTarget();
    }

    protected EMiningModel execute(EMiningModel model) throws MiningException {
        LogicalAttributeElement lae = model.getCurrentAttribute();
        int indexAttr = lae.getIndex();
        String attrID = lae.getID();

        int indexValue = model.getCurrentAttributeValueIndex();

        int indexTargetValue = ((OneRuleCountMiningModel)model).getCountMatrix(indexAttr, indexValue).getMaxIndex();
        int count = ((OneRuleCountMiningModel)model).getCountMatrix(indexAttr, indexValue, indexTargetValue).getNumberCorrectVectors();

        SimpleRule rule = createRule(indexValue, indexTargetValue, attrID, count);

        //System.out.println(Thread.currentThread() + ": Rule:  " + rule + " = " + count );
        ((OneRuleMiningModel)model).addCandidateRule(rule);
        System.out.println(Thread.currentThread() + ": Model:  " + ((OneRuleMiningModel)model).getCandidateRules());

        return model;
    }

    protected SimpleRule createRule(int indexValue, int indexTargetValue, String attribute, int count) throws MiningException {

        SimplePredicate predicate = new SimplePredicate(attribute, indexValue, Operator.EQUAL); // TODO: need know about value
        SimpleRule r  = new SimpleRule(predicate);
		r.setNumberOfPredicatedVectors(count);
        r.setScore(target.getCategoricalProperties().getValue(indexTargetValue).toString());

        return r;
    }

}
