package org.eltech.ddm.inputdata.converters;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.converters.categorical.CategoricalLabelEncoder;
import org.eltech.ddm.inputdata.converters.numerical.NumericalLabelEncoder;
import org.eltech.ddm.inputdata.converters.type.ConverterType;
import org.eltech.ddm.miningcore.MiningException;

public class VectorConverterManager implements Converter {

    /**
     * User vector converter.
     */
    private VectorConverter converter;

    /**
     * Constructor accepting conversion type.
     * @param ct - vector values conversion type
     */
    public VectorConverterManager(ConverterType ct) {
        createConverter(ct);
    }

    /**
     * Creates a vector converter based on the received type.
     * @param ct - vector values conversion type
     */
    private void createConverter(ConverterType ct) {

        switch (ct) {
            case NUMERICAL_LABEL_ENCODER:
                converter = new NumericalLabelEncoder();
                break;
            case CATEGORICAL_LABEL_ENCODER:
                converter = new CategoricalLabelEncoder();
                break;
        }
    }

    /**
     * Method calling vector conversion.
     * @param mv - user vector
     * @return modified vector
     */
    @Override
    public MiningVector vectorConversion(MiningVector mv) throws MiningException {
        return converter.vectorConversion(mv);
    }

}
