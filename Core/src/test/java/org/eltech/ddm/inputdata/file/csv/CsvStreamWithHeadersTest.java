package org.eltech.ddm.inputdata.file.csv;

import java.io.IOException;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import com.opencsv.exceptions.CsvException;
import static org.junit.Assert.assertEquals;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EAttributeAssignmentSet;
import org.eltech.ddm.miningcore.miningdata.EDirectAttributeAssignment;


/**
 * Inheritor of the MiningCsvStreamTest class.
 * A class containing tests for checking the efficiency
 *      of methods of the MiningCsvStream class for csv-files with a header.
 *
 * @author Maxim Kolpasсhikov
 */

public class CsvStreamWithHeadersTest extends MiningCsvStreamTest {

    @Before
    public void setup() throws MiningException, IOException, CsvException {
        CsvParsingSettings settings = new CsvParsingSettings();

        settings.setHeaderAvailability(true);
        settings.setSeparator(',');

        String path = "../data/csv/iris.csv";
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
        assertEquals("sepallength", logicalData.getAttribute(0).getName());
        assertEquals("sepalwidth", logicalData.getAttribute(1).getName());
        assertEquals("petallength", logicalData.getAttribute(2).getName());
        assertEquals("petalwidth", logicalData.getAttribute(3).getName());
        assertEquals("iris-class", logicalData.getAttribute(4).getName());
    }

    @Test
    public void physicalDataTest() throws MiningException {
        EAttributeAssignmentSet assignmentSet = csvStream.getAttributeAssignmentSet();
        assertEquals("sepallength numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(0)).getAttribute().toString());
        assertEquals("sepalwidth numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(1)).getAttribute().toString());
        assertEquals("petallength numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(2)).getAttribute().toString());
        assertEquals("petalwidth numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(3)).getAttribute().toString());
        assertEquals("iris-class numerical",
                ((EDirectAttributeAssignment) assignmentSet.getAttributeAssignment(4)).getAttribute().toString());
    }

    @After
    public void closeStream() throws IOException {
        close();
    }
}
