package org.eltech.ddm.inputdata.file.csv;

import static org.junit.Assert.assertEquals;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;

import java.io.IOException;
import com.opencsv.exceptions.CsvException;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

/**
 * Tested MiningCsvStream class.
 * A class containing tests for checking the health of methods of the MiningCsvStream class for csv-files.
 *
 * @author Maxim Kolpasсhikov
 */

abstract class MiningCsvStreamTest {
    protected ELogicalData logicalData;
    protected MiningCsvStream csvStream;

    public void setup(String path, CsvParsingSettings settings) throws MiningException, IOException, CsvException {
        csvStream = new MiningCsvStream(path, settings);
        csvStream.open();
        logicalData = csvStream.getLogicalData();
    }

    abstract void logicalDataTest() throws MiningException;

    abstract void physicalDataTest() throws MiningException;

    protected void attrNumberTest() throws MiningException {
        // number of all attributes test
        assertEquals(5, logicalData.getAttributesNumber());

        // number of different attributes test
        assertEquals(0, logicalData.getAttributes(AttributeType.ordinal).size());
        assertEquals(5, logicalData.getAttributes(AttributeType.numerical).size());
        assertEquals(0, logicalData.getAttributes(AttributeType.categorical).size());
        assertEquals(0, logicalData.getAttributes(AttributeType.notSpecified).size());
    }

    protected void vecTest() throws MiningException, IOException, CsvException {
        // number of mining vectors test
        assertEquals(150, csvStream.getVectorsNumber());

        // values of the first vector Test (method 'next()')
        MiningVector miningVector = csvStream.next();
        assertEquals(5.1, miningVector.getValue(0), 0);
        assertEquals(3.5, miningVector.getValue(1), 0);
        assertEquals(1.4, miningVector.getValue(2), 0);
        assertEquals(0.2, miningVector.getValue(3), 0);
        assertEquals(1.0, miningVector.getValue(4), 0);

        //values of the second vector Test (method 'next()')
        miningVector = csvStream.next();
        assertEquals(4.9, miningVector.getValue(0), 0);
        assertEquals(3.0, miningVector.getValue(1), 0);
        assertEquals(1.4, miningVector.getValue(2), 0);
        assertEquals(0.2, miningVector.getValue(3), 0);
        assertEquals(1.0, miningVector.getValue(4), 0);

        // values of the fiftieth vector Test by getVector() (method 'getVector()')
        miningVector = csvStream.getVector(50);
        assertEquals(7.0, miningVector.getValue(0), 0);
        assertEquals(3.2, miningVector.getValue(1), 0);
        assertEquals(4.7, miningVector.getValue(2), 0);
        assertEquals(1.4, miningVector.getValue(3), 0);
        assertEquals(2.0, miningVector.getValue(4), 0);

        // values of the last vector Test
        miningVector = csvStream.getVector(csvStream.getVectorsNumber() - 1);
        assertEquals(5.9, miningVector.getValue(0), 0);
        assertEquals(3.0, miningVector.getValue(1), 0);
        assertEquals(5.1, miningVector.getValue(2), 0);
        assertEquals(1.8, miningVector.getValue(3), 0);
        assertEquals(3.0, miningVector.getValue(4), 0);
    }

    protected void close() throws IOException {
        csvStream.close();
    }
}