package org.eltech.ddm.classification.ruleset.onerule;

import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

/**
 * @author iholod
 */
public class VectorsCount4TargetValue extends MiningModelElement {

    //public final static int COUNT = 1;

    volatile private int numberCorrectVectors = 0;

    public VectorsCount4TargetValue(String id) {
        super(id);
        //this.properties.add(COUNT, 0);
    }

    @Override
    protected String propertiesToString() {
        return ", number of vectors " + numberCorrectVectors;
    }

    @Override
    public void merge(List<MiningModelElement> valueCounts){
        int delta = 0;
        for(MiningModelElement vCount: valueCounts) {
            //delta = delta + ((int) vCount.get(COUNT) - (int)get(COUNT));
            delta = delta + (((VectorsCount4TargetValue) vCount).getNumberCorrectVectors() - getNumberCorrectVectors());
        }
        //set(COUNT, (int)get(COUNT)+ delta);
        numberCorrectVectors = getNumberCorrectVectors() + delta;
    }

    public int getNumberCorrectVectors() {
        return numberCorrectVectors;
    }

    public void incNumberCorrectVectors(){
        numberCorrectVectors++;
    }
}
