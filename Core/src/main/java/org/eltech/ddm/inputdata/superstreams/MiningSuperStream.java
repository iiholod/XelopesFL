package org.eltech.ddm.inputdata.superstreams;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;

/**
 * MiningMultiStream class.
 * A class that allows you to read multiple files 'vertically' or 'horizontally'.
 *
 * @author Maxim Kolpaschikov
 */

public abstract class MiningSuperStream extends MiningInputStream {

    protected MiningInputStream[] streams;

    // -----------------------------------------------------------------------
    //  Abstract methods
    // -----------------------------------------------------------------------

    public abstract MiningSuperStream deepCopy() throws MiningException;

    // -----------------------------------------------------------------------
    //  Override methods
    // -----------------------------------------------------------------------

    /**
     * Opens the stream.
     */
    @Override
    public void open() throws MiningException {
        if (open) return;
        open = true;
        for (MiningInputStream stream : streams) {
            stream.open();
            stream.setLogicalData(logicalData);
            stream.setPhysicalData(physicalData);
            stream.setAttributeAssignmentSet(attributeAssignmentSet);
        }
    }

    /**
     * Closes the stream.
     */
    @Override
    public void close() throws MiningException {
        if (!open) return;
        open = false;
        for (MiningInputStream stream : streams) {
            stream.close();
        }
    }

    // -----------------------------------------------------------------------
    //  Getters
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
