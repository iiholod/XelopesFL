package org.eltech.ddm.inputdata.converters;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class VectorConverter implements Converter {

    /**
     * Array of maps storing numerical/categorical value and its converted categorical/numeric value for all columns.
     */
    protected List<Map<Double, Double>> valuesMaps;

    /**
     * Method calling vector conversion.
     * @param mv - user vector
     * @return modified vector
     */
    @Override
    public MiningVector vectorConversion(MiningVector mv) throws MiningException {
        return conversion(mv);
    }

    /**
     * The method calls the conversion of the vector to a specific type (numerical || categorical).
     * @param mv - user vector
     * @return modified vector
     */
    protected abstract MiningVector conversion(MiningVector mv) throws MiningException;

    /**
     * Collects new vector.
     * @param oldVector - old vector
     * @param newValues - array of new values
     * @return new vector
     */
    protected MiningVector createNewVector(MiningVector oldVector, double[] newValues) {

        MiningVector newVector = new MiningVector(newValues);
        newVector.setLogicalData(oldVector.getLogicalData());
        newVector.setIndex(oldVector.getIndex());
        return newVector;
    }

    /**
     * Initializes an array of value maps.
     * @param vecLength - the number of values in the vector
     */
    protected void initValuesMap(int vecLength) {

        valuesMaps = new ArrayList<>();
        for (int i = 0; i < vecLength; i++)
            valuesMaps.add(new HashMap<>());
    }
}
