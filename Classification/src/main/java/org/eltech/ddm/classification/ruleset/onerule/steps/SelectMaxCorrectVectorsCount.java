package org.eltech.ddm.classification.ruleset.onerule.steps;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.classification.ruleset.onerule.OneRuleCountMiningModel;
import org.eltech.ddm.classification.ruleset.onerule.VectorsCount4AttributeValue;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * @author iholod
 */
public class SelectMaxCorrectVectorsCount extends MiningBlock {
    public SelectMaxCorrectVectorsCount(EMiningFunctionSettings settings)
            throws MiningException {
        super(settings);
    }

    protected EMiningModel execute(EMiningModel model) throws MiningException {
        int indexAttr = model.getCurrentAttributeIndex();

        int indexValue = model.getCurrentAttributeValueIndex();
        int indexTargetValue = ((ClassificationMiningModel)model).getCurrentTargetAttributeValueIndex();

        VectorsCount4AttributeValue vc4qv = ((OneRuleCountMiningModel)model).getCountMatrix(indexAttr, indexValue);
        int maxPos = vc4qv.getMaxIndex();
        int count = ((OneRuleCountMiningModel)model).getCountMatrix(indexAttr, indexValue, indexTargetValue).getNumberCorrectVectors();
        int countMax = ((OneRuleCountMiningModel)model).getCountMatrix(indexAttr, indexValue, maxPos).getNumberCorrectVectors();

        if(countMax < count) {
            vc4qv.setMaxIndex(indexTargetValue);
 //           System.out.println(Thread.currentThread() + Arrays.toString(cvcIndex) + " maxIndex = " + indexTargetValue );
        }

        return model;
    }

}
