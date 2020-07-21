package org.eltech.ddm.clustering;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

/**
 * Created by iihol on 13.02.2018.
 */
public class ClusterSet  extends MiningModelElement {
    private boolean changed = true;

    private int numberOfIterations;

    public ClusterSet(String id) {
        super(id);
    }

    @Override
    protected String propertiesToString() {
        return ",changed=" + changed;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        long delta = 0;
        for(MiningModelElement cluster: elements) {
             changed &= ((ClusterSet) cluster).isChanged();
        }
    }
    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int num) {
        numberOfIterations = num;
    }

    public void incNumberOfIterations() {
        numberOfIterations++;
    }

}
