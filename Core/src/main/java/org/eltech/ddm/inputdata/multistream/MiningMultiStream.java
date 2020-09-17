package org.eltech.ddm.inputdata.multistream;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.csv.ParsingValues;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;

import java.util.List;

/**
 * MiningMultiStream class.
 * A class that allows you to read multiple files 'vertically' or 'horizontally'.
 *
 * @author Maxim Kolpaschikov
 */

public abstract class MiningMultiStream extends MiningInputStream {

    protected boolean isOpen = false;
    protected List<ParsingValues> parsingValues;

    protected MiningInputStream[] streams;

    // -----------------------------------------------------------------------
    //  Abstract methods
    // -----------------------------------------------------------------------

    public abstract MiningMultiStream deepCopy() throws MiningException;

    // -----------------------------------------------------------------------
    //  Get methods
    // -----------------------------------------------------------------------

    /**
     * Returns the number of vectors.
     * @return the number of vectors
     */
    @Override
    public int getVectorsNumber() throws MiningException {
        return vectorsNumber;
    }

    /**
     * Returns a logical data.
     * @return logical data
     */
    @Override
    public ELogicalData getLogicalData() throws MiningException {
        return logicalData;
    }

    @Override
    public EPhysicalData recognize() throws MiningException {
        return physicalData;
    }

    /**
     * Returns a physical data.
     * @return physical data
     */
    @Override
    public EPhysicalData getPhysicalData() throws MiningException {
        return physicalData;
    }
}
