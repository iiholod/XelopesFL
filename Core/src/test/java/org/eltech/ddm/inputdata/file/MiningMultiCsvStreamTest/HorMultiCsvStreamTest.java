package org.eltech.ddm.inputdata.file.MiningMultiCsvStreamTest;

import com.opencsv.exceptions.CsvException;
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings;
import org.eltech.ddm.inputdata.file.csv.MultiCsvStream.HorMultiCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.EAttributeAssignmentSet;
import org.eltech.ddm.miningcore.miningdata.EDirectAttributeAssignment;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HorMultiCsvStreamTest extends MiningMultiCsvStreamTest{
    @Before
    public void setup() throws MiningException, IOException, CsvException {
        String[] files = new String[]{"../data/csv/iris_hor1.csv",
                                      "../data/csv/iris_hor2.csv",
                                      "../data/csv/iris_hor3.csv"};

        CsvParsingSettings settings = new CsvParsingSettings();
        settings.setSeparator(',');
        settings.setHeaderAvailability(true);

        setup(new HorMultiCsvStream(files, settings));
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
        assertEquals("sepallength numerical", physicalData.getAttribute(0).toString());
        assertEquals("sepalwidth numerical", physicalData.getAttribute(1).toString());
        assertEquals("petallength numerical", physicalData.getAttribute(2).toString());
        assertEquals("petalwidth numerical", physicalData.getAttribute(3).toString());
        assertEquals("iris-class numerical", physicalData.getAttribute(4).toString());
    }

    @After
    public void closeStream() throws IOException {
        close();
    }
}

