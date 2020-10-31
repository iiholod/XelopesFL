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

package org.eltech.ddm.inputdata;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.*;
import org.eltech.ddm.miningcore.miningdata.assignment.*;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeSelectionFunction;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.ValueSelectionFunction;
import org.omg.java.cwm.objectmodel.core.Attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class MiningInputStream implements Cloneable, Serializable //extends com.prudsys.pdm.Cwm.Core.Class implements MiningVectorSet
{
    // -----------------------------------------------------------------------
    //  Variables definitions
    // -----------------------------------------------------------------------
    /** Meta data with attributes description. Logical data model for mining. */
    protected transient ELogicalData logicalData;

    /** Physical data model. */
    protected transient EPhysicalData physicalData;

    /** This object contains a attributes of AttributeAssignment objects and completes attribute assignment for a mining operation. */
    protected transient EAttributeAssignmentSet attributeAssignmentSet;      //

    /** Current cursor position. */
    protected transient int  cursorPosition = -1;

    /** True, if <code>MiningInputStream</code> contains missing values. */
    protected transient boolean missingValues = false;

    /** True, if <code>MiningInputStream</code> is open. */
    protected transient boolean open = false;

    protected int vectorsNumber = 0;

	private int offsetVectorIndex = 0;

	private transient AttributeAssignmentType attributeAssignmentType;
    
    /** Meta data with attributes description. Assignmented logical data model for mining. */
    protected transient ELogicalData userLogicalData;
    
    private transient ArrayList<Integer> unusesAttributeIndexes;

    private transient AttributeVectorPool instancePool;
    // -----------------------------------------------------------------------
    //  Constructor
    // -----------------------------------------------------------------------
    /**
     * <code>MiningInputStream</code> constructor.
     */
    public MiningInputStream() {}

    // -----------------------------------------------------------------------
    //  Getter and setter methods
    // -----------------------------------------------------------------------
    /**
     * Returns the meta data.
     *
     * @return the meta data
     * @exception MiningException could not retrieve meta data
     */
    public ELogicalData getLogicalData() throws MiningException {
    	if(attributeAssignmentType != null){
    		return userLogicalData;
    	}
    	return logicalData;
    }

    public void setLogicalData(ELogicalData logicalData) {
    	this.logicalData = logicalData;
    }
	public void setPhysicalData(EPhysicalData physicalData) {
		this.physicalData = physicalData;
	}
	public void setAttributeAssignmentSet(EAttributeAssignmentSet assignmentSet) {
		this.attributeAssignmentSet = assignmentSet;
	}


    /**
     * Returns the AttributeAssignmentSet
     *
     * @return the AttributeAssignmentSet
     * @exception MiningException could not retrieve assignment attributes
     */
    public EAttributeAssignmentSet getAttributeAssignmentSet() throws MiningException
    {
    	return attributeAssignmentSet;
    }

    /**
     * Return the current cursor position.
     *
     * @return the current cursor position.
     */
    protected int getCurrentPosition()
    {
        return cursorPosition;
    }

    /**
     * Returns true if stream is open.
     *
     * @return boolean true if stream is open
     */
    public boolean isOpen()
    {
        return open;
    }

    /**
     * Determines the number of vectors. <p>
     *
     * In this most simple implementation, it utilizes the
     * reset and next methods. The cursor position is stored
     * into a temporary variable and finally recovered. <p>
     *
     * Most implementations of <code>MiningInputStream</code> will
     * overwrite this method.
     *
     * @return number of vectors
     * @exception MiningException method reset not implemented
     */
    public int getVectorsNumber() throws MiningException {
        if(vectorsNumber > 0)
        	return vectorsNumber;

    	int currCursorPosition = getCurrentPosition();
        // Count vectors:
        reset();
        int numbVec = 0;
        while ( next() != null )
          numbVec = numbVec + 1;
        // Restore cursor position;
        reset();
        for (int i = 0; i < currCursorPosition; i++)
          next();

        vectorsNumber = numbVec;
        return vectorsNumber;
    }

    /**
     * Return true if there are missing values.
     *
     * @return true if there are missing values
     */
    public boolean isMissingValues()
    {
        return missingValues;
    }

    // -----------------------------------------------------------------------
    //  General stream methods
    // -----------------------------------------------------------------------
    /**
     * Open mining data stream.
     *
     * @exception MiningException if a mining source access error occurs
     */
    public abstract void open() throws MiningException;

    /**
     * Close mining data stream.
     *
     * @exception MiningException if a mining source access error occurs
     */
    public abstract void close() throws MiningException;

    /**
     * Recognize the input stream's meta data by analyzing the input stream.
     *
     * @return     the <code>MiningDataSpecification</code>
     * @exception  MiningException  if an error occurs
     */
    public abstract EPhysicalData recognize() throws MiningException;

    // -----------------------------------------------------------------------
    //  Methods of cursor positioning
    // -----------------------------------------------------------------------
    /**
     * Resets the cursor from its current position to position before
     * the first row. Some mininig sources don't support reseting.
     *
     * @exception MiningException if a mining source access error occurs
     */
    public abstract void reset() throws MiningException;

    /**
     * Moves the cursor down one row from its current position.
     * A <code>MiningInputStream</code> cursor is initially positioned
     * before the first row; the first call to the method
     * <code>next</code> makes the first row the current row; the
     * second call makes the second row the current row, and so on.
     *
     * @return <code>true</code> if the new current row is valid;
     * <code>false</code> if there are no more rows
     * @exception MiningException if a mining source access error occurs
     */
    public MiningVector next() throws MiningException {
		if(attributeAssignmentType == AttributeAssignmentType.DirectAttributeAssignment){
			return directAssignmentNext();
		}
		if(attributeAssignmentType == AttributeAssignmentType.ReversePivotAttributeAssignment){
			return reversePivotAssignmentNext(); //readPhysicalRecord inside
		}

		return readPhysicalRecord();
    }

	public abstract MiningVector readPhysicalRecord() throws MiningException;
	
    /**
     * Moves the cursor to the given row number in
     * this <code>MiningInputStream</code> object.
     *
     * <p>If the row number is zero or positive, the cursor moves to
     * the given row number with respect to the beginning of the
     * mining input stream.  The first row is row 0, the second
     * is row 1, and so on.
     *
     * <p>An attempt to position the cursor beyond the first/last row in
     * the mining input stream leaves the cursor before the first row or
     * after the last row.
     *
     * @param position new cursor position
     * @return <code>true</code> if the cursor is on the mining input stream;
     * <code>false</code> otherwise
     * @exception MiningException if a mining source access error
     * occurs, or the <code>MiningInputStream</code> type is forward only
     */
   // protected abstract MiningVector move( int position ) throws MiningException;
	protected MiningVector move( int position ) throws MiningException {
		if(attributeAssignmentType == null ||
				attributeAssignmentType == AttributeAssignmentType.DirectAttributeAssignment){
			return movePhysicalRecord(position);
		}		
		if(attributeAssignmentType == AttributeAssignmentType.ReversePivotAttributeAssignment){
			cursorPosition = 0;
			int reverseRecordPosition = 0;
			MiningVector mv;
			
			
			reset();
			do{
				if(reverseRecordPosition == position){
					mv = reversePivotAssignmentNext();
					break;
				}
				mv = reversePivotAssignmentNext();
				reverseRecordPosition++;
			}
			while((mv != null) && (reverseRecordPosition < position));
			
			//mv = reversePivotAssignmentNext();
			return mv;
		}
		
		return null;
	}
    
    protected abstract MiningVector movePhysicalRecord( int position ) throws MiningException;

    public int getOffsetVectorIndex() {
		return offsetVectorIndex;
	}

	public void setOffsetVectorIndex(int offsetVectorIndex) {
		this.offsetVectorIndex = offsetVectorIndex;
	}

    /**
     * Reads <code>MiningVector</code> at specified row number.
     *
     * @param     rowNumber the row number
     * @return    <code>MiningVector</code> at specified row
     * @exception MiningException if an error occurs
     */
    public MiningVector getVector( int rowNumber ) throws MiningException {

        return move( rowNumber );
    }


    /**
     * Reads some number of <code>MiningVector</code>s from the input stream and
     * stores them into the buffer array <code>b</code>. The number of
     * <code>MiningVectors</code> actually read is returned as an integer.
     *
     * @param      b   the buffer into which the data is read
     * @return     the total number of <code>MiningVector</code>s read into the buffer,
     *             or <code>-1</code> is there is no more data because the end of
     *             the stream has been reached
     * @exception  MiningException  if an error occurs
     * @see        #readVectors( MiningVector[], int, int )
     */
    public int readVectors(MiningVector[] b) throws MiningException {
       return readVectors(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> <code>MiningVector</code>s from the input stream
     * into an array of <code>MiningVector</code>s.  An attempt is made to read as
     * many as <code>len</code> <code>MiningVector</code>s, but a smaller number may
     * be read, possibly zero. The number of <code>MiningVector</code>s actually
     * read is returned as an integer.
     *
     * @param      b     the buffer into which the data is read
     * @param      off   the start offset in array <code>b</code>
     *                   at which the data is written
     * @param      len   the maximum number of <code>MiningVector</code>s to read
     * @return     the total number of <code>MiningVector</code>s read into the buffer,
     *             or <code>-1</code> if there is no more data because the end of
     *             the stream has been reached
     * @exception  MiningException  if an error occurs
     */
    public int readVectors( MiningVector[] b, int off, int len ) throws MiningException {
           int i = 0;
           if(b == null) throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "Array b can't be null." );
           if( ( off < 0 ) || ( off > b.length ) || ( len < 0 ) || ( ( off + len ) > b.length ) || ( ( off + len ) < 0 ) )
        	   		throw new MiningException( MiningErrorCode.INVALID_ARGUMENT, "Index out of bounds. Check offset and length." );
           if (len == 0) return 0;
           for( ; i < len ; i++ )
           {
               MiningVector mv = next();
        	   if( mv == null ) break;
               b[off + i] = mv;
           }
           return i;
    }


    // -----------------------------------------------------------------------
    //  Methods of writing into the stream
    // -----------------------------------------------------------------------
    /**
     * Sets new meta data to this stream.
     *
     * @param metaData new meta data of stream
     * @exception MiningException if an error occurs
     */
    public void updateSetMetaData(ELogicalData metaData) throws MiningException {

      throw new MiningException(MiningErrorCode.UNSUPPORTED);
    }

    /**
     * Removes all mining vectors from this stream. Note that metadata is not
     * affected by this operation since it is fixed for any stream.
     *
     * @exception MiningException if an error occurs
     */
    public void updateRemoveAllVectors() throws MiningException {

        throw new MiningException(MiningErrorCode.UNSUPPORTED);
    }

    /**
     * Appends new mining vector to this stream.
     *
     * @param vector new mining vector to append
     * @exception MiningException if an error occurs
     */
    public void updateAppendVector(MiningVector vector) throws MiningException {

        throw new MiningException(MiningErrorCode.UNSUPPORTED);
    }


    /**
     * Representation of mining input stream as string.
     * Attention: changes cursor position.
     *
     * @return representation of mining input stream as string
     */
    public String toString() {

      // Meta data:
      StringBuilder description = new StringBuilder();

      try {
        description.append(getPhysicalData()).append("\n");
      }
      catch (MiningException ex) {
        description.append("no metadata").append("\n");
      }

		// Data:
      description.append("data").append("\n");
      // Try to reset stream:
      boolean wasOpen = isOpen();
      try {
        if(isOpen())
          reset();
        else
          open();
      }
      catch (MiningException  ex) {
		  description.append("Warning: can't reset cursor. ").append("Start reading at current position").append("\n");
	  }
		int i = 0;
      // Read data:
      try {
    	  MiningVector mv = next();
    	  while( mv != null  ){
    		  description.append(i++).append(": ").append(mv).append("\n");
    		  mv = next();
    	  }
      }
      catch (Exception ex) {
        description.append("Error: can't read vector ").append(i);
      }

		try {
        if(!wasOpen)
          close();
      }
      catch(MiningException ignored) {
      }

      return description.toString();
    }

	/**
	 * @return the physicalData
	 */
	public EPhysicalData getPhysicalData() throws MiningException {
	      if(physicalData == null)
	    	  physicalData = recognize();

		return physicalData;
	}

	public Object clone() {
		MiningInputStream o = null;
	    try {
	      o = (MiningInputStream)super.clone();
	    } catch(CloneNotSupportedException e) {
	      System.err.println(this.getClass().toString() + " can't be cloned");
	    }

	    if(attributeAssignmentSet != null) {
			assert o != null;
			o.attributeAssignmentSet = (EAttributeAssignmentSet)attributeAssignmentSet.clone();
		}

	    if(logicalData != null) {
			assert o != null;
			o.logicalData = (ELogicalData)logicalData.clone();
		}

	    if(physicalData != null) {
			assert o != null;
			o.physicalData = (EPhysicalData)physicalData.clone();
		}

	    return o;
	}
	
	//--assignment
	public void setAssignmentManager(AssignmentManager assignmentManager) throws MiningException{
		attributeAssignmentSet = assignmentManager.getAttributeAssignmentSet();
		attributeAssignmentType = assignmentManager.getAttributeAssignmentType();
		
		//make user logical data
		userLogicalData = new ELogicalData();
		if(logicalData != null){
			userLogicalData.setName(logicalData.getName());
		}
		unusesAttributeIndexes = new ArrayList<>(); //empty

    	if(attributeAssignmentType == AttributeAssignmentType.DirectAttributeAssignment){
	    	int t = physicalData.getAttributeCount(); 
			for(int i = 0; i < t; i++){
				boolean isExist = false;
				PhysicalAttribute curPhysAtt = physicalData.getAttribute(i);
				for(int j = 0; j < attributeAssignmentSet.getSize(); j++){
					EDirectAttributeAssignment_e directAssignment = (EDirectAttributeAssignment_e) attributeAssignmentSet.getAttributeAssignment(j);
	
					if(!isExist){
						if((directAssignment.getAttribute().getName().equals(curPhysAtt.getName())) //identical phys
								&&(directAssignment.getAttribute().type == curPhysAtt.type)){
							isExist = true;
							ELogicalAttribute newLogAtt = (ELogicalAttribute)directAssignment.getLogicalAttribute().get(0); //user_log_att
							userLogicalData.addAttribute(newLogAtt);
						}
					}
				}
				if(!isExist){
					unusesAttributeIndexes.add(i);
				}
			}
    	}
    	else if(attributeAssignmentType == AttributeAssignmentType.ReversePivotAttributeAssignment){
    		instancePool = new AttributeVectorPool();
    		instancePool.cur_row_indx = 0;
    		instancePool.instances_row_indx = -1;
    		
    		ELogicalAttribute logAtt_transact_id = new ELogicalAttribute();
    		logAtt_transact_id.setName("transactId");
    		logAtt_transact_id.setAttributeType(AttributeType.categorical);
    		userLogicalData.addAttribute(logAtt_transact_id); 
    		
    		ELogicalAttribute logAtt_item = new ELogicalAttribute();
    		logAtt_item.setName("item");
    		logAtt_item.setAttributeType(AttributeType.categorical);
    		userLogicalData.addAttribute(logAtt_item);
    		
			for(int i = 0; i < physicalData.getAttributeCount(); i++){
				boolean isExist = false;
				PhysicalAttribute curPhysAtt = physicalData.getAttribute(i);
				for (Attribute phAtt : ((EReversePivotAttributeAssignment)attributeAssignmentSet.getAttributeAssignment(0)).getSelectorAttribute()) {
					if((phAtt.getName().equals(curPhysAtt.getName())) &&(phAtt.type == curPhysAtt.type)){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					unusesAttributeIndexes.add(i);
				}
			}
    	}
	}
	
	public Map<PhysicalAttribute, ELogicalAttribute> getMapAttributes() throws MiningException{
		Map<PhysicalAttribute, ELogicalAttribute> mapAttributes = new HashMap<>();
		unusesAttributeIndexes = new ArrayList<>(); //empty
		userLogicalData = new ELogicalData();
		
		AttributeValuesMapping valueMap = new AttributeValuesMapping();
		if(logicalData == null){
			//generate!
			for (int i = 0; i < physicalData.getAttributeCount(); i++) {
				PhysicalAttribute physicalAttribute = physicalData.getAttribute(i);
				ELogicalAttribute logicalAttribute = valueMap.getLogicalAttribute(physicalAttribute);
				mapAttributes.put(physicalAttribute, logicalAttribute);
				userLogicalData.addAttribute(logicalAttribute);
			}
		}
		else{
			userLogicalData = logicalData;
			for (int i = 0; i < physicalData.getAttributeCount(); i++) {
				mapAttributes.put(physicalData.getAttribute(i), logicalData.getAttribute(i));
			}
		}
		return mapAttributes;
	}

	private MiningVector directAssignmentNext() throws MiningException {
		MiningVector cursorVector = readPhysicalRecord();
		if(cursorVector == null){
			System.out.println(" return null \n");
			return null;
		}
		boolean isExistLogData = true;
		if(cursorVector.logicalData == null){
			isExistLogData = false;
		}
		else{
			if(cursorVector.logicalData.getAttributesNumber() == 0){
	 			isExistLogData = false;
	 		}
		}
 		int curIndexUserLogData = 0;
 		double[] instance = new double[ userLogicalData.getAttributesNumber() ];
 		for(int t = 0; t < cursorVector.values.length; t++){
 			if(!unusesAttributeIndexes.contains(t)){
 				double curValue = cursorVector.getValue(t);
 				if(userLogicalData.getAttribute(curIndexUserLogData).getAttributeType() == AttributeType.categorical){
 					if(isExistLogData){
 						ECategory eCategory = logicalData.getAttribute(t).getCategoricalProperties().getCategory((int) curValue);
	                		if(eCategory == null) {
								assert false;
								userLogicalData.getAttribute(curIndexUserLogData).getCategoricalProperties().addCategory(eCategory.getName(), CategoryProperty.valid);
							}
	                		else
	                			userLogicalData.getAttribute(curIndexUserLogData).setCategoricalProperties(logicalData.getAttribute(t).getCategoricalProperties());
 					}
 					else{
 						ECategory eCategory = userLogicalData.getAttribute(curIndexUserLogData).getCategoricalProperties().getCategory((int) curValue);
	                		if(eCategory == null) {
								assert false;
								userLogicalData.getAttribute(curIndexUserLogData).getCategoricalProperties().addCategory(eCategory.getName(), CategoryProperty.valid);
							}
 					}
 				}
 				else{ //to numeric
 					if(isExistLogData){
 						 if(logicalData.getAttribute(t).getAttributeType() == AttributeType.categorical){//cat to num!
 							 //getElement value category
 							ECategory eCategory = logicalData.getAttribute(t).getCategoricalProperties().getCategory((int) curValue);
 							try {
 								curValue = Double.parseDouble(eCategory.getName());
 			                }
 			                catch(NumberFormatException ex) {
 			                	System.out.println("	EXEPTION: " + ex.toString() + "  Can't parse double value this value: " + eCategory.getName());
 			                }
 						 }
 					}
 				}
 				instance[curIndexUserLogData] = curValue;
 				curIndexUserLogData ++;
 			}
 		}
 		int pos = cursorVector.getIndex();
        cursorVector = new MiningVector(instance);
        cursorVector.setIndex(pos);

        cursorVector.setLogicalData( userLogicalData );

 		return cursorVector;
	 }
	
	
	private MiningVector reversePivotAssignmentNext() throws MiningException {
		System.out.println("\n\nresult of NEXT() : \n");
		boolean isEmptyRecord = true;
		
		if((instancePool.instances_row_indx >= instancePool.instances.size()) ||
				(instancePool.instances_row_indx == -1)) {
			while (isEmptyRecord) {

				instancePool.instances.clear();
				instancePool.instances_row_indx = -1;

				//read new vector;
				MiningVector cursorVector = readPhysicalRecord();
				if (cursorVector == null) {
					instancePool.instances.clear();
					instancePool.instances_row_indx = -1;
					instancePool.cur_row_indx = 0;
					System.out.println("endoffile. clear instancePool.");
					return null;
				}

				//-----------
				instancePool.instances_row_indx = 0;
				for (int i = 0; i < cursorVector.values.length; i++) {
					double dtoken = cursorVector.getValue(i);
					String token;
					ECategory eCategory = logicalData.getAttribute(i).getCategoricalProperties().getCategory((int) dtoken);
					if (eCategory == null)
						token = Double.toString(dtoken); //0 , 1 (from category)
					else
						token = eCategory.getName(); //true, false.... null...

					if (!unusesAttributeIndexes.contains(i)) { //attribute contains in selector attributes
						double[] instance = new double[userLogicalData.getAttributesNumber()];
						if (checkAttributeSelectionFunction(token, ((EReversePivotAttributeAssignment) attributeAssignmentSet.getAttributeAssignment(0)).getAttributeSelectionFunction())) {//value - ok
							//1) transactId self! without reader!
							String tokenTransactId = String.valueOf(cursorPosition);
							Integer indexCategory_transactid = userLogicalData.getAttribute(0).getCategoricalProperties().getIndex(tokenTransactId);
							if (indexCategory_transactid == null)
								instance[0] = userLogicalData.getAttribute(0).getCategoricalProperties().addCategory(tokenTransactId, CategoryProperty.valid);
							else
								instance[0] = indexCategory_transactid.doubleValue();

							//2) reader -> data to categorical!
							PhysicalAttribute pha = physicalData.getAttribute(i);
							String item_name_or_value = null;//la.getName();
							if (((EReversePivotAttributeAssignment) attributeAssignmentSet.getAttributeAssignment(0)).getValueSelectionFunction() == ValueSelectionFunction.attribute) {
								item_name_or_value = pha.getName();
							} else if (((EReversePivotAttributeAssignment) attributeAssignmentSet.getAttributeAssignment(0)).getValueSelectionFunction() == ValueSelectionFunction.value) {
								item_name_or_value = token;
							}

							Integer indexCategory_user = userLogicalData.getAttribute(1).getCategoricalProperties().getIndex(item_name_or_value);
							if (indexCategory_user == null)
								instance[1] = userLogicalData.getAttribute(1).getCategoricalProperties().addCategory(item_name_or_value, CategoryProperty.valid);
							else
								instance[1] = indexCategory_user.doubleValue();

							instancePool.instances.add(instance);
							instancePool.instances_row_indx++;
							isEmptyRecord = false;

						}

					}
				}
				if (instancePool.instances.size() > 0) {
					instancePool.instances_row_indx = 0;
					cursorVector = new MiningVector(instancePool.instances.get(0));
					cursorVector.setLogicalData(userLogicalData);
					instancePool.instances_row_indx++;

					System.out.println("	Created vectors: ");
					for (int h = 0; h < instancePool.instances.size(); h++) {
						System.out.println("[ " + instancePool.instances.get(h)[0] + " , " + instancePool.instances.get(h)[1] + " ]");
					}
					System.out.println("	With logical data: " + userLogicalData.toString());
					System.out.println("	RETURN VECTOR: " + cursorVector.toString());

					return cursorVector;
				} else {
					System.out.println("	There is no created vectors");
				}

			}
		}
		else{
			//exist ready vector!
			MiningVector cursorVector = new MiningVector(instancePool.instances.get(instancePool.instances_row_indx));
            cursorVector.setLogicalData( userLogicalData );
            instancePool.instances_row_indx ++; 
            System.out.println("	RETURN VECTOR: " + cursorVector.toString());
            System.out.println("	With user logical data: " + userLogicalData.toString());
            return cursorVector;
		}
	    return null;
	}
	
	
	
	private boolean checkAttributeSelectionFunction(String token, AttributeSelectionFunction attSelFunc){
		switch (attSelFunc) {
		case isNotNull:
			if((!token.trim().equalsIgnoreCase("null"))){
				return true;
			}
			break;
		case isNull:
			if(token.trim().equalsIgnoreCase("null")){
				return true;
			}
			break;
		case isOne:
			int t = 0;
			try{
                t = Integer.parseInt(token);
            }
            catch(NumberFormatException e){
            	System.out.println("\n token type is not int: " + token);
            }
			if(t == 1){
				return true;
			}
			break;
			
		case isZero:
			int t2 = 1;
			try{
                t2 = Integer.parseInt(token);
            }
            catch(NumberFormatException e){
            	System.out.println("\n token type is not int: " + token);
            }
			if(t2 == 0){
				return true;
			}
			break;
			
			
		case isTrue:
			if(token.trim().equalsIgnoreCase("true")){
				return true;
			}
			break;
		case isFalse:
			if(token.trim().equalsIgnoreCase("false")){
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	
	
}
