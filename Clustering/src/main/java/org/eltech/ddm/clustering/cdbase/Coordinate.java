package org.eltech.ddm.clustering.cdbase;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by iihol on 13.02.2018.
 */
public class Coordinate extends MiningModelElement {

    private double value = 0;

    // mass of the coordinate (summ distances of all vectors)
    private double mass = 0;

    public Coordinate(String id) {
        super(id);
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        double delta = 0;
        for(MiningModelElement elem: elements) {
            delta = delta + (((Coordinate)elem).mass - mass);
        }
        mass += delta;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMass() {
        return this.mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void addMass(double delta) {
        this.mass += delta;
    }

    @Override
    public String propertiesToString() {
//        return super.propertiesToString() + ",v=" + value + ", m="+ mass + ">";
        return ",v=" + new BigDecimal(value).setScale(2, RoundingMode.UP).doubleValue()
                + ",m="+ new BigDecimal(mass).setScale(2, RoundingMode.UP).doubleValue() + ">";
    }
}
