package org.eltech.ddm.inputdata.converters.categorical;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import java.util.Map;

public class CategoricalLabelEncoder extends CategoricalConverter{

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
            if (mv.getLogicalData().getAttribute(i).getAttributeType() == AttributeType.numerical) {
                values[i] = getCategoryOfValue(values[i], i);
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
    private double getCategoryOfValue(double value, int valueNumberInVector) {

        Map<Double, Double> numValMap = valuesMaps.get(valueNumberInVector);

        if (numValMap.size() == 0) {
            numValMap.put(value, 1.0d);
            return 1.0d;
        }

        if (numValMap.containsKey(value)) {
            return numValMap.get(value);
        } else {
            double numericalValue = (double)numValMap.size() + 1.0d;
            numValMap.put(value, numericalValue);
            return numericalValue;
        }
    }
}
