package org.eltech.ddm.inputdata.multistreams;

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
 * @author Maxim Kolpashikov
 */

public class VerMultiStream extends MiningMultiStream {

    // -----------------------------------------------------------------------
    //  Constructor
    // -----------------------------------------------------------------------

    /**
     * Constructor that accepts an array of csv-file streams.
     * At this stage, the logical data of the csv-files that must match is checked.
     * @param streams - array of streams
     */
    public VerMultiStream(MiningInputStream[] streams) throws MiningException {
        if (streams == null) throw  new NullPointerException("The stream array is empty.");
        init(streams);
    }

    /**
     * Initializes the class with input data, if the logical data is correct.
     * @param streams - array of streams
     */
    private void init(MiningInputStream[] streams) throws MiningException {
        if (vectorsNumberChecked(streams)) {
            super.streams = streams;
            super.logicalData = collectLogicalData();
            super.physicalData = collectPhysicalData();
            super.vectorsNumber = streams[0].getVectorsNumber();
            this.parsingValues = new ArrayList();
        } else {
            try {
                throw new InvalidObjectException("There are different number of vectors in the files.");
            } catch (InvalidObjectException ex) {
                ex.printStackTrace();
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
    public MiningVector next() throws MiningException {

        open();
        int pos = -1;
        int valuesNumber = 0;
        List<double[]> values = new ArrayList<>();

        try {
            for (MiningInputStream stream : streams) {

                MiningVector mv = stream.next();
                if (pos == -1) pos = mv.getIndex();
                double[] vectorValues = mv.getValues();

                valuesNumber += vectorValues.length;
                values.add(vectorValues);
            }
        } catch (Exception e) {
            throw new OutOfMemoryError("Vectors are out.");
        }

        double[] allValues = collectValues(values, valuesNumber);
        MiningVector miningVector = new MiningVector(allValues);
        miningVector.setLogicalData(logicalData);
        miningVector.setIndex(pos);
        return miningVector;
    }

    @Override
    public MiningVector readPhysicalRecord()  {
        try {
            return next();
        } catch (MiningException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a vector based on the specified index.
     * @param pos - index of the vector
     * @return MiningVector
     */
    @Override
    public MiningVector getVector(int pos) throws MiningException {

        open();
        if (pos < 0) throw new OutOfMemoryError("Invalid index.");

        int valuesNumber = 0;
        List<double[]> values = new ArrayList<>();
        try {
            for (MiningInputStream stream: streams) {
                MiningVector mv = stream.getVector(pos);
                double[] vectorValues = mv.getValues();

                valuesNumber += vectorValues.length;
                values.add(vectorValues);
            }
        } catch (Exception e) {
            throw new OutOfMemoryError("Invalid index.");
        }

        double[] allValues = collectValues(values, valuesNumber);
        MiningVector miningVector =  new MiningVector(allValues);
        miningVector.setLogicalData(logicalData);
        miningVector.setIndex(pos);
        return miningVector;
    }

    @Override
    protected MiningVector movePhysicalRecord(int position)  {

        try {
            return getVector(position);
        } catch (MiningException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Combines values from all received vectors.
     * @param values - a list containing arrays with the values of individual vectors
     * @param valuesNumber - number of all values
     * @return an array with all the values
     */
    private double[] collectValues(List<double[]> values, int valuesNumber) {
        int i = 0;
        double[] allValues = new double[valuesNumber];
        for (double[] val : values) {
            for (double v : val) {
                allValues[i] = v;
                i++;
            }
        }
        return allValues;
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
        return new VerMultiStream(streams);
    }

}
