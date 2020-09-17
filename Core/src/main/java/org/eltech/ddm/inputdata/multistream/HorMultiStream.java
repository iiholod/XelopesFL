package org.eltech.ddm.inputdata.multistream;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;

import java.io.InvalidObjectException;
import java.util.ArrayList;

/**
 * HorMultiStream class.
 * A class that allows you to read multiple files 'horizontally'.
 *
 * @author Maxim Kolpashikov
 */

public class HorMultiStream extends MiningMultiStream {

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
    public HorMultiStream(MiningInputStream[] streams) throws MiningException {
        if (streams == null) throw  new NullPointerException("The stream array is empty.");
        init(streams);
    }

    /**
     * Initializes the class with input data, if the logical data is correct.
     * @param streams - array of streams
     */
    private void init(MiningInputStream[] streams) throws MiningException {
        if (logicalDataChecked(streams)) {
            thisInit(streams);
            superInit(streams);
        } else {
            try {
                throw new InvalidObjectException("Logical data does not match.");
            } catch (InvalidObjectException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void thisInit(MiningInputStream[] streams) {
        activeStreamIndex = 0;
        activeStream = streams[0];
    }

    private void superInit(MiningInputStream[] streams) throws MiningException {

        super.streams = streams;
        parsingValues = new ArrayList();
        vectorsNumber = calculateVecNumber();
        logicalData = activeStream.getLogicalData();
        physicalData = activeStream.getPhysicalData();
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
     * @return MiningVector
     */
    @Override
    public MiningVector readPhysicalRecord() throws MiningException {

        open();

        try {
            MiningVector vector = activeStream.next();
            vector.setIndex(getVectorPos(vector.getIndex()));
            return vector;
        } catch (Exception e) {
            if (activeStreamIndex == streams.length - 1)
                throw new NullPointerException("Files run out.");

            activeStreamIndex++;
            activeStream = streams[activeStreamIndex];
            activeStream.reset();

            MiningVector vector = activeStream.next();
            vector.setIndex(getVectorPos(vector.getIndex()));
            return vector;
        }
    }

    /**
     * Returns a vector based on the specified index.
     * @param position - index of the vector
     * @return MiningVector
     */
    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException {

        open();
        if (position < 0) throw new OutOfMemoryError("Invalid index.");

        MiningVector vector = getVectorOfStream(position);
        vector.setIndex(position);
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
        throw new OutOfMemoryError("Invalid index.");
    }

    private int getVectorPos(int posOfStream) throws MiningException {

        int vectorsNumber = 0;
        for (int i = 0; i < activeStreamIndex; i++) {
            vectorsNumber += streams[i].getVectorsNumber();
        }
        return vectorsNumber + posOfStream;
    }

    // -----------------------------------------------------------------------
    //  Methods for changing the stream state
    // -----------------------------------------------------------------------

    /**
     * Opens the stream.
     */
    @Override
    public void open() throws MiningException {
        if (isOpen) return;

        isOpen = true;
        for (MiningInputStream stream : streams) {
            stream.open();
            stream.setParsingValues(parsingValues);
        }
    }

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
     * Closes the stream.
     */
    @Override
    public void close() throws MiningException {
        if (!isOpen) return;

        for (MiningInputStream stream : streams) {
            stream.close();
        }
        isOpen = false;
    }

     /**
     * Updates all streams.
     */
    @Override
    public void reset() throws MiningException {
        open();

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
    public MiningMultiStream deepCopy() throws MiningException {
        return new HorMultiStream(streams);
    }

}