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

/**
  * Title: XELOPES Data Mining Library
  * Description: The XELOPES library is an open platform-independent and data-source-independent library for Embedded Data Mining.
  * Copyright: Copyright (c) 2002-2005 prudsys AG. All Rights Reserved.
  * License: Use is subject to XELOPES license terms.
  * @author Valentine Stepanenko (valentine.stepanenko@zsoft.ru)
  * @version 1.0
  */

package org.eltech.ddm.inputdata.file;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.eltech.ddm.inputdata.MiningSparseVector;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.*;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

import java.io.*;



/**
 * Extends MiningFileStream for ARFF files of the
 * WEKA library.
 *
 * Supports the updateble stream type. Attention: The update mode
 * always overwrites the previous data section (but not the meta data).
 */
public class MiningArffStream extends MiningFileStream
{
    // -----------------------------------------------------------------------
    //  Variables definitions
    // -----------------------------------------------------------------------
    /** Tokenizer for reading. */
    protected StreamTokenizer tokenizer;

    // -----------------------------------------------------------------------
    //  Constructors
    // -----------------------------------------------------------------------
    /**
     * Empty constructor.
     */
    public MiningArffStream()
    {
    }

    /**
     * Mining ARFF stream for a given ARFF file and given meta data.
     *
     * @param dataFileName path of ARFF file to access
     * @param logicalData meta data of file data
     * @throws MiningException invalid file path or format error
     */
    public MiningArffStream( String dataFileName, ELogicalData logicalData ) throws MiningException, IOException {
        super( dataFileName, logicalData );
        fileName = dataFileName;
        if( logicalData == null )
        {
        	physicalData = recognize();
        }
        open();
    }

    /**
     * Mining file stream for a given file.
     * The meta data is automatically determined using the
     * method recognize which reads the ARFF header.
     *
     * @param dataFileName path of ARFF file to access
     * @throws MiningException invalid file path or format error
     */
    public MiningArffStream( String dataFileName ) throws MiningException, IOException {
        super( dataFileName );
        fileName = dataFileName;
        physicalData = recognize();
        physicalData.setURI(dataFileName);
        open();
    }

    // -----------------------------------------------------------------------
    //  General stream methods
    // -----------------------------------------------------------------------
    /**
     * Reads and stores header of an ARFF file.
     *
     * @return meta data of ARFF file
     * @exception MiningException if the information is not read
     * successfully
    */
    synchronized public EPhysicalData recognize() throws MiningException, IOException {
        boolean wasOpen = this.isOpen();
        if(!this.isOpen())
          this.open();

        if(this.getCurrentPosition() != -1)
          throw new MiningDataException("Can't recognize if cursor is not before first row");

        if( physicalData == null )
        {
            super.reset();
            initTokenizer( reader );
            logicalData = new ELogicalData();
            physicalData = new EPhysicalData();
            attributeAssignmentSet = new EAttributeAssignmentSet();
            String attributeName;
            String token;
            // Get name of relation.
            token = getNextToken();
            while( token.equalsIgnoreCase( "endofline" ) )
            {
                token = getNextToken();
            }
            if(token.equalsIgnoreCase("@relation"))
            {
                token = getNextToken();
                if( token.equalsIgnoreCase( "endoffile" ) || token.equalsIgnoreCase( "endofline" ) )
                {
                    tokenizerException("premature end of line or file");
                }
                logicalData.setName(token );
                physicalData.setName(token);
                token = getNextToken();
                if( !token.equalsIgnoreCase( "endofline" ) )
                {
                    if( !token.equalsIgnoreCase( "endoffile" ) )
                    {
                        tokenizerException("end of line expected");
                    }
                }
            }
            else
            {
                tokenizerException("keyword @relation expected");
            }
            // Get attribute declarations.
            token = getNextToken();
            while( token.equalsIgnoreCase( "endofline" ) )
            {
                token = getNextToken();
            }
            if( token.equalsIgnoreCase( "endoffile" ) )
            {
                tokenizerException("premature end of file");
            }
            while( token.equalsIgnoreCase("@attribute"))
            {
                // Get attribute name.
                token = getNextToken();
                if( token.equalsIgnoreCase( "endoffile" ) || token.equalsIgnoreCase( "endofline" ) )
                {
                    tokenizerException("premature end of line or file");
                }
                attributeName = token;
                token = getNextToken();
                if( token.equalsIgnoreCase( "endoffile" ) || token.equalsIgnoreCase( "endofline" ) )
                {
                    tokenizerException("premature end of line or file");
                }
                // Check if attribute is nominal.
                ELogicalAttribute la = new ELogicalAttribute();
                PhysicalAttribute pa = new PhysicalAttribute();
                EDirectAttributeAssignment da = new EDirectAttributeAssignment();
                la.setName(attributeName);
                pa.setName(attributeName);
                if( token.equalsIgnoreCase("{") )
                {
                    // Attribute is nominal.
                	la.setAttributeType(AttributeType.categorical);
                	pa.setAttributeType(AttributeType.categorical);
                	pa.setDataType(AttributeDataType.stringType);
                    token = getNextToken();
                    while( !token.equalsIgnoreCase( "}" ) )
                    {
                        if( token.equalsIgnoreCase( "endofline" ) )
                        {
                            tokenizerException("} expected at end of enumeration");
                        }
                        else
                        {
                        	la.getCategoricalProperties().addCategory( token, CategoryProperty.valid);
                        }
                        token = getNextToken();
                    }
                    if (la.getCategoricalProperties().getSize() == 0)
                    {
                        tokenizerException("no nominal values found");
                    }
                }
                else
                {
                    // Attribute is real, integer, or string.
                    if ( token.equalsIgnoreCase("real") || token.equalsIgnoreCase("integer") || token.equalsIgnoreCase("numeric"))
                    {
                    	la.setAttributeType(AttributeType.numerical);
                    	pa.setAttributeType(AttributeType.numerical);
                        // default data type is double
                    	if ( token.equalsIgnoreCase("integer") )
                    		pa.setDataType( AttributeDataType.integerType);
                    	else
                    		pa.setDataType( AttributeDataType.doubleType);
                    }
                    else
                    if (token.equalsIgnoreCase("string"))
                    {
                    	la.setAttributeType(AttributeType.categorical);
                    	pa.setAttributeType(AttributeType.categorical);
                    	pa.setDataType( AttributeDataType.stringType);
                    }
                    else
                    {
                        tokenizerException("no valid attribute type or invalid "+ "enumeration");
                    }
                }
                logicalData.addAttribute( la );
                physicalData.addAttribute(pa);
                da.addLogicalAttribute(la);
                da.setAttribute(pa);
                attributeAssignmentSet.addAssignment(da);
                token = getNextToken();
                if( !token.equalsIgnoreCase( "endofline" ) )
                {
                    if( !token.equalsIgnoreCase( "endoffile" ) )
                    {
                        tokenizerException( "end of line expected");
                    }
                }
                token = getNextToken();
                while( token.equalsIgnoreCase( "endofline" ) )
                {
                    token = getNextToken();
                }
            }
            // Check if any attributes have been declared.
            if( physicalData.getAttributes().size() == 0 )
            {
                tokenizerException("no attributes declared");
            }
        }
        if(wasOpen)
          reset();
        else
          close();
        return physicalData;
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
    synchronized public void reset() throws MiningException, FileNotFoundException {
        if(!isOpen())
          throw new MiningDataException("Can't reset closed stream. Call open()");

        super.reset();
        initTokenizer( reader );
        String token = getNextToken();
        while( !token.equalsIgnoreCase( "@data" ) )
        {
            token = getNextToken();
        }
        cursorPosition = -1;
    }


    // -----------------------------------------------------------------------
    //  Methods of writing into the stream
    // -----------------------------------------------------------------------
    private PrintWriter writer;

        /**
         * Sets new meta data to this stream. Removes all data.
         *
         * @param logicalData new meta data to attributes
         * @exception MiningException if an error occurs
         */
        public void updateSetLogicalData(ELogicalData logicalData) throws MiningException
        {
          this.logicalData = logicalData;
          updateRemoveAllVectors();
        };


        /**
         * Removes the specified LogicalAttribute instance from the logical data and appropriate attributes
         * from AttributeAssignmentSet.
         *
         * @param attName
         * @throws MiningException
         */

        public void updateRemoveAttLogicalData(String attName) throws MiningException
        {
        	logicalData.removeAttribute(attName);
        	int index = 0;
        	int size = attributeAssignmentSet.getSize();
        	boolean find = false;
        	String attNameAssignment;
        	while ((!find) && (index < size))
        	{
        		attNameAssignment = attributeAssignmentSet.getAttributeAssignment(index).getLogicalAttribute().get(0).getName();
        		if (attNameAssignment.equals(attName))
        		{
        			attributeAssignmentSet.removeAssignment(attributeAssignmentSet.getAttributeAssignment(index));
        			find = true;
        		}
        		index++;
        	}

        }

        /**
         * Appends new mining vector to this stream. Only for updateble
         * input streams. Before using this method the first time,
         * updateSetMetaData or updateRemoveAllVectors must be called.
         *
         * @param vector mining vector to add
         * @exception MiningException if an error occurs
         */
        public void updateAppendVector(MiningVector vector) throws MiningException
        {
            String line = "";
            ELogicalData ld = vector.getLogicalData();
            int nVec = vector.getValues().length;
            for (int i = 0; i < nVec; i++) {
              double value = vector.getValue(i);
              String stVal = "";
              if (ld == null || ld.getAttribute(i).getAttributeType() == AttributeType.numerical)
                stVal = String.valueOf(value);
              else
                stVal = "" + vector.getValueCategory(i);
              if (vector.isMissing(i))
                stVal = "?";
              line = line + stVal;
              if (i < nVec - 1)
                line = line + ",";
            };

            writer.println(line);
        }

        /**
         * Close mining ARFF stream by closing reader and writer.
         *
         * @exception MiningException if a mining source access error occurs
         */
        public void close() throws MiningException, IOException {
          super.close();
          try
          {
            if (writer != null) writer.close();
          }
          catch( Exception ex)
          {
            throw new MiningDataException( "Can't close ARFF stream from file: "+fileName );
          };
        }

    /**
     * Returns next token.
     *
     * @return next token
     * @throws MiningException can't return next token
     */
    protected String getNextToken() throws MiningException
    {
        String token = "";
        int c;
        try
        {
            c = tokenizer.nextToken();
        }
        catch( IOException ex )
        {
            throw new MiningDataException( ex.getMessage() );
        }
        switch( c )
        {
            case StreamTokenizer.TT_EOL:token = "endofline";break;
            case StreamTokenizer.TT_EOF:token = "endoffile";break;
            case '{'  : token="{"; break;
            case '}'  : token="}"; break;
            case '?'  : tokenizer.ttype = StreamTokenizer.TT_WORD; token = "?"; break;
            case '\'' : tokenizer.ttype = StreamTokenizer.TT_WORD; token = tokenizer.sval; break;
            case '"'  : tokenizer.ttype = StreamTokenizer.TT_WORD; token = tokenizer.sval; break;
            default   : token = tokenizer.sval;break;
        }
        return token;
    }

    /**
     * Init tokenizer.
     *
     * @param reader of tokenizer
     */
    protected void initTokenizer( Reader reader )
    {
        BufferedReader bufferedInput = new BufferedReader( reader );
        tokenizer = new StreamTokenizer( bufferedInput );
        tokenizer.resetSyntax();
        tokenizer.whitespaceChars( 0, ' ' );
        tokenizer.wordChars( ' ' + 1, '\u00FF' );
        tokenizer.whitespaceChars( ',', ',' );
        tokenizer.commentChar( '%' );
        tokenizer.quoteChar( '"' );
        tokenizer.quoteChar( '\'' );
        tokenizer.ordinaryChar( '{' );
        tokenizer.ordinaryChar( '}' );
        tokenizer.eolIsSignificant( true );
    }

    /**
     * Throws error message with line number and last token read.
     *
     * @param message the error message to be thrown
     * @throws MiningException containing the error message
    */
    protected void tokenizerException( String message ) throws MiningException
    {
        throw new MiningDataException( message + ", read " + tokenizer.toString() );
    }

	@Override
	synchronized protected MiningVector movePhysicalRecord(int position) throws MiningException, IOException, CsvException {
		MiningVector mv = null;
		if(getCurrentPosition() < position)
		{
			do{mv = next();}
			while((mv!= null) && (getCurrentPosition() < position));
		}
		else
		{
			reset();
			do{mv = next();}
			while((mv != null) && (getCurrentPosition() < position));
		};

		return mv;
	}

	@Override
	public Object clone() {
		MiningArffStream o = (MiningArffStream)super.clone();	    
		return o;
	}
	
	@Override
	public MiningVector readPhysicalRecord() throws MiningException {
	  if(!isOpen())
          throw new MiningDataException("Can't perform operation on closed stream. Call open()");

        // Check if end of file reached.
        String token = getNextToken();
        while( token.equalsIgnoreCase( "endofline" ) )
        {
            token = getNextToken();
        }
        if( token.equalsIgnoreCase( "endoffile" ) )
        {
            return null;
        }
        if( !token.equals( "{" ) )
        {
            double[] instance = new double[ logicalData.getAttributesNumber() ];
            // Get values for all attributes.
            for (int i = 0; i < physicalData.getAttributeCount(); i++)
            {
                // Get next token
                if (i > 0)
                {
                    token = getNextToken();
                }
                ELogicalAttribute la = logicalData.getAttribute( i );
                PhysicalAttribute pa = physicalData.getAttribute( i );
                if (la.getAttributeType() == AttributeType.categorical)
                {
                	if(token.equals("?")) {// missing
                		instance[i] = la.getCategoricalProperties().addCategory(token, CategoryProperty.missing);
                		missingValues = true;
                	}
                	else{
                		Integer indexCategory = la.getCategoricalProperties().getIndex(token);
                		if(indexCategory == null)
                			instance[i] = la.getCategoricalProperties().addCategory(token, CategoryProperty.invalid);
                		else
                			instance[i] = indexCategory.doubleValue();
                	}
                }
                else
                {
                  try {
                    instance[i] = Double.parseDouble(token);
                  }
                  catch(NumberFormatException ex) {
                    instance[i] = Double.NaN;
                    missingValues = true;
                  }
                }
            }
            token = getNextToken();
            if( !token.equalsIgnoreCase( "endofline" ) )
            {
                if( !token.equalsIgnoreCase( "endoffile" ) )
                {
                    tokenizerException( "end of line expected" );
                }
            }
            // Add instance to dataset
            MiningVector cursorVector = new MiningVector(instance);
            cursorVector.setLogicalData( logicalData );
            cursorPosition++;
            cursorVector.setIndex(cursorPosition);
            return cursorVector;
        }
        else
        {
            double[] m_ValueBuffer = new double[ logicalData.getAttributesNumber()];
            int[] m_IndicesBuffer = new int[ logicalData.getAttributesNumber()];
            int valIndex, numValues = 0, maxIndex = -1;
            do
            {
                token = getNextToken();
                if (tokenizer.ttype == '}')
                {
                    break;
                }
                // Is index valid?
                try
                {
                    m_IndicesBuffer[numValues] = Integer.valueOf( token ).intValue();
                }
                catch(NumberFormatException e)
                {
                    tokenizerException( "index number expected" );
                }
                if( m_IndicesBuffer[numValues] <= maxIndex )
                {
                    tokenizerException( "indices have to be ordered" );
                }
                if( ( m_IndicesBuffer[numValues] < 0 ) || ( m_IndicesBuffer[numValues] >= logicalData.getAttributesNumber()  ) )
                {
                    tokenizerException( "index out of bounds" );
                }
                maxIndex = m_IndicesBuffer[numValues];
                // Get value;
                token = getNextToken();
                ELogicalAttribute la = logicalData.getAttribute( m_IndicesBuffer[numValues] );
            	if(token.equals("?")) {// missing
            		m_ValueBuffer[numValues] = la.getCategoricalProperties().addCategory(token, CategoryProperty.missing);
            		missingValues = true;
            	}
            	else if(la.getAttributeType() == AttributeType.categorical)
                {
                	m_ValueBuffer[numValues] = la.getCategoricalProperties().addCategory(token, CategoryProperty.invalid);
                }
                else m_ValueBuffer[numValues] = Double.parseDouble( token );
                numValues++;
            }
            while( true );
            token = getNextToken();
            if( !token.equalsIgnoreCase( "endofline" ) )
            {
                if( !token.equalsIgnoreCase( "endoffile" ) )
                {
                    tokenizerException( "end of line expected" );
                }
            }
            // Add instance to dataset
            double[] tempValues = new double[numValues];
            int[] tempIndices = new int[numValues];
            System.arraycopy(m_ValueBuffer, 0, tempValues, 0, numValues);
            System.arraycopy(m_IndicesBuffer, 0, tempIndices, 0, numValues);
            // Add instance to dataset
            MiningVector cursorVector = new MiningSparseVector( 1, tempValues, tempIndices );
            cursorVector.setLogicalData( logicalData );
            cursorPosition++;
            return cursorVector;
        }
	}
}
