package org.eltech.ddm.classification.ruleset;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iholod
 */
public class RuleSet extends MiningModelElement {

    //public final static int NUMBER_CORRECT_VECTORS = 1;
    private int numberCorrectVectors = 0;

    public RuleSet(String id) {
        super(id);
        //this.properties.add(NUMBER_CORRECT_VECTORS, 0);
        set = new ArrayList<>();
    }

    protected void remove(int index){
        SimpleRule entity = (SimpleRule) set.get(index);
        if(entity != null) {
            //set(NUMBER_CORRECT_VECTORS, (int)get(NUMBER_CORRECT_VECTORS) - (int)entity.get(NUMBER_CORRECT_VECTORS));
            numberCorrectVectors = numberCorrectVectors - entity.getNumberOfPredicatedVectors();
        }
        super.remove(index);
    }

    @Override
    protected String propertiesToString() {
        return ",number vectors = " +numberCorrectVectors;
    }

    @Override
    public void merge(List<MiningModelElement> ruleSets) throws MiningException {
        int delta = 0;
        for(MiningModelElement rSet: ruleSets) {
            //delta = delta + ((int)rule.get(NUMBER_CORRECT_VECTORS) - (int)get(NUMBER_CORRECT_VECTORS));
            delta = delta + (((RuleSet)rSet).numberCorrectVectors - numberCorrectVectors);
        }
        //set( NUMBER_CORRECT_VECTORS, (int)get(NUMBER_CORRECT_VECTORS) + delta);
        numberCorrectVectors += delta;
    }

    public synchronized int getNumberCorrectVectors() {
        return numberCorrectVectors;
    }

    public synchronized void addNumberCorrectVectors(int numberCorrectVectors) {
        this.numberCorrectVectors += numberCorrectVectors;
    }
}
