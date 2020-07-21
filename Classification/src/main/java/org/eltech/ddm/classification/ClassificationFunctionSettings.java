package org.eltech.ddm.classification;

import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningFunction;
import org.eltech.ddm.supervised.ESupervisedFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryMatrix;

public class ClassificationFunctionSettings extends ESupervisedFunctionSettings {

    /**
     * The optional CostMatrix attribute specifies a two-dimensional, NxN matrix that defines the
     * cost associated with a prediction versus the actual value. A cost matrix is typically used in
     * classification models, where N is the number of classes in the target, and the columns and
     * rows are labeled with class values.
     */
    private CategoryMatrix costMatrix;

    private final String TAG_NAME_TARGET_ATTRIBUTE = "target";

    public ClassificationFunctionSettings(ELogicalData ld) {
        super(ld);

        addTaggedValue(TAG_NAME_TARGET_ATTRIBUTE, null, "String");
    }

    public void setLogicalData(ELogicalData data) {
        this.logicalData = data;
    }


    //@Override
    public MiningFunction getMiningFunction() {
        // TODO Auto-generated method stub
        return MiningFunction.classification;
    }

    public ELogicalAttribute getTarget() {
        String v = getTaggedValue(TAG_NAME_TARGET_ATTRIBUTE);
        if (v != null) {
            ELogicalAttribute target = ((ELogicalData) logicalData).getAttribute(v);

            return target;
        }

        return null;
    }

    public void setTarget(ELogicalAttribute trgt) {
        setTaggedValue(TAG_NAME_TARGET_ATTRIBUTE, trgt.getName());
    }
}
