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

package org.omg.java.cwm.objectmodel.core;

import java.io.Serializable;

/**
 * The Any data type is used to indicate that an attribute or parameter may take
 * values from any of the available data types. In CWM, the set of data types an
 * Any attribute or parameter may assume includes the data types and enumerations
 * described in this chapter plus any available instances of the Classifier class.
 *
 *
 *
 * There is no default value for data type Any.
 */

public class Any implements Cloneable, Serializable
{
   private java.lang.Object object;

   /**
    * Empty constructor.
    */
   public Any(java.lang.Object obj)
   {
	   object = obj;
   }

   public Object getObject() {
     return object;
   }

   public void setObject(Object object) {
     this.object = object;
   }
   
   public Object clone() {
   	Object o = null;
	    try {
	      o = super.clone();
	    } catch(CloneNotSupportedException e) {
	      System.err.println(this.getClass().toString() + " can't be cloned");
	    }
	    
	    return o;
	}
   
   @Override
	public boolean equals(Object obj) {
	   if(object != null)
		   return object.equals(obj);
	   
	   return false;
	}
   
   @Override
	public int hashCode() {
	   if(object != null)
		   return object.hashCode();
	   
	   return super.hashCode();
	}
}
