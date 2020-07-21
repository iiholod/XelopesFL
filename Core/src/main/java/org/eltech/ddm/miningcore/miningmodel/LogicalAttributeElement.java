package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategory;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iihol on 12.10.2016.
 */
public class LogicalAttributeElement extends MiningModelElement {

    private ELogicalAttribute attribute;

    private int index;

    LogicalAttributeElement(int index, String id, ELogicalAttribute attribute) {
        super(id);
        this.index = index;
        this.attribute = attribute;
        set = new ArrayList<>();
        if(attribute.getAttributeType() != AttributeType.numerical) {
            for (int i = 0; i < attribute.getCategoricalProperties().getSize(); i++) {
                ECategory cat = attribute.getCategoricalProperties().getCategory(i);
                LogicalAttributeValueElement lave = new LogicalAttributeValueElement(i, cat.getName(), cat);
                set.add(i, lave);
            }
        }
    }


    public int size(){
        return attribute.getCategoricalProperties().getSize();
    }

    protected void add(MiningModelElement element){
    }

    void insert(int pos, MiningModelElement element){
    }

    protected void remove(int index){
    }


    @Override
    public Object clone() {
        LogicalAttributeElement o = null;
        o = (LogicalAttributeElement)super.clone();

        o.attribute = getAttribute(); // Attributes is unchangeable entity therefor we clone only reference
        return o;
    }

    @Override
    protected String propertiesToString() {
        return getAttribute().toString();
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {


    }

    public ELogicalAttribute getAttribute() {
        return attribute;
    }

    public int getIndex() {
        return index;
    }
}
