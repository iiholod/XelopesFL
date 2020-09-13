/*
XELOPES Java Version 3.2
Copyright (C) 2008  prudsys AG

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
version 2 as published by the Free Software Foundation.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package org.eltech.ddm.inputdata.file;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.csv.ParsingValues;
import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;

import java.io.*;
import java.util.List;

/**
 * Mining input stream class for files.
 *
 * For special file formats this class should be
 * extended.
 */
public abstract class MiningFileStream extends MiningInputStream {
    // -----------------------------------------------------------------------
    //  Variables definitions
    // -----------------------------------------------------------------------
    /** File name. */
    protected String fileName;

    /** File reader object. */
    protected transient Reader reader;

    // -----------------------------------------------------------------------
    //  Constructor
    // -----------------------------------------------------------------------

    /**
     * Empty constructor.
     */
    public MiningFileStream() {
        super();
    }

    /**
     * Mining file stream for a given file and given meta data.
     *
     * @param file path of file to access
     * @param logicalData meta data of file data
     */
    public MiningFileStream(String file, ELogicalData logicalData) {
        this();
        this.fileName = file;
        this.logicalData = logicalData;
        this.reader = null;
    }

    /**
     * Mining file stream for a given file.
     * The meta data is automatically determined using the
     * method createMetaData.
     *
     * @param file path of file to access
     * @throws MiningException invalid file path
     */
    public MiningFileStream(String file) {
        this(file, null);
    }


    /**
     * Returns file name.
     *
     * @return file name
     */
    public String getFileName() {
        return fileName;
    }

    // -----------------------------------------------------------------------
    //  General stream methods
    // -----------------------------------------------------------------------

    /**
     * Open mining file stream. This method can be left.
     *
     * @exception MiningException if a mining source access error occurs
     */
    public void open() throws MiningException {
        try {
            reader = new BufferedReader(new FileReader(this.fileName));
            this.open = true;
        } catch (IOException ex) {
            this.reader = null;
            this.physicalData = null;
            this.open = false;
            throw new MiningDataException("Can't read from the file: " + this.fileName);
        }

        reset();
    }

    /**
     * Close mining file stream by closing reader.
     *
     * @exception MiningException if a mining source access error occurs
     */
    public void close() throws MiningException {
        if (!this.isOpen())
            throw new MiningDataException("Stream is already closed");

        this.open = false;
        try {
            reader.close();
        } catch (IOException ex) {
            throw new MiningDataException("Can't close reader from the file: " + fileName);
        } catch (NullPointerException ex) { // stream is still/already closed
        }
        reader = null;
    }

    /**
     * Utility method for getting  input stream of the resource;
     *
     * @return - reader for parser
     */
    protected Reader getReader() {
        try {
            return new BufferedReader(new FileReader(this.fileName));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Recognize the input stream data specification. Not implemented.
     *
     * @return the MiningDataSpecification
     * @exception MiningException always thrown
     */
    public EPhysicalData recognize() throws MiningException {
        throw new MiningException(MiningErrorCode.UNSUPPORTED);
    }

    // -----------------------------------------------------------------------
    //  Methods of cursor positioning
    // -----------------------------------------------------------------------

    /**
     * Places the cursor before first row.
     * This is done by closing and reopening the file reader.
     *
     * @throws MiningException reset error
     */
    public void reset() throws MiningException {
        if (!this.isOpen())
            throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't reset closed stream. Call open()");

        try {
            if (reader != null) reader.close();
            reader = new BufferedReader(new FileReader(this.fileName));
            this.open = true;
            cursorPosition = -1;
        } catch (IOException ex) {
//            this.open = false;
//            throw new MiningException( ex.getMessage() );
            this.reader = null;
            this.physicalData = null;
            this.open = false;
            throw new MiningDataException("Can't read from the file: " + this.fileName);
        }
    }

    /**
     * Advance cursor by one position. Not implemented.
     *
     * @return true if next vector exists, else false
     * @throws MiningException always thrown
     */

    // public abstract boolean next() throws MiningException;
    public abstract MiningVector readPhysicalRecord() throws MiningException;


    /**
     * Move cursor to given position. Not supported.
     *
     * @param position new cursor position
     * @return true if possible, else false
     * @throws MiningException always thrown
     */
    //protected abstract MiningVector move( int position ) throws MiningException;
    protected abstract MiningVector movePhysicalRecord(int position) throws MiningException;


    // -----------------------------------------------------------------------
    //  Other methods
    // -----------------------------------------------------------------------

    /**
     * Returns string representation of mining file stream
     * (just the file name).
     *
     * @return string representation of mining file stream
     */
    public String toString() {
        return fileName + "/n" + super.toString();
    }


    public Object clone() {
        MiningFileStream o = (MiningFileStream) super.clone();
        o.fileName = fileName;

        if (isOpen()) {
            try {
                o.open();
            } catch (MiningException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return o;
    }


}
