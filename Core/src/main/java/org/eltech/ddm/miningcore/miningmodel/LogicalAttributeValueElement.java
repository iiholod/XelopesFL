package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategory;

import java.util.List;

/**
 * Created by iihol on 13.10.2016.
 */
public class LogicalAttributeValueElement extends MiningModelElement {

    private ECategory value;

    private int index;

    LogicalAttributeValueElement(int index, String id, ECategory value){
        super(id);
        this.value = value;
        this.index = index;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    @Override
    public Object clone() {
        LogicalAttributeValueElement o = null;

        o = (LogicalAttributeValueElement)super.clone();
        o.value = getValue(); // Values of attributes are unchangeable entity therefor we clone only reference

        return o;
    }

    @Override
    protected String propertiesToString() {
        return getValue().toString();
    }

    public ECategory getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}
