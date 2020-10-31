package org.eltech.ddm.inputdata.converters.numerical;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import java.util.Map;

public class NumericalLabelEncoder extends NumericalConverter {

    /**
     * The method calls the conversion of the vector to a numeric type by the numbering method.
     * @param mv - user vector
     * @return modified vector
     */
    @Override
    protected MiningVector numericalConversion(MiningVector mv) throws MiningException {

        double[] values = mv.getValues();
        if (valuesMaps == null) {
            initValuesMap(values.length);
        }

        for (int i = 0; i < values.length; i++) {
            if (mv.getLogicalData().getAttribute(i).getAttributeType() == AttributeType.categorical) {
                values[i] = getNumberOfValue(values[i], i);
            }
        }

        return createNewVector(mv, values);
    }

    /**
     * Returns a numeric value for a categorical value.
     * @param value - categorical value
     * @param valueNumberInVector - the position of the categorical value in the vector
     * @return new vector
     */
    private double getNumberOfValue(double value, int valueNumberInVector) {

        Map<Double, Double> catValMap = valuesMaps.get(valueNumberInVector);

        if (catValMap.size() == 0) {
            catValMap.put(value, 1.0d);
            return 1.0d;
        }

        if (catValMap.containsKey(value)) {
            return catValMap.get(value);
        } else {
            double numericalValue = (double)catValMap.size() + 1.0d;
            catValMap.put(value, numericalValue);
            return numericalValue;
        }
    }

}
