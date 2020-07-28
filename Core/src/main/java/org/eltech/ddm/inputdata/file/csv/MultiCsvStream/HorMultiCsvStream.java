package org.eltech.ddm.inputdata.file.csv.MultiCsvStream;

import com.sun.istack.internal.NotNull;
import com.opencsv.exceptions.CsvException;
import javax.management.RuntimeErrorException;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InvalidObjectException;

import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.PhysicalData;



public class HorMultiCsvStream implements MultiCsvStream {

    private boolean isOpen;

    private int vectorsNumber;
    private MiningCsvStream[] streams;

    private int activeStreamIndex;
    private MiningCsvStream activeStream;

    private ELogicalData logicalData;
    private PhysicalData physicalData;
    private CsvParsingSettings settings;

    // -----------------------------------------------------------------------
    //  Constructors
    // -----------------------------------------------------------------------

    /**
     * Accepts an array of csv file names without settings.
     * At this stage, an array of threads with standard settings is created.
     *
     * @param files - array of csv-file names
     */
    public HorMultiCsvStream(@NotNull String[] files) throws MiningException, IOException {
        init(getStreams(files));
    }

    /**
     * Accepts an array of csv file names with settings.
     * At this stage, an array of threads with custom settings is created.
     *
     * @param files    - array of csv-file names
     * @param settings - settings for reading files
     */
    public HorMultiCsvStream(@NotNull String[] files, @NotNull CsvParsingSettings settings)
            throws MiningException, IOException {
        init(getStreams(files, settings));
    }

    /**
     * Constructor that accepts an array of csv-file streams.
     * At this stage, the logical data of the csv-files that must match is checked.
     *
     * @param streams - array of streams
     */
    public HorMultiCsvStream(@NotNull MiningCsvStream[] streams) throws MiningException, InvalidObjectException {
        init(streams);
    }

    /**
     * Initializes the class with input data, if the logical data is correct.
     *
     * @param streams - array of streams
     */
    private void init(MiningCsvStream[] streams) throws MiningException, InvalidObjectException {
        if (logicalDataChecked(streams)) {
            this.isOpen = false;

            this.activeStreamIndex = 0;
            this.activeStream = streams[0];

            this.streams = streams;
            this.settings = activeStream.getSettings();
        } else {
            throw new InvalidObjectException("logical data does not match");
        }
    }

    /**
     * Returns an array of csv-streams with standard settings.
     *
     * @param files - array of csv-file names
     * @return array of csv-streams
     */
    private MiningCsvStream[] getStreams(String[] files) throws MiningException, IOException {
        MiningCsvStream[] streams = new MiningCsvStream[files.length];
        for (int i = 0; i < streams.length; i++) {
            streams[i] = new MiningCsvStream(files[i], new CsvParsingSettings());
        }
        return streams;
    }

    /**
     * Returns an array of csv-streams with custom settings.
     *
     * @param files    - array of csv-file names
     * @param settings - settings for reading files
     * @return array of csv-streams
     */
    private MiningCsvStream[] getStreams(String[] files, CsvParsingSettings settings)
            throws MiningException, IOException {
        MiningCsvStream[] streams = new MiningCsvStream[files.length];
        for (int i = 0; i < streams.length; i++) {
            streams[i] = new MiningCsvStream(files[i], settings);
        }
        return streams;
    }

    /**
     * Checks the values of logical data in files
     *
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
     *
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
     *
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
     *
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
    public void open() throws IOException, MiningException, CsvException {
        if (isOpen) return;

        for (MiningCsvStream stream : streams) {
            stream.open();
        }
        isOpen = true;
        vectorsNumber = calculateVecNumber();
        logicalData = activeStream.getLogicalData();
        physicalData = activeStream.getPhysicalData();
    }

    /**
     * Counts the number of vectors.
     *
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
    public void reset() throws IOException, CsvException, MiningException {
        open();

        activeStreamIndex = 0;
        activeStream = streams[0];
        for (int i = activeStreamIndex; i < streams.length; i++) {
            streams[i].reset();
        }
    }

    // -----------------------------------------------------------------------
    //  Get methods
    // -----------------------------------------------------------------------

    /**
     * returns a copy of the current stream
     * @return HorMultiCsvStream
     */
    public HorMultiCsvStream getCopy() throws MiningException, IOException, CsvException {
        open();
        return new HorMultiCsvStream(streams);
    }

    /**
     * Returns the number of vectors.
     *
     * @return the number of vectors
     */
    public int getVectorsNumber() throws CsvException, IOException, MiningException {
        open();
        return this.vectorsNumber;
    }

    /**
     * Returns settings.
     *
     * @return setting of the streams
     */
    public CsvParsingSettings getSettings() throws CsvException, IOException, MiningException {
        open();
        return this.settings;
    }

    /**
     * Returns a logical data.
     * @return logical data
     */
    public ELogicalData getLogicalData() throws CsvException, IOException, MiningException {
        open();
        return this.logicalData;
    }

    /**
     * Returns a physical data.
     * @return physical data
     */
    public PhysicalData getPhysicalData() throws CsvException, IOException, MiningException {
        open();
        return physicalData;
    }
}