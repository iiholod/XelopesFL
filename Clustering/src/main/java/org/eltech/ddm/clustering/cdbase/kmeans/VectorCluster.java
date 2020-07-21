package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

/**
 * Created by iihol on 13.02.2018.
 */
public class VectorCluster extends MiningModelElement {

    private double distance;

    public VectorCluster(String id) {
        super(id);
    }

    @Override
    public MiningModelElement createNewCopyElement() {
        return new VectorCluster(getID());
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        double delta = 0;
        for(MiningModelElement elem: elements) {
            delta = delta + (((VectorCluster)elem).distance - distance);
        }
        distance += delta;
        //System.out.println("Thread-" + Thread.currentThread().getName() + " this = " + this);

    }

    public double getDistance() {
        return distance;
    }

    public void addDistance(double distance) {
        this.distance += distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    protected String propertiesToString(){
        return ",d=" + distance ;
    }
}
