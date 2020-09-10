package org.eltech.ddm.inputdata.file.csv.MultiCsvStream;

import com.opencsv.exceptions.CsvException;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.MiningFileStream;
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings;
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;

import java.io.IOException;

/**
 * MiningMultiCsvStream class.
 * A class that allows you to read multiple csv-files 'vertically' or 'horizontally'.
 *
 * @author Maxim Kolpashikov
 */

public abstract class MiningMultiCsvStream extends MiningFileStream {
    protected boolean isOpen = false;

    protected int vectorsNumber = 0;
    protected MiningCsvStream[] streams;

    protected ELogicalData logicalData;
    protected EPhysicalData physicalData;

    // -----------------------------------------------------------------------
    //  Abstract methods
    // -----------------------------------------------------------------------

    public abstract void close() throws IOException;

    public abstract void open() throws IOException, MiningException, CsvException;

    public abstract void reset() throws IOException, CsvException, MiningException;

    public abstract MiningMultiCsvStream getCopy() throws MiningException, IOException, CsvException;

    public abstract MiningVector next() throws CsvException, IOException, MiningException ;

    public abstract MiningVector getVector(int pos) throws CsvException, IOException, MiningException ;

    // -----------------------------------------------------------------------
    //  Converting the files to stream
    // -----------------------------------------------------------------------

    /**
     * Returns an array of csv-streams with standard settings.
     *
     * @param files - array of csv-file names
     * @return array of csv-streams
     */
    protected MiningCsvStream[] getStreams(String[] files) throws MiningException, IOException {
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
    protected MiningCsvStream[] getStreams(String[] files, CsvParsingSettings settings)
            throws MiningException, IOException {
        MiningCsvStream[] streams = new MiningCsvStream[files.length];
        for (int i = 0; i < streams.length; i++) {
            streams[i] = new MiningCsvStream(files[i], settings);
        }
        return streams;
    }

    // -----------------------------------------------------------------------
    //  Get methods
    // -----------------------------------------------------------------------

    /**
     * Returns the number of vectors.
     *
     * @return the number of vectors
     */
    public int getVectorsNumber() throws CsvException, IOException, MiningException {
        open();
        return vectorsNumber;
    }

    /**
     * Returns a logical data.
     * @return logical data
     */
    public ELogicalData getLogicalData() throws CsvException, IOException, MiningException {
        open();
        return logicalData;
    }

    /**
     * Returns a physical data.
     * @return physical data
     */
    public EPhysicalData getPhysicalData() throws CsvException, IOException, MiningException {
        open();
        return physicalData;
    }
}
