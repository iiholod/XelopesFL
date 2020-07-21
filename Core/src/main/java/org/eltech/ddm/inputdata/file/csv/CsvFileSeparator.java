package org.eltech.ddm.inputdata.file.csv;

import com.univocity.parsers.common.AbstractWriter;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.eltech.ddm.inputdata.file.common.FileSeparator;
import org.eltech.ddm.miningcore.MiningException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Csv file separator. Separate input CSV files into several ones
 *
 * @author Evgenii Ray
 */
public class CsvFileSeparator implements FileSeparator<MiningCsvStream> {

    private static final Logger LOGGER = Logger.getLogger(CsvFileSeparator.class.getName());

    private static final String CHUNK_TEMPLATE = "temp%d.csv";
    private CsvWriterSettings writerSettings = getDefaultWriterSettings();

    @Override
    public List<MiningCsvStream> separate(String filePath, int handlerNumber) {
        LOGGER.info("Initiating data separation...");
        CsvParserSettings settings = MiningCsvStream.getDefaultSettings();
        CsvParser parser = new CsvParser(settings);

        List<DataOutputStream> list = new ArrayList<>(handlerNumber);
        LOGGER.info("Number of files to create: " + handlerNumber);

        for (int i = 0; i < handlerNumber; i++) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(String.format(CHUNK_TEMPLATE, i));
            } catch (FileNotFoundException e) {
                LOGGER.log(Level.SEVERE, e, () -> "During file creation an exception occurred");
            }
            LOGGER.info(String.format("Temporary file: %s is created", String.format(CHUNK_TEMPLATE, i)));
            list.add(new DataOutputStream(new BufferedOutputStream(fos)));
        }

        parser.beginParsing(getReader(filePath));
        String[] headers = parser.getContext().parsedHeaders();
        parser.stopParsing();

        List<CsvWriter> streams = list.stream()
                .map(OutputStreamWriter::new)
                .map(writer -> new CsvWriter(writer, writerSettings))
                .peek(writer -> writer.writeHeaders(headers))
                .collect(Collectors.toList());

        String[] row;
        Iterator<CsvWriter> iterator = streams.iterator();

        LOGGER.info("Starting writing data to temporary files");
        parser = new CsvParser(settings);
        parser.beginParsing(getReader(filePath));
        while ((row = parser.parseNext()) != null) {
            if (!iterator.hasNext()) {
                iterator = streams.iterator();
            }
            iterator.next().writeRow(row);
        }
        parser.stopParsing();
        streams.forEach(AbstractWriter::close);
        LOGGER.info("Data is successfully written");
        return IntStream.range(0, handlerNumber)
                .mapToObj(index -> {
                    try {
                        return new MiningCsvStream(String.format(CHUNK_TEMPLATE, index), null, true);
                    } catch (MiningException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }


    /**
     * Utility method for getting  input stream of the resource;
     *
     * @return - reader for parser
     */
    private Reader getReader(String fileName) {
        return new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName));
    }


    /**
     * Provides default configuration for CSV writer
     *
     * @return - default config for writer
     */
    private static CsvWriterSettings getDefaultWriterSettings() {
        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setNullValue("0");
        settings.setEmptyValue("0");
        settings.setSkipEmptyLines(true);
        return settings;
    }


}
