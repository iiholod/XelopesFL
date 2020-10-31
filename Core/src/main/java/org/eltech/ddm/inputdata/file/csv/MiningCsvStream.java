package org.eltech.ddm.inputdata.file.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.MiningFileStream;
import org.eltech.ddm.inputdata.file.common.CloneableStream;
import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.*;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

import java.io.IOException;
import java.util.Objects;

public class MiningCsvStream extends MiningFileStream implements CloneableStream {

    /*
     * Required fields for using parser
     */
    private transient CSVReader parser;
    private final transient CsvParsingSettings settings;

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     * @param file - relative path to the data file
     */
    public MiningCsvStream(String file) throws MiningException {
        super(file);
        this.settings = getDefaultSettings();

        open();
        recognize();
    }

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     * @param file - relative path to the data file
     * @param settings - parser setting to apply
     */
    public MiningCsvStream(String file, CsvParsingSettings settings) throws MiningException {
        super(file);
        this.settings = settings == null ? getDefaultSettings() : settings;

        open();
        recognize();
    }

    public MiningCsvStream(String file, ELogicalData logicalData, CsvParsingSettings settings) throws MiningException {
        super(file, logicalData);
        this.settings = settings == null ? getDefaultSettings() : settings;

        open();
        recognize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MiningVector readPhysicalRecord() throws MiningException {

        String[] row;
        try {
            row = parser.readNext();
        } catch (CsvValidationException | IOException ex) {
            ex.printStackTrace();
            return null;
        }

        if (row != null) {
            double[] values = new double[row.length];

            for (int i = 0; i < row.length; i++) {
                ELogicalAttribute attr = logicalData.getAttribute(i);
                ECategoricalAttributeProperties catPr = attr.getCategoricalProperties();
                if(catPr == null)
                    catPr = new ECategoricalAttributeProperties();

                try {
                    values[i] = Double.parseDouble(row[i]);
                } catch (Exception ex) {
                    Integer index = catPr.getIndex(row[i]);
                    if (index == null) {
                        catPr.addCategory(row[i], CategoryProperty.valid);
                        values[i] = 0.0;
                    } else {
                        values[i] = index;
                    }
                }
            }

            MiningVector vector = new MiningVector(values);
            vector.setLogicalData(logicalData);
            vector.setIndex(++cursorPosition);
            return vector;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void reset() throws MiningException {
        super.reset();

        try {
            if (parser != null) parser.close();
            parser = getCsvParser();
        } catch (IOException ex) {
            parser = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws MiningException {
        super.close();

        try {
            if (parser != null) parser.close();
        } catch (Exception ex) {
            throw new MiningDataException("Can't close CSV stream from file: " + path);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    synchronized public EPhysicalData recognize() throws MiningException {
        if (logicalData == null && physicalData == null && attributeAssignmentSet == null) {
            initData();
            return physicalData;
        }
        return physicalData;
    }

    /**
     * Initializes meta data.
     */
    private void initData() throws MiningException {

        parser = getCsvParser();
        logicalData = new ELogicalData();
        if (physicalData == null)
            physicalData = new EPhysicalData();
        if (attributeAssignmentSet == null)
            attributeAssignmentSet = new EAttributeAssignmentSet();

        if (settings.getHeaderAvailability()) {
            initWithContext();
        } else {
            initWithoutContext();
        }

        initAttributesCategory();
    }

    /**
     * Initializes dataset with context.
     */
    private void initWithContext() throws MiningException {
        String[] headers = getContext();
        for (String attrName : headers) {
            if (Objects.nonNull(attrName)) {
                ELogicalAttribute la = new ELogicalAttribute(attrName, AttributeType.numerical);
                PhysicalAttribute pa = new PhysicalAttribute(attrName, AttributeType.numerical, AttributeDataType.doubleType);
                EDirectAttributeAssignment da = new EDirectAttributeAssignment();
                logicalData.addAttribute(la);
                physicalData.addAttribute(pa);
                da.addLogicalAttribute(la);
                da.setAttribute(pa);
                attributeAssignmentSet.addAssignment(da);
            }
        }
    }

    /**
     * Initializes dataset without context.
     */
    private void initWithoutContext() throws MiningException {

        int attributeNumber;
        try {
            attributeNumber = parser.peek().length;
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        for (int i = 1; i <= attributeNumber; i++) {
            ELogicalAttribute la =
                    new ELogicalAttribute("Attribute " + i, AttributeType.numerical);
            PhysicalAttribute pa =
                    new PhysicalAttribute("Attribute " + i, AttributeType.numerical, AttributeDataType.doubleType);
            EDirectAttributeAssignment da = new EDirectAttributeAssignment();
            logicalData.addAttribute(la);
            physicalData.addAttribute(pa);
            da.addLogicalAttribute(la);
            da.setAttribute(pa);
            attributeAssignmentSet.addAssignment(da);
        }
    }

    /**
     * Searches for categorical data in a dataset.
     */
    private void initAttributesCategory() throws MiningException {

        String[] row;
        try {
            row = parser.peek();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        for(int i = 0; i< row.length;i++) {
            try {
                Double.parseDouble(row[i]);
            } catch (Exception ex) {
                logicalData.getAttribute(i).setAttributeType(AttributeType.categorical);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException{
        if (position <= getCurrentPosition()) reset();
        return advancePosition(position);
    }

    /**
     * Advance current position forward to the  value passed in the
     * method.
     *
     * @param position - position to reach
     * @return - mining vector for reached position
     */
    private MiningVector advancePosition(int position) throws MiningException {
        MiningVector mv;
        do {
            mv = next();
        }
        while ((mv != null) && (getCurrentPosition() != position));
        return mv;
    }

    @Override
    public MiningFileStream deepCopy() throws MiningException {
        MiningCsvStream stream = new MiningCsvStream(path, logicalData, settings);
        stream.setVectorsNumber(vectorsNumber);
        return stream;
    }

    private void setVectorsNumber(int number) {
        vectorsNumber = number;
    }

    @Override
    public String toString() {
        return "MiningCsvStream{" +
                ", currentPosition=" + cursorPosition +
                '}';
    }

    public static CsvParsingSettings getDefaultSettings() {
        return new CsvParsingSettings();
    }

    private CSVReader getCsvParser() {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(settings.getSeparator()).build();

        return new CSVReaderBuilder(reader)
                .withCSVParser(csvParser)
                .withSkipLines(settings.getSkipLines())
                .build();
    }

    private String[] getContext() {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(settings.getSeparator()).build();

        try {
            return new CSVReaderBuilder(reader)
                    .withCSVParser(csvParser)
                    .build()
                    .peek();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}