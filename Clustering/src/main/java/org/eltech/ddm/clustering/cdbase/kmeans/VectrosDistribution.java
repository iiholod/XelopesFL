package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by iihol on 17.02.2018.
 */
public class VectrosDistribution extends MiningModelElement {

    private HashMap<String, Integer> mapVectors;

    public VectrosDistribution(String id) {
        super(id);
        mapVectors =  new HashMap<String, Integer>();
        set = new ArrayList<>();
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        for(MiningModelElement elem: elements){
            mapVectors.putAll(((VectrosDistribution)elem).mapVectors);
        }
    }

    public synchronized MiningModelElement getElement(int index){
        Integer indx = mapVectors.get("v"+index);
        if (indx != null)
            return super.getElement(indx);
        else
            return null;
    }

    protected synchronized void add(MiningModelElement element){
        super.add(element);
        mapVectors.put(element.getID(), set.size()-1);
    }

//    synchronized void insert(int pos, MiningModelElement element){
//        set.add(pos, element);
//    }

    protected synchronized void replace(int pos, MiningModelElement element){
        Integer indx = mapVectors.get("v"+pos);
        if (indx != null)
            super.replace(indx, element);
    }

    protected synchronized void remove(int index){
        Integer indx = mapVectors.get("v"+index);
        if (indx != null) {
            super.remove(indx);
            mapVectors.remove("v"+index);
        }
    }

    /**
     * Clone mining model's element for parallel processing
     * O(n) = n where n - number of elements of this set
     * @return
     */
    @Override
    public Object clone(){
        VectrosDistribution o = (VectrosDistribution)super.clone();

        if(mapVectors != null){
            o.mapVectors = new HashMap<>();
            o.mapVectors.putAll(mapVectors);
//            for(String key : mapVectors.keySet())
//                o.mapVectors.put(key, mapVectors.get(key));
        }

        return o;
    }

    @Override
    protected String propertiesToString() {
        return "";
    }

}
