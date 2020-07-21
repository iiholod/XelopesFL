package org.eltech.ddm.inputdata.file.csv;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.MiningFileStream;
import org.eltech.ddm.inputdata.file.RowCountProcessor;
import org.eltech.ddm.inputdata.file.common.CloneableStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.*;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import java.io.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * This class is an adapter for CSV Parser from Univocity Team.
 * Basically, it encapsulates logic for reading CSV data effectively
 * without loading it into memory.
 * Parser could be configured in a different way such as:
 * <p>
 * 1) Read rows of the file (Horizontal Separation)
 * 2) Read columns of the file (Vertical Separation)
 * 3) Batching input in case of the large data
 * 4) Separate thread option in order not to lock the main thread
 * <p>
 * All settings could be found in {@link CsvParserSettings}
 * For different type of row processing please see implementation of
 *
 * @author etitkov
 * @see com.univocity.parsers.common.processor.RowProcessor
 * @see com.univocity.parsers.common.Context
 * @see com.univocity.parsers.common.processor.RowListProcessor
 * @see com.univocity.parsers.common.processor.ColumnProcessor
 */
public class MiningCsvStream extends MiningFileStream implements CloneableStream {

    /*
     * Required fields for using parser
     */
    private transient CsvParser parser;
    private transient CsvParserSettings settings;
    private String absolutePath;
    private static final Logger LOGGER = Logger.getLogger(MiningCsvStream.class.getName());
    private boolean delayed;

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
     * @param file     - relative path to the data file
     * @param settings - parser setting to apply
     * @throws MiningException - in case of failure
     */
    public MiningCsvStream(String file, CsvParserSettings settings, boolean absolute) throws MiningException {
        super(absolute ? null : file);
        absolutePath = absolute ? file : null;
        this.settings = settings == null ? getDefaultSettings() : settings;
        if (logicalData == null) {
            physicalData = recognize();
        }
    }

    public MiningCsvStream() {
    }

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
     * @param file - relative path to the data file
     */
    private MiningCsvStream(String file, boolean absolute) {
        super(absolute ? null : file);
        absolutePath = absolute ? file : null;
        this.settings = getDefaultSettings();
        this.delayed = true;
        this.open = false;
    }

    /**
     * Default constructor with configuration provider. If configuration is {@code null}
     * the the default one will be used instead;
     *
     * @param file     - relative path to the data file
     * @param settings - parser setting to apply
     */
    public MiningCsvStream(String file,
                           CsvParserSettings settings,
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
    public MiningVector readPhysicalRecord() {
        open();
        String[] row = parser.parseNext();
        if (row != null) {
            double[] values = Stream.of(row).mapToDouble(value -> value == null ? 0d : Double.valueOf(value)).toArray();
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
    @Override
    public void reset() {
        cursorPosition = -1;
        parser.stopParsing();
        try {
            parser.beginParsing(getReader());
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e, () -> "Exception occurred while opening a file for parsing");
        }
        open = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        if (!isOpen()) {
            if (delayed) {
                try {
                    this.delayed = false;
                    physicalData = recognize();
                } catch (MiningException e) {
                    e.printStackTrace();
                }
            }
            this.open = true;
            if (settings == null) {
                settings = getDefaultSettings();
            }
            this.parser = new CsvParser(settings);
            try {
                this.parser.beginParsing(getReader());
            } catch (FileNotFoundException e) {
                LOGGER.log(Level.SEVERE, e, () -> "Exception occurred while opening a file for parsing");
            }
        }
    }

    /**
     * Utility method for getting  input stream of the resource;
     *
     * @return - reader for parser
     */
    private Reader getReader() throws FileNotFoundException {
        return absolutePath == null
                ? new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName))
                : new InputStreamReader(new FileInputStream(new File(absolutePath)));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.open = false;
        this.parser.stopParsing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EPhysicalData recognize() throws MiningException {
        if (logicalData == null && physicalData == null && attributeAssignmentSet == null) {
            open();
            initData();
            close();
            return physicalData;
        }
        return physicalData;
    }


    private void initData() throws MiningException {
        logicalData = new ELogicalData();
        physicalData = new EPhysicalData();
        attributeAssignmentSet = new EAttributeAssignmentSet();

        settings.setNumberOfRecordsToRead(1);
        String[] headers = parser.getContext().parsedHeaders();
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
        settings.setNumberOfRecordsToRead(-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MiningVector movePhysicalRecord(int position) throws MiningException {
        if (getCurrentPosition() > position) {
            reset();
        }
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
        while ((mv != null) && (getCurrentPosition() < position));
        return mv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVectorsNumber() {
        if (vectorsNumber <= 0) {
            RowCountProcessor processor = new RowCountProcessor();
            CsvParserSettings settings = new CsvParserSettings();
            settings.setProcessor(processor);
            CsvParser parser = new CsvParser(settings);
            try {
                parser.parse(getReader());
            } catch (FileNotFoundException e) {
                LOGGER.log(Level.SEVERE, e, () -> "Exception occurred while opening a file for parsing");
            }
            vectorsNumber = processor.getRowCount();
        }
        return vectorsNumber;
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

    public static CsvParserSettings getDefaultSettings() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        return settings;
    }


    public static MiningCsvStream createWithoutInit(String file, boolean absolute) {
        return new MiningCsvStream(file, absolute);
    }
}