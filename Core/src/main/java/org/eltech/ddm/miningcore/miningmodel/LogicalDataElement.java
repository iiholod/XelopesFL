package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iihol on 07.02.2018.
 */
public class LogicalDataElement extends MiningModelElement {

    private ELogicalData logicalData;

    LogicalDataElement(String id, ELogicalData logicalData) {
        super(id);
        this.logicalData = logicalData;
        set = new ArrayList<>(logicalData.getAttributesNumber());
        for(int i = 0; i < logicalData.getAttributesNumber(); i++){
            ELogicalAttribute attr = null;
            try {
                attr = logicalData.getAttributes().get(i);
            } catch (MiningException e) {
                e.printStackTrace();
            }
            LogicalAttributeElement lae = new LogicalAttributeElement(i, attr.getName(), attr);
            set.add(i, lae);
        }
    }

    public int size(){
        return logicalData.getAttributesNumber();
    }

    protected void add(MiningModelElement element){
    }

    void insert(int pos, MiningModelElement element){
    }

    protected void remove(int index){
    }

    @Override
    protected String propertiesToString() {
        return logicalData.toString();
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }
}
