package org.eltech.ddm.classification;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.IClassifier;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeValueElement;

public abstract class ClassificationMiningModel extends EMiningModel implements IClassifier {

    protected int indexTarget;
    public int[] INDEX_CURRENT_TARGET_VALUE;


    protected ELogicalAttribute target;

    // ======= current state of model (build model task) ==================
    //private int currentTargetValue;


    public ClassificationMiningModel(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        target = ((ClassificationFunctionSettings) settings).getTarget();
        indexTarget = settings.getLogicalData().getAttributeIndex(target);

        INDEX_CURRENT_TARGET_VALUE = index(ATTRIBUTE_SET, indexTarget, -1);
    }

    public ClassificationMiningModel() {

    }

    //	public void setCurrentTargetValue(int index) {
//		currentTargetValue = index;
//	}
//
    public LogicalAttributeValueElement getTargetAttributeValue(int indexTargetValue) throws MiningException {
        return (LogicalAttributeValueElement) getElement(index(ATTRIBUTE_SET, indexTarget, indexTargetValue));
    }

    public int getCurrentTargetAttributeValueIndex() throws MiningException {
        return getCurrentElementIndex(index(ATTRIBUTE_SET, indexTarget));
    }


    public ELogicalAttribute getTarget() {
        return target;
    }

    public void setTarget(ELogicalAttribute target) {
        this.target = target;
    }

    public Object clone() {
        ClassificationMiningModel o = null;
        o = (ClassificationMiningModel) super.clone();

        o.target = target;
//	    o.indexTarget = indexTarget;
//	    o.currentTargetValue = currentTargetValue;

        return o;
    }

}