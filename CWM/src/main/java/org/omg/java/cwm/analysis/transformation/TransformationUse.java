/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/**
 * Title: XELOPES Data Mining Library
 * Description: The XELOPES library is an open platform-independent and data-source-independent library for Embedded Data Mining.
 * Copyright: Copyright (c) 2002 Prudential Systems Software GmbH
 * Company: ZSoft (www.zsoft.ru), Prudsys (www.prudsys.com)
 * @author Valentine Stepanenko (valentine.stepanenko@zsoft.ru)
 * @version 1.0
 */

package org.omg.java.cwm.analysis.transformation;

import org.omg.java.cwm.objectmodel.core.Dependency;
import org.omg.java.cwm.objectmodel.core.ModelElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * This is a specialized dependency used to associate a Transformation to the
 * model of an existing object (e.g., program, query, or rule) that performs the
 * transformation.
 */
public class TransformationUse extends Dependency
{

   /**
    * Identifies the type of object that can perform the transformation.
    */
   public org.omg.java.cwm.objectmodel.core.String type;

//   public ModelElement transformation;
   public ModelElement transformation[]; // manually corrected by M. Thess, 26.12.2003

//   public ModelElement operation;
   public ModelElement operation[]; // manually corrected by M. Thess, 26.12.2003

   public TransformationUse()
   {

   }

   public Collection getOperation() {
	   if (operation == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(operation) );
   }

   public void setOperation(Collection operation) {

     this.operation = new ModelElement[ operation.size() ];
     Iterator it = operation.iterator();
     for (int i = 0; i < operation.size(); i++)
       this.operation[i] = (ModelElement) it.next();
   }

   public void addOperation(ModelElement input) {

     int size = (operation == null) ? 0 : operation.length;
     ModelElement[] oldData = operation;
     operation = new ModelElement[size+1];
     if (size > 0) System.arraycopy(oldData, 0, operation, 0, size);
     operation[size] = (ModelElement) input;
   }

   public void removeOperation(ModelElement input) {

     int size = (operation == null) ? 0 : operation.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (operation[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     ModelElement[] oldData = operation;
     operation = new ModelElement[size-1];
     for (int i = 0; i < ipos; i++)
       operation[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       operation[i-1] = oldData[i];
   }

   public Collection getTransformation() {
	   if (transformation == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(transformation) );
   }

   public void setTransformation(Collection transformation) {

     this.transformation = new ModelElement[ transformation.size() ];
     Iterator it = transformation.iterator();
     for (int i = 0; i < transformation.size(); i++)
       this.transformation[i] = (ModelElement) it.next();
   }

   public void addTransformation(ModelElement input) {

     int size = (transformation == null) ? 0 : transformation.length;
     ModelElement[] oldData = transformation;
     transformation = new ModelElement[size+1];
     if (size > 0) System.arraycopy(oldData, 0, transformation, 0, size);
     transformation[size] = (ModelElement) input;
   }

   public void removeTransformation(ModelElement input) {

     int size = (transformation == null) ? 0 : transformation.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (transformation[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     ModelElement[] oldData = transformation;
     transformation = new ModelElement[size-1];
     for (int i = 0; i < ipos; i++)
       transformation[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       transformation[i-1] = oldData[i];
   }

   public java.lang.String getType() {
     return type.getString();
   }

   public void setType(java.lang.String type) {
	   org.omg.java.cwm.objectmodel.core.String s = new org.omg.java.cwm.objectmodel.core.String();
     s.setString(type);
     this.type = s;
   }
}
