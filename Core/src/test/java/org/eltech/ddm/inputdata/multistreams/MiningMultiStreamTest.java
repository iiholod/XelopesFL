package org.eltech.ddm.inputdata.multistreams;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import static org.junit.Assert.assertEquals;

/**
 * Tested MiningMultiCsvStream class.
 * A class containing tests for checking the health of methods of the MiningMultiCsvStream class for csv-files.
 *
 * @author Maxim Kolpashikov
 */

abstract class MiningMultiStreamTest {
    protected ELogicalData logicalData;
    protected EPhysicalData physicalData;
    protected MiningMultiStream multiCsvStream;

    public void setup(MiningMultiStream stream) throws MiningException {
        multiCsvStream = stream;
        logicalData = multiCsvStream.getLogicalData();
        physicalData = multiCsvStream.getPhysicalData();
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

    protected void vecTest() throws MiningException {
        // number of mining vectors test
        assertEquals(150, multiCsvStream.getVectorsNumber());

        // values of the first vector Test (method 'next()')
        MiningVector miningVector = multiCsvStream.next();
        assertEquals(0, miningVector.getIndex());
        assertEquals(5.1, miningVector.getValue(0), 0);
        assertEquals(3.5, miningVector.getValue(1), 0);
        assertEquals(1.4, miningVector.getValue(2), 0);
        assertEquals(0.2, miningVector.getValue(3), 0);
        assertEquals(1.0, miningVector.getValue(4), 0);

        // values of the second vector Test (method 'next()')
        miningVector = multiCsvStream.next();
        assertEquals(1, miningVector.getIndex());
        assertEquals(4.9, miningVector.getValue(0), 0);
        assertEquals(3.0, miningVector.getValue(1), 0);
        assertEquals(1.4, miningVector.getValue(2), 0);
        assertEquals(0.2, miningVector.getValue(3), 0);
        assertEquals(1.0, miningVector.getValue(4), 0);

        // values of the fiftieth vector Test by getVector() (method 'getVector()')
        miningVector = multiCsvStream.getVector(50);
        assertEquals(50, miningVector.getIndex());
        assertEquals(7.0, miningVector.getValue(0), 0);
        assertEquals(3.2, miningVector.getValue(1), 0);
        assertEquals(4.7, miningVector.getValue(2), 0);
        assertEquals(1.4, miningVector.getValue(3), 0);
        assertEquals(2.0, miningVector.getValue(4), 0);

        // values of the last vector Test
        miningVector = multiCsvStream.getVector(multiCsvStream.getVectorsNumber() - 1);
        assertEquals(149, miningVector.getIndex());
        assertEquals(5.9, miningVector.getValue(0), 0);
        assertEquals(3.0, miningVector.getValue(1), 0);
        assertEquals(5.1, miningVector.getValue(2), 0);
        assertEquals(1.8, miningVector.getValue(3), 0);
        assertEquals(3.0, miningVector.getValue(4), 0);
    }

    protected void close() throws MiningException {
        multiCsvStream.close();
    }
}

