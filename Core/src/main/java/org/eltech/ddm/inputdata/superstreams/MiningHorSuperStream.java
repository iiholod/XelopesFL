package org.eltech.ddm.inputdata.superstreams;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;

import java.io.InvalidObjectException;

/**
 * HorMultiStream class.
 * A class that allows you to read multiple files 'horizontally'.
 *
 * @author Maxim Kolpaschikov
 */

public class MiningHorSuperStream extends MiningSuperStream {

    private int activeStreamIndex;
    private MiningInputStream activeStream;

    // -----------------------------------------------------------------------
    //  Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructor that accepts an array of csv-file streams.
     * At this stage, the logical data of the csv-files that must match is checked.
     * @param streams - array of streams
     */
    public MiningHorSuperStream(MiningInputStream[] streams) throws MiningException {
        if (streams == null) throw  new NullPointerException("The stream array is empty.");
        super.streams = streams;
        init();
        open();
    }

    /**
     * Initializes the class with input data, if the logical data is correct.
     */
    private void init() throws MiningException {
        if (logicalDataChecked(streams)) {
            activeStreamIndex = 0;
            activeStream = streams[0];
            vectorsNumber = calculateVecNumber();
            logicalData = activeStream.getLogicalData();
            physicalData = activeStream.getPhysicalData();
            attributeAssignmentSet = activeStream.getAttributeAssignmentSet();
        } else {
            try {
                throw new InvalidObjectException("Logical data does not match.");
            } catch (InvalidObjectException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Checks the values of logical data in files
     * @param streams - array of streams
     * @return <b>true</b> if the logical data is correct, <b>false</b> if the logical data is incorrect
     */
    private boolean logicalDataChecked(MiningInputStream[] streams) throws MiningException {
        ELogicalData logicalData = streams[0].getLogicalData();
        for (int i = 1; i < streams.length; i++) {
            ELogicalData ld = streams[i].getLogicalData();
            if (!logicalData.equals(ld)) return false;
        }
        return true;
    }

    // -----------------------------------------------------------------------
    //  Methods for getting vectors
    // -----------------------------------------------------------------------

    /**
     * Returns the current vector and moves the cursor to the next one.
     * When the file ends, the cursor is moved to the next stream.
     */
    @Override
    public MiningVector readPhysicalRecord() throws MiningException {

        open();

        MiningVector vector = activeStream.next();
        if (vector == null) {
            if (activeStreamIndex == streams.length - 1)
                return null;

            activeStream = streams[++activeStreamIndex];
            activeStream.reset();
            vector = activeStream.next();
        }

        vector.setIndex(++cursorPosition);
        vector.setLogicalData(logicalData);
        return vector;
    }

    /**
     * Returns a vector based on the specified index.
     * @param position - index of the vector
     */
    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException {

        if (position < 0) throw new OutOfMemoryError("Invalid index.");
        open();

        MiningVector vector = getVectorOfStream(position);
        if (vector == null) return null;

        cursorPosition = position;
        vector.setIndex(position);
        vector.setLogicalData(logicalData);
        return vector;
    }

    /**
     * Finds a vector by index among all files.
     * Changes the active stream and index if necessary.
     * @param pos - index of the vector
     * @return vector number in the found file
     */
    private MiningVector getVectorOfStream(int pos) throws MiningException {

        int vectorsNumber = 0;
        for (int i = 0; i < streams.length; i++) {
            vectorsNumber += streams[i].getVectorsNumber();
            if (pos <= vectorsNumber - 1) {
                activeStreamIndex = i;
                activeStream = streams[i];
                int prevValue = vectorsNumber - streams[i].getVectorsNumber();
                return activeStream.getVector(pos - prevValue);
            }
        }
        return null;
    }

    // -----------------------------------------------------------------------
    //  Methods for changing the stream state
    // -----------------------------------------------------------------------

    /**
     * Counts the number of vectors.
     * @return the number of vectors
     */
    private int calculateVecNumber() throws MiningException {
        int number = 0;
        for (MiningInputStream stream : streams) {
            number += stream.getVectorsNumber();
        }
        return number;
    }

     /**
     * Updates all streams.
     */
    @Override
    public void reset() throws MiningException {
        open();

        cursorPosition = -1;
        activeStreamIndex = 0;
        activeStream = streams[0];
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
        return new MiningHorSuperStream(streams);
    }

}