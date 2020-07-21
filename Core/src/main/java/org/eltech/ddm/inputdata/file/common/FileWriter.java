package org.eltech.ddm.inputdata.file.common;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileWriter {
    void write(List<String[]> data, String file) throws FileNotFoundException;

    void write(String[] data, String file) throws FileNotFoundException;
}
