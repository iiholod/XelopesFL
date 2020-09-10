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
  * @author Michael Thess
  * @version 1.0
  */

package org.eltech.ddm.inputdata;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

/**
 * Extension of MiningInputStreams for arrays. Can only be
 * used for data arrays that fit completely into memory. <p>
 *
 * Two basic array types are supported: an array of double
 * values (for categorical attributes these are the keys) and
 * an array of objects (for numeric attributes the double
 * values are wrapped as Double).
 */
public class MiningArrayStream extends MiningInputStream implements Cloneable
{
    // -----------------------------------------------------------------------
    //  Variables definitions
    // -----------------------------------------------------------------------
    /** Array of doubles. Could also be directly used for fast access. */
    public double[][] miningArray = null;

    /** Array of objects. Could also be directly used for fast access. */
    public Object[][] miningObjectArray = null;

    /** Use array of objects (miningObjectArray) instead of array of doubles (miningArray). */
    protected boolean useObjectArray = false;

    /** Length of mining array **/
    protected int miningArrayLength = -1;

    // -----------------------------------------------------------------------
    //  Constructors
    // -----------------------------------------------------------------------
    public MiningArrayStream(){}
    /**
     * Constructs mining array stream from array of doubles.
     * All attributes are treated as numeric and the meta data
     * is constructed automatically.
     *
     * @param miningArray array of double values
     * @exception MiningException could not create array stream
     */
    public MiningArrayStream( double[][] miningArray ) throws MiningException
    {
        this.useObjectArray = false;
        this.miningArray    = miningArray;

        // Create meta data:
        this.logicalData       = new ELogicalData();
        this.logicalData.setName("Created for MiningArrayStream at: " + new java.util.Date());
        int numbAtt         = miningArray[0].length;
        for (int i = 0; i < numbAtt; i++){
        	ELogicalAttribute la = new  ELogicalAttribute();
        	la.setName("numAtt"+ i);
        	la.setAttributeType(AttributeType.numerical);
        	logicalData.addAttribute(la);
        }

        miningArrayLength   = getMiningArrayLength();
        cursorPosition = -1;
        this.open           = true;
    }

    /**
     * Constructs mining array stream from array of doubles whose meta data
     * is specified in metaData. In case of categorical attributes, the
     * double values are the keys.
     *
     * @param miningArray array of double values
     * @param logicalData meta data of attributes
     */
    public MiningArrayStream( double[][] miningArray, ELogicalData logicalData ) {
        this.useObjectArray = false;
        this.miningArray    = miningArray;
        this.logicalData       = logicalData;

        miningArrayLength   = getMiningArrayLength();
        cursorPosition = -1;

        this.open           = true;
    }

    /**
     * Constructs mining array stream from array of objects whose meta data
     * is specified in metaData. For numeric attributes, the objects are
     * Doubles wrappers. In case of categorical attributes, the
     * objects are of the type Category.
     *
     * @param miningObjectArray array of objects
     * @param logicalData meta data of attributes
     */
    public MiningArrayStream( Object[][] miningObjectArray, ELogicalData logicalData ) {
        this.useObjectArray = true;
        this.miningObjectArray = miningObjectArray;
        this.logicalData = logicalData;

        miningArrayLength = getMiningArrayLength();
        cursorPosition = -1;

        this.open = true;
    }

    /**
     * Creates a mining array from any mining input stream.
     * Requires two passes through the data: one to determine the number
     * of rows of the input stream and one to copy the data to the
     * mining array.
     *
     * @param inputStream mining input stream to read in
     * @exception IllegalArgumentException empty input stream
     * @exception MiningException could not create array stream
     */
    public MiningArrayStream( MiningInputStream inputStream ) throws IllegalArgumentException, MiningException {
    	defConstructor(inputStream);
    }

    public MiningArrayStream( MiningArrayStream inputStream ) throws IllegalArgumentException, MiningException {
        // No input stream => exception:
        if( inputStream == null )
            throw new IllegalArgumentException( "MiningInputStream can't be null." );

        useObjectArray = inputStream.useObjectArray;
        logicalData    = inputStream.getLogicalData();
        physicalData   = inputStream.getPhysicalData();
        miningArrayLength = inputStream.miningArrayLength;
        miningArray = inputStream.miningArray;
        miningObjectArray = inputStream.miningObjectArray;
        useObjectArray = inputStream.useObjectArray;

        cursorPosition = -1;

        this.open           = true;
    }

    /**
     * Returns length of mining array. If useObjectArray is true,
     * the miningObjectArray is used, else miningArray.
     *
     * @return length of mining array, -1 if array is null
     */
    private int getMiningArrayLength() {

       if (useObjectArray) {
         if (miningObjectArray == null)
           return -1;
         else
           return miningObjectArray.length;
       }
       else {
         if (miningArray == null)
           return -1;
         else
           return miningArray.length;
       }
    }

    // -----------------------------------------------------------------------
    //  Getter and setter methods
    // -----------------------------------------------------------------------

    //     public void findPhysicalModel() throws MiningException {
//
//       org.omg.cwm.objectmodel.core.CorePackage cp = com.prudsys.pdm.Cwm.CWMCompletePackage.getCWMCompletePackage().getCore();
//       org.omg.cwm.objectmodel.core.Package dataPackage = cp.getPackage().createPackage("StoredData package", null);
//       dataPackage.addOwnedElement(metaData);
//       physicalModel = dataPackage;
//     }

    //    public org.omg.cwm.analysis.transformation.TransformationMap getPhysicalToLogicalModelTransformation()
//        throws MiningException {
//
//      com.prudsys.pdm.Cwm.CWMCompletePackage cwmFactory =
//          com.prudsys.pdm.Cwm.CWMCompletePackage.getCWMCompletePackage();
//      TransformationPackage tpg = cwmFactory.getTransformation();
//
//      TransformationMap tm = tpg.getTransformationMap().createTransformationMap();
//      ClassifierMap cm = tpg.getClassifierMap().createClassifierMap();
//      tm.addOwnedElement(cm);
//      for (int i = 0; i < metaData.getAttributesNumber(); i++) {
//        FeatureMap fm = tpg.getFeatureMap().createFeatureMap();
//        fm.addSource( metaData.getMiningAttribute(i) );
//        fm.addTarget( metaData.getMiningAttribute(i) );
//        cm.addFeatureMap(fm);
//      };
//
//      metaData.addOwnedElement(tm);
//      physicalModel.addOwnedElement(tm);
//
//      return tm;
//    }

    /**
     * Returns number of vectors of mining array.
     *
     * @return number of vectors
     */
    public int getVectorsNumber() {

      return miningArrayLength;
    }

    /**
     * Is object array instead of double array used?
     *
     * @return true if object array is used, false if double array
     */
    public boolean isUseObjectArray() {

      return useObjectArray;
    }

    /**
     * Returns data matrix of doubles.
     *
     * @return data matrix of doubles
     * @exception MiningException array contains objects instead of doubles
     */
    public double[][] getMatrix() throws MiningException {

      if (useObjectArray)
        throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "array contains objects instead of doubles");

      return miningArray;
    }

    /**
     * Returns data matrix of objects.
     *
     * @return data matrix of objects
     * @exception MiningException array contains doubles instead of objects
     */
    public Object[][] getObjectMatrix() throws MiningException {

      if (!useObjectArray)
        throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA,"array contains doubles instead of objects");

      return miningObjectArray;
    }

    // -----------------------------------------------------------------------
    //  General stream methods
    // -----------------------------------------------------------------------
    /**
     * Opens mining array stream.
     *
     * @exception MiningException if a mining source access error occurs
     */
    synchronized public void open() throws MiningException
    {
      this.open = true;
      reset();
    }

    /**
     * Closes mining array stream.
     *
     * @exception MiningException if a mining source access error occurs
     */
    synchronized public void close() throws MiningException
    {
      if (!open)
        throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA,"Stream is already closed");

      this.open = false;
    }

    /**
     * Recognizes meta data of array.
     *
     * @return meta data of array
     */
    synchronized public EPhysicalData recognize() {
        return physicalData;
    }

    // -----------------------------------------------------------------------
    //  Methods of cursor positioning
    // -----------------------------------------------------------------------
    /**
     * Set cursor before first row.
     *
     * @exception MiningException could not reset cursor
     */
    synchronized public void reset() throws MiningException
    {
        if (!open)
          throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't reset closed stream. Call open()");

        cursorPosition = -1;
    }


    // changing in MiningUnputStream heirs
    //synchronized public boolean next() throws MiningException
    synchronized public MiningVector readPhysicalRecord() throws MiningException {
        if (!open)
          throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA,"Can't perform operation on closed stream. Call open()");

        cursorPosition++;
        if (getCurrentPosition() < miningArrayLength)
        	return move(getCurrentPosition());

        return null;
    }

    /**
     * Move cursor to given position.
     *
     * @param position new position of the cursor
     * @return true if cursor could be positioned, false if not
     * @exception MiningException could not move cursor
     */
    //synchronized protected MiningVector move(int position) throws MiningException
    synchronized protected MiningVector movePhysicalRecord(int position) throws MiningException
    {
        if (!open)
          throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't perform operation on closed stream. Call open()");

        if (position < miningArrayLength)
        {
            cursorPosition = position;
            return  readVector(position);
        }

        return null;
    }

    // -----------------------------------------------------------------------
    //  Methods of reading from the stream
    // -----------------------------------------------------------------------
    /**
     * Read current mining vector.
     *
     * @return mining vector to read
     * @exception MiningException could not read vector
     */
    private MiningVector readVector(int cursorPosition) throws MiningException
    {
        if (!open)
          throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't perform operation on closed stream. Call open()");

        MiningVector vector;
        double[] values;

        // Use array of objects:
        if (useObjectArray) {
        	values     = new double[ logicalData.getAttributesNumber()];
          for( int i = 0; i < values.length; i++ )
          {
            Object obj         = miningObjectArray[cursorPosition][i];
            ELogicalAttribute la = logicalData.getAttribute( i );
            if (la.getAttributeType() == AttributeType.categorical )
            {
                Integer index = la.getCategoricalProperties().getIndex(obj);
                if (index != null)
              	  values[i]    = index.doubleValue();
                else
              	  values[i] = la.getCategoricalProperties().addCategory(obj, CategoryProperty.invalid);
            }
            else
            {
              values[i] = Double.parseDouble( obj.toString() );
            }
          }
        }
        // Use array of doubles:
        else {
          values = miningArray[cursorPosition];
        }

        vector = new MiningVector( values );
        vector.setLogicalData( logicalData );
        vector.setIndex(cursorPosition);

        return vector;
    }

//    /**
//     * Reads value at given coordinates.
//     *
//     * @param     rowNumber         the row number
//     * @param     attributeIndex    the index of MiningAttribute to read value
//     *
//     * @return    value of MiningAttribute with index <code>attributeIndex</code> at
//     *            <code>rowNumber</code> row
//     * @exception MiningException if an error occurs
//     */
//    public double readAttributeValue(int rowNumber, int attributeIndex) throws MiningException
//    {
//      if (!open)
//        throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Can't perform operation on closed stream. Call open()");
//
//      // Use mining array:
//      if (! useObjectArray)
//        return miningArray[rowNumber][attributeIndex];
//
//      // Use mining objects array:
//      double value       = 0.0;
//      Object obj         = miningObjectArray[rowNumber][attributeIndex];
//      ELogicalAttribute la = logicalData.getAttribute( attributeIndex );
//      if (la.getAttributeType() == AttributeType.categorical )
//      {
//          Integer index = la.getCategoricalProperties().getIndex(obj);
//          if (index != null)
//        	  value    = index.doubleValue();
//          else
//        	  value = la.getCategoricalProperties().addCategory(obj, CategoryProperty.invalid);
//      }      else
//      {
//        value = Double.parseDouble( obj.toString() );
//      }
//
//      return value;
//    }

    public Object clone() {
		MiningArrayStream o;
		o = (MiningArrayStream) super.clone();

		if (miningArray != null) {
			o.miningArray = miningArray.clone();
			for (int i = 0; i < miningArray.length; i++)
				if (miningArray[i] != null)
					o.miningArray[i] = miningArray[i].clone();
		}

		if (miningObjectArray != null) {
			o.miningObjectArray = miningObjectArray.clone();
			for (int i = 0; i < miningObjectArray.length; i++)
				if (miningObjectArray[i] != null) {
					o.miningObjectArray[i] = miningObjectArray[i].clone();
				}
		}

		return o;
	}

    private void defConstructor(MiningInputStream inputStream) throws MiningException {

        // No input stream => exception:
        if (inputStream == null) {
            throw new IllegalArgumentException("MiningInputStream can't be null.");
        }

        this.useObjectArray = false;
        this.logicalData = inputStream.getLogicalData();
        this.physicalData = inputStream.getPhysicalData();

        // Determine length of input stream and allocate array:
        //inputStream.reset();
        miningArrayLength = inputStream.getVectorsNumber();
//        while( inputStream.next() )
//        {
//          miningArrayLength = miningArrayLength + 1;
//        }
        miningArray = new double[miningArrayLength][logicalData.getAttributesNumber()];

        // Copy data to mining array:
        //inputStream.reset();
        //while (inputStream.next()) {
        for (int iRow = 0; iRow < miningArrayLength; iRow++) {
            MiningVector vec = inputStream.getVector(iRow);
            for (int i = 0; i < logicalData.getAttributesNumber(); i++)
                miningArray[iRow][i] = vec.getValue(i);
            //         iRow = iRow + 1;
        }

        //cursorPosition      = -1;

        this.open = true;
    }
	@Override
	public MiningVector getVector(int rowNumber) throws MiningException {
		return readVector(rowNumber);
	}

}
