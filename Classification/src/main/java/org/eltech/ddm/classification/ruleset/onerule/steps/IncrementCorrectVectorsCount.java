package org.eltech.ddm.classification.ruleset.onerule.steps;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.ruleset.onerule.OneRuleCountMiningModel;
import org.eltech.ddm.classification.ruleset.onerule.VectorsCount4TargetValue;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeElement;

/**
 * @author iholod
 */
public class IncrementCorrectVectorsCount extends DataMiningBlock {

    private final int indexTarget;

    public IncrementCorrectVectorsCount(EMiningFunctionSettings settings)
            throws MiningException {
        super(settings);
        ELogicalAttribute target = ((ClassificationFunctionSettings) settings).getTarget();
        indexTarget = settings.getLogicalData().getAttributeIndex(target);

    }

    protected EMiningModel execute(MiningInputStream data, EMiningModel model) throws MiningException {
        LogicalAttributeElement la = model.getCurrentAttribute();
        int iAttr = la.getIndex();
        MiningVector mv = data.getVector(model.getCurrentVectorIndex());
        System.out.println(Thread.currentThread() + ": mv = " + mv.getIndex());

        int value = (int) mv.getValue(la.getID());
        int targetValue = (int)mv.getValue(indexTarget);

        VectorsCount4TargetValue vc4tv = ((OneRuleCountMiningModel)model).getCountMatrix(iAttr, value, targetValue);
        vc4tv.incNumberCorrectVectors();

        return model;
    }

}
