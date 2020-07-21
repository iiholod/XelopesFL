package org.eltech.ddm.inputdata.file.csv;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.eltech.ddm.inputdata.file.common.FileWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class CsvFileWriter implements FileWriter {

    private static final String CHUNK_TEMPLATE = "temp%d.csv";
    private static final String DEFAULT_NAME = "normalized.csv";
    private CsvWriterSettings writerSettings = getDefaultWriterSettings();
    private CsvWriter writer;
    private String[] headers;

    public CsvFileWriter(String[] headers) {
        this.headers = headers;
    }

    public CsvFileWriter() {
    }

    @Override
    public void write(List<String[]> data, String file) throws FileNotFoundException {
        if (headers != null) {
            writerSettings.setColumnReorderingEnabled(true);
            writerSettings.setHeaders(headers);
        }
        CsvWriter writer = new CsvWriter(new FileOutputStream(file == null ? DEFAULT_NAME : file), writerSettings);
        if (headers != null) {
            writer.writeHeaders(headers);
        }
        data.forEach(writer::writeRow);
    }

    public void write(String[] data, String file) throws FileNotFoundException {
        if (writer == null) {
            writer = new CsvWriter(new FileOutputStream(file == null ? DEFAULT_NAME : file), writerSettings);
            writer.writeHeaders(headers);
        }
        writer.writeRow(data);
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
