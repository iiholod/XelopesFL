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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class MiningCsvStream extends MiningFileStream implements CloneableStream {

    /*
     * Required fields for using parser
     */
    private transient CSVReader parser;
    private final transient CsvParsingSettings settings;

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
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
     *
     * @param file - relative path to the data file
     * @param settings - parser setting to apply
     * @throws MiningException - in case of failure
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
            row = getRow(parser.readNext());
        } catch (CsvValidationException | IOException ex) {
            ex.printStackTrace();
            return null;
        }

        if (row != null) {
            double[] values = Stream.of(row).mapToDouble(value -> value == null ? 0d : Double.parseDouble(value)).toArray();
            MiningVector vector = new MiningVector(values);
            vector.setLogicalData(logicalData);
            vector.setIndex(++cursorPosition);
            return vector;
        }
        return null;
    }

    /**
     * Finds columns of rows and assigns them an ordinal number.
     * @param row - array of column values
     */
    private String[] getRow(String[] row) throws MiningException {
        if (row != null) {
            for (int i = 0; i < row.length; i++) {
                if (!isDigit(row[i]))
                    row[i] = getIndex(row[i], logicalData.getAttribute(i).getName());
            }
            return row;
        }
        return null;
    }

    /**
     * Sets the index for the string.
     * @param value - string to convert
     * @param attrName - name of attribute
     */
    private String getIndex(String value, String attrName) {
        if (parsingValues == null) {
            parsingValues = new ArrayList<>();
            parsingValues.add(new ParsingValues(attrName));
            parsingValues.get(0).add(value);
            return "1";
        }

        for(ParsingValues values : parsingValues) {
            if (values.getAttributeName().equals(attrName)) {
                if(values.contains(value)) {
                    return Double.toString(values.indexOf(value) + 1);
                } else {
                    values.add(value);
                    return Double.toString(values.size());
                }
            }
        }

        parsingValues.add(new ParsingValues(attrName, value));
        return "1";
    }

    private boolean isDigit(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return  false;
        }
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

    private void initData() throws MiningException {

        parser = getCsvParser();
        logicalData = new ELogicalData();
        physicalData = new EPhysicalData();
        attributeAssignmentSet = new EAttributeAssignmentSet();

        if (settings.getHeaderAvailability()) {
            initWithContext();
        } else {
            initWithoutContext();
        }
    }

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
     * {@inheritDoc}
     */
    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException{
        if (position < getCurrentPosition()) reset();
        return advancePosition(position);
    }

    /**
     * Advance current position forward to the  value passed in the
     * method.
     *
     * @param position - position to reach
     * @return - mining vector for reached position
     * @throws MiningException - in case of failure during file parsing
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

    public static MiningCsvStream createWithoutInit(String file) throws MiningException {
        return new MiningCsvStream(file);
    }
}