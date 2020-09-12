package org.eltech.ddm.inputdata.file.MultiInputStream;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.MiningFileStream;
import org.eltech.ddm.inputdata.file.csv.ParsingValues;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;

import java.util.List;

/**
 * MiningMultiStream class.
 * A class that allows you to read multiple files 'vertically' or 'horizontally'.
 *
 * @author Maxim Kolpashikov
 */

public abstract class MiningMultiStream extends MiningFileStream {

    protected boolean isOpen = false;
    protected List<ParsingValues> parsingValues;

    protected int vectorsNumber = 0;
    protected MiningInputStream[] streams;

    protected ELogicalData logicalData;
    protected EPhysicalData physicalData;

    // -----------------------------------------------------------------------
    //  Abstract methods
    // -----------------------------------------------------------------------

    public abstract void close() throws MiningException;

    public abstract void open() throws MiningException;

    public abstract void reset() throws MiningException;

    public abstract MiningMultiStream getCopy() throws MiningException;

    public abstract MiningVector next() throws MiningException ;

    public abstract MiningVector getVector(int pos) throws MiningException ;

    // -----------------------------------------------------------------------
    //  Get methods
    // -----------------------------------------------------------------------

    /**
     * Returns the number of vectors.
     *
     * @return the number of vectors
     */
    public int getVectorsNumber() throws MiningException {
        open();
        return vectorsNumber;
    }

    /**
     * Returns a logical data.
     * @return logical data
     */
    public ELogicalData getLogicalData() throws MiningException {
        open();
        return logicalData;
    }

    /**
     * Returns a physical data.
     * @return physical data
     */
    public EPhysicalData getPhysicalData() throws MiningException {
        open();
        return physicalData;
    }
}
