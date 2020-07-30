package org.eltech.ddm.inputdata.file.MiningCsvStreamTest;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import com.opencsv.exceptions.CsvException;
import static org.junit.Assert.assertEquals;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings;
import org.eltech.ddm.miningcore.miningdata.EAttributeAssignmentSet;
import org.eltech.ddm.miningcore.miningdata.EDirectAttributeAssignment;

import java.io.IOException;

/**
 * Inheritor of the MiningCsvStreamTest class.
 *
 * A class containing tests for checking the efficiency
 * of methods of the MiningCsvStream class for 'csv' files without a header.
 *
 * @author Maxim Kolpashikov
 */

public class CsvStreamWithoutHeadersTest extends MiningCsvStreamTest {

    @Before
    public void setup() throws MiningException, IOException {
        CsvParsingSettings settings = new CsvParsingSettings();

        settings.setHeaderAvailability(false);
        settings.setSeparator(';');

        String path = "../data/csv/iris_etl.csv";
        setup(path, settings);
    }

    @Test
    public void vectorsTest() throws MiningException, IOException, CsvException {
        vecTest();
    }

    @Test
    public void attributeNumberTest() throws MiningException {
        attrNumberTest();
    }

    @Test
    public void logicalDataTest() throws MiningException {
        ELogicalData logicalData = super.logicalData;
        assertEquals("Attribute 1", logicalData.getAttribute(0).getName());
        assertEquals("Attribute 2", logicalData.getAttribute(1).getName());
        assertEquals("Attribute 3", logicalData.getAttribute(2).getName());
        assertEquals("Attribute 4", logicalData.getAttribute(3).getName());
        assertEquals("Attribute 5", logicalData.getAttribute(4).getName());
    }

    @Test
    public void physicalDataTest() throws MiningException {
        EAttributeAssignmentSet assignmentSet = super.csvStream.getAttributeAssignmentSet();
        assertEquals("Attribute 1 numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(0)).getAttribute().toString());
        assertEquals("Attribute 2 numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(1)).getAttribute().toString());
        assertEquals("Attribute 3 numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(2)).getAttribute().toString());
        assertEquals("Attribute 4 numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(3)).getAttribute().toString());
        assertEquals("Attribute 5 numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(4)).getAttribute().toString());
    }

    @After
    public void closeStream() throws IOException {
        close();
    }
}