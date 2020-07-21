package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iihol on 13.02.2018.
 */
public class VectorAssignment extends MiningModelElement {

    private int iCluster;

    private double distanceToCenter = Double.MAX_VALUE;

    public VectorAssignment(String id) {
        super(id);
        set = new ArrayList<>();
    }

    @Override
    public MiningModelElement createNewCopyElement() {
        return new VectorAssignment(getID());
    }

    @Override
    protected String propertiesToString() {
        return ",cluster="+ iCluster + ",distToCenter=" + distanceToCenter;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
         VectorAssignment va = (VectorAssignment)elements.get(0);
         iCluster = va.iCluster;
         distanceToCenter = va.distanceToCenter;
         for(int i = 1; i < elements.size(); i++) {
             va = (VectorAssignment)elements.get(i);
             if(distanceToCenter > va.distanceToCenter){
                 iCluster = va.iCluster;
                 distanceToCenter = va.distanceToCenter;
             }
        }
    }

    public int getIndexCluster() {
        return iCluster;
    }

    public void setIndexCluster(int iClaster) {
        this.iCluster = iClaster;
    }

    public double getDistanceToCenter() {
        return distanceToCenter;
    }

    public void setDistanceToCenter(double distanceToCenter) {
        this.distanceToCenter = distanceToCenter;
    }
}
