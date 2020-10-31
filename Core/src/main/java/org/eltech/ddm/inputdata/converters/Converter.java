package org.eltech.ddm.inputdata.converters;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;

public interface Converter {

    /**
     * Method calling vector conversion.
     * @param mv - user vector
     * @return modified vector
     */
    MiningVector vectorConversion(MiningVector mv) throws MiningException;
}
