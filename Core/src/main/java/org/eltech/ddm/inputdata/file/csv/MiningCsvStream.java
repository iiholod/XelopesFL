package org.eltech.ddm.inputdata.file.csv;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.stream.Stream;

import java.io.IOException;
import java.io.FileNotFoundException;

import com.opencsv.exceptions.CsvException;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import com.opencsv.CSVReader;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.miningdata.*;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.inputdata.file.MiningFileStream;
import org.eltech.ddm.inputdata.file.common.CloneableStream;

public class MiningCsvStream extends MiningFileStream implements CloneableStream {

    /*
     * Required fields for using parser
     */
    private boolean delayed;
    private List<String>[] pv;
    private List<ParsingValues> parsingValues;

    private transient CSVReader parser;
    private transient CsvParsingSettings settings;

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
     * @param file - relative path to the data file
     */
    public MiningCsvStream(String file) {
        super(file);
        this.settings = getDefaultSettings();
        this.delayed = true;
        this.open = false;
    }

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
     * @param file - relative path to the data file
     * @param settings - parser setting to apply
     * @throws MiningException - in case of failure
     */
    public MiningCsvStream(String file, CsvParsingSettings settings) throws MiningException, IOException {
        super(file);
        this.settings = settings == null ? getDefaultSettings() : settings;
        if (logicalData == null) {
            physicalData = recognize();
        }
    }

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
     * @param file     - relative path to the data file
     * @param settings - parser setting to apply
     */
    public MiningCsvStream(String file,
                           CsvParsingSettings settings,
                           ELogicalData logicalData,
                           EPhysicalData physicalData) {
        super(file, logicalData);
        this.physicalData = physicalData;
        this.settings = settings == null ? getDefaultSettings() : settings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MiningVector readPhysicalRecord() throws IOException, CsvValidationException, MiningException {
        open();
        String[] row = getRow(parser.readNext());
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
    @Override
    public void reset() throws FileNotFoundException {
        resetCurrentPosition();
        parser = getCsvParser();
        open = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() throws FileNotFoundException {
        if (isOpen()) return;

        if (delayed) {
            try {
                this.delayed = false;
                physicalData = recognize();
                return;
            } catch (MiningException | IOException e) {
                e.printStackTrace();
            }
        }
        this.open = true;
        if (settings == null) {
            settings = getDefaultSettings();
        }
        this.parser = getCsvParser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        this.open = false;
        this.parser.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EPhysicalData recognize() throws MiningException, IOException {
        if (logicalData == null && physicalData == null && attributeAssignmentSet == null) {
            open();
            initData();
            close();
            return physicalData;
        }
        return physicalData;
    }


    private void initData() throws MiningException, IOException {
        logicalData = new ELogicalData();
        physicalData = new EPhysicalData();
        attributeAssignmentSet = new EAttributeAssignmentSet();

        if (settings.getHeaderAvailability()) {
            initWithContext();
        } else {
            initWithoutContext();
        }
    }

    private void initWithContext() throws MiningException, IOException {
        String[] headers = getContext();
        for(int i=0;i<headers.length;i++) {
            String attrName = headers[i];
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

    private void initWithoutContext() throws MiningException, IOException {
        int attributeNumber = parser.peek().length;
        for (int i = 1; i <= attributeNumber; i++) {
            ELogicalAttribute la =
                    new ELogicalAttribute("Attribute " + Integer.toString(i), AttributeType.numerical);
            PhysicalAttribute pa =
                    new PhysicalAttribute("Attribute " + Integer.toString(i), AttributeType.numerical, AttributeDataType.doubleType);
            EDirectAttributeAssignment da = new EDirectAttributeAssignment();
            logicalData.addAttribute(la);
            physicalData.addAttribute(pa);
            da.addLogicalAttribute(la);
            da.setAttribute(pa);
            attributeAssignmentSet.addAssignment(da);
        }
    }

    public void setParsingValues(List<ParsingValues> parsingValues) {
        this.parsingValues = parsingValues;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException, IOException, CsvException {
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
    private MiningVector advancePosition(int position) throws MiningException, IOException, CsvException {
        MiningVector mv;
        do {
            mv = next();
        }
        while ((mv != null) && ( getCurrentPosition() != position));
        return mv;
    }

    @Override
    public MiningFileStream deepCopy() {
        MiningCsvStream stream = new MiningCsvStream(this.fileName, this.settings, logicalData, physicalData);
        stream.setVectorsNumber(this.vectorsNumber);
        return stream;
    }

    private void setVectorsNumber(int number) {
        this.vectorsNumber = number;
    }

    @Override
    public String toString() {
        return "MiningCsvStream{" +
                ", currentPosition=" + cursorPosition +
                '}';
    }

    @Override
    public int getVectorsNumber() throws IOException, CsvException, MiningException {
        int cursorPos = getCurrentPosition();

        reset();
        int vecNumber = parser.readAll().size();
        reset();

        for(int i = 0; i<cursorPos; i++)
            next();

        return vecNumber;
    }

    public static CsvParsingSettings getDefaultSettings() {
        return new CsvParsingSettings();
    }

    private CSVReader getCsvParser() throws FileNotFoundException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(settings.getSeparator()).build();
        return new CSVReaderBuilder(getReader())
                                    .withCSVParser(csvParser)
                                    .withSkipLines(settings.getSkipLines())
                                    .build();
    }

    private String[] getContext() throws IOException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(settings.getSeparator()).build();
        return new CSVReaderBuilder(getReader())
                                    .withCSVParser(csvParser)
                                    .build()
                                    .peek();
    }

    public static MiningCsvStream createWithoutInit(String file) {
        return new MiningCsvStream(file);
    }
}