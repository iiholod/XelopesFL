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

/**
 * Represents sparse mining vectors. A sparse vector only
 * stores the non-zero elements together with their
 * attribute index.
 */
public class MiningSparseVector extends MiningVector
{
    // -----------------------------------------------------------------------
    //  Variables definitions
    // -----------------------------------------------------------------------
    /** The index of the attribute associated with each stored value. */
    protected int[] indexes;

    // -----------------------------------------------------------------------
    //  Constructors
    // -----------------------------------------------------------------------
    /**
     * Empty constructor.
     */
    protected MiningSparseVector()
    {
    }

    /**
     * Constructor that generates a sparse vector from the given
     * mining vector.
     *
     * @param vector the vector from which the attribute values
     * and the weight are to be copied
     */
    public MiningSparseVector(MiningVector vector) {

      // Init:
      this.weight = vector.getWeight();

      // Vector is already of sparse type:
      if (vector instanceof MiningSparseVector) {
        this.values  = ((MiningSparseVector) vector).values;
        this.indexes = ((MiningSparseVector) vector).indexes;
      }
      // Vector is just of mining vector type:
      else {
        // Get all non-zero values of vector:
        int nAtt = vector.getValues().length;
        double[] tempValues = new double[nAtt];
        int[] tempIndexes   = new int[nAtt];
        int vals = 0;
        for (int i = 0; i < nAtt; i++) {
          if (vector.getValue(i) != 0) {
            tempValues[vals]  = vector.getValue(i);
            tempIndexes[vals] = i;
            vals = vals + 1;
          };
        };

        // Copy values to sparse vector:
        values  = new double[vals];
        indexes = new int[vals];
        System.arraycopy(tempValues , 0, values,  0, vals);
        System.arraycopy(tempIndexes, 0, indexes, 0, vals);
      };
    }

    /**
     * Creates MiningSparseVector from its weight, the non-zero values, and
     * their indexes.
     *
     * @param weight sparse vector's weight
     * @param values array of values of non-zero elements
     * @param indexes attribute indexes of non-zero elements
     */
    public MiningSparseVector( double weight, double[] values, int[] indexes )
    {
        this.values  = values;
        this.indexes = indexes;
        this.weight  = weight;
    }

    // -----------------------------------------------------------------------
    //  Getter and setter methods
    // -----------------------------------------------------------------------
    /**
     * Returns the number of values in the sparse vector.
     *
     * @return the number of values
     */
    public int getNumValuesSparse() {

      return indexes.length;
    }

    /**
     * Get value array of the mining vector.
     *
     * @return value array of the vector
     */
    public double[] getValues()
    {
        int size = logicalData.getAttributesNumber();
        double[] v = new double[ size ];
        for( int i = 0; i < indexes.length; i++ )
        {
            v[indexes[i]] = values[i];
        }
        return v;
    }

    // -----------------------------------------------------------------------
    //  Value access methods
    // -----------------------------------------------------------------------
    /**
     * Returns a vector's attribute value in internal format.
     *
     * @param indexOfIndex the index of the attribute's index
     * @return the specified value as a double (If the corresponding
     * attribute is categorical then it returns the value's index as a
     * double)
     */
    public double getValueSparse(int indexOfIndex) {

      return values[indexOfIndex];
    }

    /**
     * Returns the index of the attribute stored at the given position.
     *
     * @param position the position
     * @return the index of the attribute stored at the given position
     */
    public int getIndex(int position) {

      return indexes[position];
    }

    /**
     * Returns (relative) attribute index for a given index.
     *
     * @param index atribute index
     * @return (relative) attribute index
     */
    public int locateIndex(int index)
    {
        int min = 0, max = indexes.length - 1;
        // Binary search
        while (max >= min)
        {
            int current = (max + min) / 2;
            if(indexes[current] > index)
            {
                max = current - 1;
            }
            else
            if(indexes[current] < index)
            {
                min = current + 1;
            }
            else
            {
                return current;
            }
        }
        return max;
    }

    /**
      * Returns vector's attribute value.
      *
      * @param attributeIndex the attribute's index
      * @return the specified value as a double (if the corresponding
      * attribute is categorical then it returns the value's index as a
      * double)
      */
      public double getValue(int attributeIndex)
      {
          int index = locateIndex(attributeIndex);
          if ((index >= 0) && (indexes[index] == attributeIndex))
          {
              return values[index];
          }
          else
          {
              return 0.0;
          }
    }

    // -----------------------------------------------------------------------
    //  java.lang.Object methods
    // -----------------------------------------------------------------------
    /**
     * Copies sparse mining vector inclding its meta data. Shallow copy.
     *
     * @return copy of mining sparse vector
     */
    public Object clone() {

      MiningSparseVector minSparseVec = new MiningSparseVector(this);
      minSparseVec.setLogicalData( this.getLogicalData() );

      return minSparseVec;
    }

//    /**
//     * Returns string representation of full vector.
//     *
//     * @return string representation of full vector.
//     */
//    public String toString()
//    {
//        String description = "";
//        int size = logicalData.getAttributes().size();
//        for (int i = 0; i < size; i++) {
//            if (logicalData == null) {
//              description = description + Double.toString(getValue(i)) + ",";
//              continue;
//            };
//
//            try {
//				if ( isMissing(i) ) {
//				    description = description + "?" + ",";
//				    continue;
//				}
//
//	            LogicalAttribute attr = logicalData.getAttribute(i);
//	            if(attr.getAttributeType() == AttributeType.categorical)
//	            {
//	              description = description + attr.getCategoricalProperties().getName(getIntValue(values[i]))+ ",";
//	            }
//	            else
//	            {
//	              description = description + Double.toString(getValue(i)) + ",";
//	            }
//			} catch (MiningException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			};
//        };
//        description = description.substring( 0, description.lastIndexOf( "," ) );
//        return description;
//    }
//
//    /**
//     * Returns string representation of sparse vector.
//     *
//     * @return string representation of sparse vector.
//     */
//    public String toStringSparse()
//    {
//        String description = "";
//        for (int i = 0; i < getNumValuesSparse(); i++) {
//            int ind = getIndex(i);
//            description = description + String.valueOf(ind) + ": ";
//
//            if (logicalData == null) {
//              description = description + Double.toString(getValueSparse(i)) + ",";
//              continue;
//            };
//
//            try {
//				if ( isMissing(i) ) {
//				    description = description + "?" + ",";
//				    continue;
//				}
//
//	            LogicalAttribute attr = logicalData.getAttribute(i);
//	            if(attr.getAttributeType() == AttributeType.categorical)
//	            {
//	                description = description + attr.getCategoricalProperties().getName(getIntValue(values[i]))+ ",";
//	            }
//	            else
//	            {
//	              description = description + Double.toString(getValueSparse(i)) + ",";
//	            }
//			} catch (MiningException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			};
//        }
//        description = description.substring( 0, description.lastIndexOf( "," ) );
//        return description;
//    }
}
