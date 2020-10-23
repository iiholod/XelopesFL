package org.eltech.ddm.inputdata.superstreams;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

/**
 * VerMultiStream class.
 * A class that allows you to read multiple files 'vertically'.
 *
 * @author Maxim Kolpaschikov
 */

public class MiningVerSuperStream extends MiningSuperStream {

    // -----------------------------------------------------------------------
    //  Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructor that accepts an array of csv-file streams.
     * At this stage, the logical data of the csv-files that must match is checked.
     * @param streams - array of streams
     */
    public MiningVerSuperStream(MiningInputStream[] streams) throws MiningException {
        if (streams == null) throw  new NullPointerException("The stream array is empty.");
        super.streams = streams;
        init();
        open();
    }

    /**
     * Initializes the class with input data, if the logical data is correct.
     */
    private void init() throws MiningException {
        if (vectorsNumberChecked(streams)) {
            logicalData = collectLogicalData();
            super.physicalData = collectPhysicalData();
            super.vectorsNumber = streams[0].getVectorsNumber();
        } else {
            try {
                throw new InvalidObjectException("There are different number of vectors in the files.");
            } catch (InvalidObjectException ex) {
                // auto-generated block
            }
        }
    }

    /**
     * checks that the number of vectors in the files is the same
     * @return <b>true</b> if the number of vectors matches, <b>false</b> if the number of vectors does not match
     */
    private boolean vectorsNumberChecked(MiningInputStream[] streams) throws MiningException {
        int number = streams[0].getVectorsNumber();
        for (int i = 1; i < streams.length; i++) {
            if (streams[i].getVectorsNumber() != number) return false;
        }
        return true;
    }

    // -----------------------------------------------------------------------
    //  Methods that collect logical and physical data together
    // -----------------------------------------------------------------------

    /**
     * Collects logical data together.
     * @return ELogicalData
     */
    private ELogicalData collectLogicalData() throws MiningException {
        ELogicalData logicalData = new ELogicalData();
        for (MiningInputStream stream : streams) {
            ELogicalData ld = stream.getLogicalData();
            for(ELogicalAttribute la : ld.getAttributes()) {
                logicalData.addAttribute(la);
            }
        }
        return logicalData;
    }

    /**
     * Collects physical data together.
     * @return EPhysicalData
     */
    private EPhysicalData collectPhysicalData() throws MiningException {
        EPhysicalData physicalData = new EPhysicalData();
        for (MiningInputStream stream : streams) {
            EPhysicalData pa = stream.getPhysicalData();
            for(int i = 0; i < pa.getAttributeCount(); i++) {
                physicalData.addAttribute(pa.getAttribute(i));
            }
        }
        return physicalData;
    }

    // -----------------------------------------------------------------------
    //  Methods for getting vectors
    // -----------------------------------------------------------------------

    /**
     * Returns the current vector and moves the cursor to the next one.
     * @return MiningVector
     */
    @Override
    public MiningVector readPhysicalRecord() throws MiningException {

        open();
        int vecLength = 0;
        List<double[]> values = new ArrayList<>();

        try {
            for (MiningInputStream stream : streams) {
                double[] val = stream.next().getValues();
                values.add(val);
                vecLength += val.length;
            }
        } catch (Exception e) {
            return null;
        }

        MiningVector miningVector = new MiningVector(collectValues(values, vecLength));
        miningVector.setIndex(++cursorPosition);
        miningVector.setLogicalData(logicalData);
        return miningVector;
    }

    /**
     * Returns a vector based on the specified index.
     * @param pos - index of the vector
     * @return MiningVector
     */
    @Override
    protected MiningVector movePhysicalRecord(int pos) throws MiningException {

        open();
        if (pos < 0) throw new OutOfMemoryError("Invalid index.");

        int vecLength = 0;
        List<double[]> values = new ArrayList<>();
        try {
            for (MiningInputStream stream : streams) {
                double[] val = stream.getVector(pos).getValues();
                values.add(val);
                vecLength += val.length;
            }
        } catch (Exception e) {
           return null;
        }

        MiningVector miningVector = new MiningVector(collectValues(values, vecLength));
        cursorPosition = pos;
        miningVector.setIndex(cursorPosition);
        miningVector.setLogicalData(logicalData);
        return miningVector;
    }

    /**
     * Combines values from all received vectors.
     * @param values - a list containing arrays with the values of individual vectors
     * @param valuesNumber - number of all values
     * @return an array with all the values
     */
    private double[] collectValues(List<double[]> values, int valuesNumber) {

        int i = 0;
        double[] valuesArr = new double[valuesNumber];
        for (double[] val : values) {
            for (double v : val)
                valuesArr[i++] = v;
        }
        return valuesArr;
    }

    // -----------------------------------------------------------------------
    //  Methods for changing the stream state
    // -----------------------------------------------------------------------

    /**
     * Updates all streams.
     */
    @Override
    public void reset() throws MiningException {
        open();

        for (MiningInputStream stream : streams) {
            stream.reset();
        }
    }

    /**
     * returns a copy of the current stream
     * @return HorMultiCsvStream
     */
    @Override
    public MiningSuperStream deepCopy() throws MiningException {
        return new MiningVerSuperStream(streams);
    }

}
