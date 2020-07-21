package org.eltech.ddm.classification.ruleset.onerule;

import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

/**
 * @author iholod
 */
public class VectorsCount4AttributeValue extends MiningModelElement {

    //public final static int MAX_INDEX = 1;
    private int maxIndex;

    public VectorsCount4AttributeValue(String id) {
        super(id);
        //this.properties.add(MAX_INDEX, 0);
    }

    @Override
    protected String propertiesToString() {
        return ", maxIndex" + maxIndex;
    }

    @Override
    public void merge(List<MiningModelElement> valueCounts){
        for(MiningModelElement vCount: valueCounts) {
            //set(MAX_INDEX,  ((int)get(MAX_INDEX) > (int)vCount.get(MAX_INDEX))? MAX_INDEX):vCount.get(MAX_INDEX));
            setMaxIndex((((VectorsCount4TargetValue)getElement(getMaxIndex())).getNumberCorrectVectors() >
                    ((VectorsCount4TargetValue)vCount.getElement(((VectorsCount4AttributeValue) vCount).getMaxIndex())).getNumberCorrectVectors())?
                    getMaxIndex() : ((VectorsCount4AttributeValue) vCount).getMaxIndex());
        }
    }

    public int getMaxIndex() {
        return maxIndex;
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
    }
}
