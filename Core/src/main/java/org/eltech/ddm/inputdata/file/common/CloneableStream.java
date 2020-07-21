package org.eltech.ddm.inputdata.file.common;

import org.eltech.ddm.inputdata.file.MiningFileStream;
import org.eltech.ddm.miningcore.MiningException;

/**
 * Interface specifies possibility that the current Input stream
 * can be cloned
 *
 * @author eray
 */
public interface CloneableStream {
    /**
     * Creates a deep copy of the stream. This method returns completely
     * new copy of the current stream with a inner business data
     *
     * @return - new MiningFileStream with copied properties
     * @throws MiningException - in case of error
     */
    MiningFileStream deepCopy() throws MiningException;
}
