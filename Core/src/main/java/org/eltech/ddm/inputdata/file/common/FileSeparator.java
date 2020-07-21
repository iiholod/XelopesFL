package org.eltech.ddm.inputdata.file.common;

import org.eltech.ddm.inputdata.file.MiningFileStream;

import java.util.List;

/**
 * Separator interface describes the contract of separation file into
 * several ones.
 *
 * @author Evgenii Ray
 */
public interface FileSeparator<T extends MiningFileStream> {
    /**
     * Separate input file into several ones based on handler number.
     * Method should provide robust implementation with no data lost
     * or cut. Data should be correctly separated based on the file format
     *
     * @param filePath      - relative or absolute path
     * @param handlerNumber - number of files which is needed
     * @return - List of input streams
     */
    List<T> separate(String filePath, int handlerNumber);
}
