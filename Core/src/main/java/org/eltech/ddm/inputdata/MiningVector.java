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
import org.eltech.ddm.miningcore.miningdata.ECategory;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

import java.io.Serializable;
import java.util.Vector;

/**
 * Represents vector of the data matrix. To every mining vector
 * the meta data is attached. <p>
 *
 * In mathematical formulation, a mining vector mv is a vector in the
 * linear space spanned by the mining attributes ma_i, i = 1, ..., n
 * (having in mind that the term 'linear space' is not fully correct
 * due to the ambivalent nature of categorical attributes).
 * Hence the the meta data defines the basis and the values v_i are the
 * vector coordinates: mv = sum_{i=1}^n v_i * ma_i. <p>
 *
 * The values of the vector are stored as a double array. For categorical
 * attributes, the category keys are stored and the categories
 * are available through the meta data.
 */
public class MiningVector implements   Serializable //Cloneable, extends com.prudsys.pdm.Cwm.Instance.Object implements MiningMatrixElement
{
    // -----------------------------------------------------------------------
    //  Variables definitions
    // -----------------------------------------------------------------------
    /** Meta data of mining vector. */
    protected ELogicalData logicalData;

    /** The vector's attribute values. */
    protected double[] values;

    /** The vector's weight. */
    protected double weight;

    /** The vector's id. */
    protected int index = -1;

    // converting double value to integer index of category
    protected static int getIntValue(double value){
    	return (int) Math.round(value);
    }

    // -----------------------------------------------------------------------
    //  Constructors
    // -----------------------------------------------------------------------
    /**
     * Empty constructor.
     */
    public MiningVector()
    {
            }

   /**
    * Constructor that inititalizes mining vector with given
    * values. Reference to the meta data is attributes to null. (I.e. the vector
    * doesn't have access to information about the attribute types.)
    *
    * @param values a vector of attribute values
    */
    public MiningVector( double[] values )
    {
        this.values = values;
        this.weight = 1;
        this.logicalData = null;
    }

    // -----------------------------------------------------------------------
    //  Getter and setter methods
    // -----------------------------------------------------------------------
    /**
     * Returns meta data of mining vector.
     *
     * @return meta data of mining vector
     */
    public ELogicalData getLogicalData()
    {
        return logicalData;
    }

    /**
     * Sets meta data of mining vector.
     *
     * @param logicalData new meta data of mining vector
     */
    public void setLogicalData(ELogicalData logicalData)
    {
        this.logicalData = logicalData;
    }

    /**
     * Returns index of mining vector.
     *
     * @return index of mining vector
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Sets index of mining vector.
     *
     * @param id new index of mining vector
     */
    public void setIndex(int id)
    {
        this.index = id;
    }

    /**
     * Returns the weight of the vector.
     *
     * @return vector weight
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * Set weight of the vector.
     *
     * @param weight new vector weight
     */
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    /**
     * Get value array of the mining vector.
     *
     * @return value array of the vector (as copy)
     */
    public double[] getValues()
    {
      double[] v = new double[ values.length ];
        System.arraycopy(values, 0, v, 0, values.length);
      return v;
    }

    // -----------------------------------------------------------------------
    //  Value access methods
    // -----------------------------------------------------------------------
    /**
     * Sets value at specified position.
     *
     * @param attributeIndex attribute index for new value
     * @param newValue new value
     */
    public void setValue( int attributeIndex, double newValue )
    {
        values[attributeIndex] = newValue;
    }

   /**
    * Returns vector's attribute value in internal format.
    *
    * @param attributeIndex attribute index for value to read
    * @return the specified value as a double (If the corresponding
    * attribute is categorical then it returns the value's index as a
    * double).
    */
    public double getValue( int attributeIndex )
    {
        return values[attributeIndex];
    }

    /**
     * Returns value at specified attribute.
     *
     * @param attribute mining attribute
     * @return value of mining attribute
     */
    @Deprecated
    public double getValue( ELogicalAttribute attribute )
    {
        int index = logicalData.getAttributeIndex( attribute );
        return getValue(index);
    }

    /**
     * Returns value at specified attribute name.
     *
     * @param attributeName mining attribute name
     * @return value of mining attribute
     */
    public double getValue( String attributeName )
    {
        return getValue( logicalData.getAttribute( attributeName ) );
    }

    /**
     * Returns categorical value for categorical attribute.
     *
     * @param attributeIndex index of (categoryical) attribute
     * @return category of value of categorical attribute
     */
    public ECategory getValueCategory( int attributeIndex ) throws MiningException {

    	ELogicalAttribute la = logicalData.getAttribute(attributeIndex);
        if (la.getAttributeType() != AttributeType.categorical )
          throw new MiningException(MiningErrorCode.INVALID_DATA_TYPE, "attribute " + la.getName() + " must be categorical");

        return la.getCategoricalProperties().getCategory((int) getValue(attributeIndex));
    }

    /**
     * Returns categorical value of specified categorical attribute.
     *
     * @param catAttribute categoryical attribute
     * @return category of value of categorical attribute
     */
    @Deprecated
    public ECategory getValueCategory( ELogicalAttribute catAttribute ) throws MiningException {
        if (catAttribute.getAttributeType() != AttributeType.categorical )
            throw new MiningException(MiningErrorCode.INVALID_DATA_TYPE, "attribute " + catAttribute.getName() + " must be categorical");

        return catAttribute.getCategoricalProperties().getCategory( getIntValue(getValue(catAttribute)) );
    }

    /**
     * Returns categorical value of specified categorical attribute name.
     *
     * @param attributeName categorical attribute name
     * @return category of value of categorical attribute
     */
    @Deprecated
    public ECategory getValueCategory( String attributeName ) throws MiningException {

    	ELogicalAttribute la = logicalData.getAttribute(attributeName);
    	int indexAttr = logicalData.getAttributeIndex(la);

    	return getValueCategory(indexAttr);
    }

    /**
     * Does the mining vector contain a missing value
     * at specified position?
     *
     * @param index given attribute index
     * @return true if missing value, else false
     */
    public boolean isMissing( int index ) throws MiningException
    {
    	ELogicalAttribute la = logicalData.getAttribute(index);
    	boolean missing = false;
        if (la.getAttributeType() == AttributeType.categorical ){
        	CategoryProperty currentCP = la.getCategoricalProperties().getProperty((int) getValue(index));
        	if(currentCP == CategoryProperty.missing)
        		missing = true;
        }
        else {
        	missing = Double.isNaN(getValue(index));
        }

        return missing;
    }

    /**
     * Does the mining vector contain missing values?
     *
     * @return true if contains missing values, else false
     */
    public boolean isMissing() throws MiningException {

      for (int i = 0; i < values.length; i++)
        if ( isMissing(i) )
          return true;

      return false;
    }

    // -----------------------------------------------------------------------
    //  Export methods
    // -----------------------------------------------------------------------
    /**
     * Returns mining vector as vector of strings. For categorical attributes
     * the category objects are contained, for numeric attributes
     * the double value wrapped as Double.
     *
     * @return mining vector as Java vector
     * @throws MiningException
     */
    public Vector toVector() throws MiningException
    {
        Vector vector = new Vector();
        for (int i = 0; i < values.length; i++) {
        	ELogicalAttribute attr = logicalData.getAttribute(i);
            if(attr.getAttributeType() == AttributeType.categorical)
            {
                vector.add( attr.getCategoricalProperties().getValue(getIntValue(values[i])));
            }
            else
            {
                vector.add(values[i]);
            }
        }
        return vector;
    }

    // -----------------------------------------------------------------------
    //  java.lang.Object methods
    // -----------------------------------------------------------------------

//    public Object clone() {
//    	MiningVector o = null;
//        o = (MiningVector)super.clone();
//
//        if(logicalData != null)
//	    	o.logicalData = (ELogicalData)logicalData.clone();
//
//		if (values != null)
//			o.values = (double[])values.clone();
//
//		return o;
//	}

    /**
    * Returns the dataset as a string in ARFF format. Strings
    * are quoted if they contain whitespace characters, or if they
    * are a question mark.
    *
    * @return the dataset in ARFF format as a string
    */
    public String toString()
    {
        String description = "\"";
        for (int i = 0; i < values.length; i++) {

            if (logicalData == null) {
              description = description + values[i] + ",";
              continue;
            }
            try {
				if (isMissing( i )  ) {
				    description = description + "?" + ",";
				    continue;
				}

	            ELogicalAttribute attr = logicalData.getAttribute(i);
	            if(attr.getAttributeType() == AttributeType.categorical)
	            {
	              description = description + attr.getCategoricalProperties().getValue(getIntValue(values[i]))+",";
	            }
	            else
	            {
	              description = description + values[i] +",";
	            }
			} catch (MiningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if (description.length() > 0)
          description = description.substring( 0, description.lastIndexOf( "," ) );

        description += "\", id = " + index;

        return description;
    }

    public boolean equals(MiningVector miningVector) {
    	if(miningVector == null)
    		return false;

    	if((logicalData == null) || (miningVector.logicalData == null)){
            return logicalData == miningVector.logicalData;
    	}
    	else{
    		int countAttribute = logicalData.getAttributesNumber();
    		if(miningVector.logicalData.getAttributesNumber() != countAttribute)
    			return false;

    		for(int i = 0; i<countAttribute; i++)
    			if(this.getValue(i) != miningVector.getValue(i))
    				return false;
    	}

    	return true;
    }

    public boolean equalsWithId(MiningVector miningVector) {
    	return equals(miningVector) && (index == miningVector.getIndex());
    }
}
