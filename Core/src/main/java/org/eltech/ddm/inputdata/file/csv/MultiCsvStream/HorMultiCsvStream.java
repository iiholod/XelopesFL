package org.eltech.ddm.inputdata.file.csv.MultiCsvStream;

import com.opencsv.exceptions.CsvException;
import com.sun.istack.internal.NotNull;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings;
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;

import java.io.IOException;
import java.io.InvalidObjectException;


public class HorMultiCsvStream extends MiningMultiCsvStream {

    private int activeStreamIndex;
    private MiningCsvStream activeStream;

    // -----------------------------------------------------------------------
    //  Constructors
    // -----------------------------------------------------------------------

    /**
     * Accepts an array of csv file names without settings.
     * At this stage, an array of threads with standard settings is created.
     * @param files - array of csv-file names
     */
    public HorMultiCsvStream(@NotNull String[] files) throws MiningException, IOException, CsvException {
        init(getStreams(files));
    }

    /**
     * Accepts an array of csv file names with settings.
     * At this stage, an array of threads with custom settings is created.
     * @param files    - array of csv-file names
     * @param settings - settings for reading files
     */
    public HorMultiCsvStream(@NotNull String[] files, @NotNull CsvParsingSettings settings)
            throws MiningException, IOException, CsvException {
        init(getStreams(files, settings));
    }

    /**
     * Constructor that accepts an array of csv-file streams.
     * At this stage, the logical data of the csv-files that must match is checked.
     * @param streams - array of streams
     */
    public HorMultiCsvStream(@NotNull MiningCsvStream[] streams) throws MiningException, IOException, CsvException {
        init(streams);
    }

    /**
     * Initializes the class with input data, if the logical data is correct.
     * @param streams - array of streams
     */
    private void init(MiningCsvStream[] streams) throws MiningException, IOException, CsvException {
        if (logicalDataChecked(streams)) {
            thisInit(streams);
            superInit(streams);
        } else {
            throw new InvalidObjectException("Logical data does not match.");
        }
    }

    private void thisInit(MiningCsvStream[] streams) {
        this.activeStreamIndex = 0;
        this.activeStream = streams[0];
    }

    private void superInit(MiningCsvStream[] streams) throws CsvException, IOException, MiningException {
        super.streams = streams;
        super.vectorsNumber = calculateVecNumber();
        super.logicalData = activeStream.getLogicalData();
        super.physicalData = activeStream.getPhysicalData();
    }

    /**
     * Checks the values of logical data in files
     * @param streams - array of streams
     * @return <b>true</b> if the logical data is correct, <b>false</b> if the logical data is incorrect
     */
    private boolean logicalDataChecked(MiningCsvStream[] streams) throws MiningException {
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
    public MiningVector next() throws CsvException, IOException, MiningException {
        open();

        try {
            return activeStream.next();
        } catch (Exception e) {
            if (activeStreamIndex == streams.length - 1)
                throw new NullPointerException("Files run out.");

            activeStreamIndex++;
            activeStream = streams[activeStreamIndex];
            activeStream.reset();
            return activeStream.next();
        }
    }

    /**
     * Returns a vector based on the specified index.
     * @param pos - index of the vector
     * @return MiningVector
     */
    @Override
    public MiningVector getVector(int pos) throws CsvException, IOException, MiningException {
        open();

        if (pos < 0) throw new OutOfMemoryError("Invalid index.");
        pos = findVector(pos);
        return activeStream.getVector(pos);
    }

    /**
     * Finds a vector by index among all files.
     * Changes the active stream and index if necessary.
     * @param pos - index of the vector
     * @return vector number in the found file
     */
    private int findVector(int pos) throws MiningException, IOException, CsvException {
        int vectorsNumber = 0;
        for (int i = 0; i < streams.length; i++) {
            vectorsNumber += streams[i].getVectorsNumber();
            if (pos < vectorsNumber - 1) {
                int prevValue = vectorsNumber - streams[i].getVectorsNumber();
                activeStreamIndex = i;
                activeStream = streams[i];
                return (pos - prevValue);
            }
        }
        throw new OutOfMemoryError("Invalid index.");
    }

    // -----------------------------------------------------------------------
    //  Methods for changing the stream state
    // -----------------------------------------------------------------------

    /**
     * Opens the stream.
     */
    @Override
    public void open() throws IOException {
        if (isOpen) return;

        isOpen = true;
        for (MiningCsvStream stream : streams) {
            stream.open();
        }
    }

    /**
     * Counts the number of vectors.
     * @return the number of vectors
     */
    private int calculateVecNumber() throws MiningException, IOException, CsvException {
        int number = 0;
        for (MiningCsvStream stream : streams) {
            number += stream.getVectorsNumber();
        }
        return number;
    }

    /**
     * Closes the stream.
     */
    @Override
    public void close() throws IOException {
        if (!isOpen) return;

        for (MiningCsvStream stream : streams) {
            stream.close();
        }
        isOpen = false;
    }

    /**
     * Updates all streams.
     */
    @Override
    public void reset() throws IOException {
        open();

        activeStreamIndex = 0;
        activeStream = streams[0];
        for (int i = 0; i < streams.length; i++) {
            streams[i].reset();
        }
    }

    /**
     * returns a copy of the current stream
     * @return HorMultiCsvStream
     */
    @Override
    public MiningMultiCsvStream getCopy() throws MiningException, IOException, CsvException {
        return new HorMultiCsvStream(streams);
    }
}