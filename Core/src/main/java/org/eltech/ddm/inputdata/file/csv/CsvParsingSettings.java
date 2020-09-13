package org.eltech.ddm.inputdata.file.csv;

/**
 * Class of csv-parser setting.
 *
 * The settings include the type of separator and the presence of a header.
 *
 * @author Maxim Kolpashikov
 */

public class CsvParsingSettings {
    private char separator = ',';

    private boolean headerAvailability = true;

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public void setHeaderAvailability(boolean headerAvailability) {
        this.headerAvailability = headerAvailability;
    }

    public char getSeparator() {
        return separator;
    }

    public int getSkipLines() {
        return headerAvailability ? 1 : 0;
    }

    public boolean getHeaderAvailability() {
        return headerAvailability;
    }
}
