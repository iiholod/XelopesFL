package org.eltech.ddm.inputdata.converters.numerical;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.converters.VectorConverter;
import org.eltech.ddm.miningcore.MiningException;

abstract class NumericalConverter extends VectorConverter {

    /**
     * The method calls the conversion of the vector to a specific type (numerical or categorical).
     * @param mv - user vector
     * @return modified vector
     */
    @Override
    protected MiningVector conversion(MiningVector mv) throws MiningException {
        return numericalConversion(mv);
    }

    /**
     * Method calls conversion of a vector to a numeric type by the specified method.
     * @param mv - user vector
     * @return modified vector
     */
    protected abstract MiningVector numericalConversion(MiningVector mv) throws MiningException;

}
