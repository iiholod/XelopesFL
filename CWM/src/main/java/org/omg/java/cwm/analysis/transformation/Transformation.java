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
import org.omg.java.cwm.objectmodel.core.Namespace;
import org.omg.java.cwm.objectmodel.core.ProcedureExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * This represents a transformation from a set of sources to a set of targets.
 *
 *
 *
 * If a model already exists for the object that performs the Transformation, then
 * the model can be related to the Transformation via a TransformationUse
 * dependency.
 */
public class Transformation extends Namespace
{

   /**
    * Any code or script for the Transformation.
    */
   public ProcedureExpression function;

   /**
    * A short description for any code or script performed by the Transformation.
    */
   public org.omg.java.cwm.objectmodel.core.String functionDescription;

   /**
    * This Transformation is the primary transformation for the associated
    * TransformationTask.
    */
   public org.omg.java.cwm.objectmodel.core.Boolean isPrimary;

//   public Dependency use;
   public Dependency[] use; // manually corrected by M. Thess, 26.12.2003

   public DataObjectSet[] source;
   public DataObjectSet[] target;
   public TransformationTask[] task;

   public Transformation()
   {

   }
   public java.lang.String getFunctionDescription() {
     return functionDescription.getString();
   }

   public void setFunctionDescription(java.lang.String functionDescription) {
	   org.omg.java.cwm.objectmodel.core.String s = new org.omg.java.cwm.objectmodel.core.String();
     s.setString(functionDescription);
     this.functionDescription = s;
   }

   public ProcedureExpression getFunction() {
     return function;
   }

   public void setFunction(ProcedureExpression function) {
     this.function = function;
   }

   public java.lang.Boolean getIsPrimary() {
     return isPrimary.getBoolean();
   }

   public void setIsPrimary(java.lang.Boolean isPrimary) {
	   org.omg.java.cwm.objectmodel.core.Boolean b = new org.omg.java.cwm.objectmodel.core.Boolean();
     b.setBoolean(isPrimary);
     this.isPrimary = b;
   }

   public boolean isPrimaryValue() {
     return isPrimary.isBooleanValue();
   }

   public void setIsPrimary(boolean input) {
     setIsPrimary( new java.lang.Boolean(input) );
   }

   public Collection getSource() {
	   if (source == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(source) );
   }

   public void setSource(Collection source) {

     this.source = new DataObjectSet[ source.size() ];
     Iterator it = source.iterator();
     for (int i = 0; i < source.size(); i++)
       this.source[i] = (DataObjectSet) it.next();
   }

   public void addSource(DataObjectSet input) {

     int size = (source == null) ? 0 : source.length;
     DataObjectSet[] oldData = source;
     source = new DataObjectSet[size+1];
     if (size > 0) System.arraycopy(oldData, 0, source, 0, size);
     source[size] = input;
   }

   public void removeSource(DataObjectSet input) {

     int size = (source == null) ? 0 : source.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (source[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     DataObjectSet[] oldData = source;
     source = new DataObjectSet[size-1];
     for (int i = 0; i < ipos; i++)
       source[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       source[i-1] = oldData[i];
   }

   public Collection getTarget() {
	   if (target == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(target) );
   }

   public void setTarget(Collection target) {

     this.target = new DataObjectSet[ target.size() ];
     Iterator it = target.iterator();
     for (int i = 0; i < target.size(); i++)
       this.target[i] = (DataObjectSet) it.next();
   }

   public void addTarget(DataObjectSet input) {

     int size = (target == null) ? 0 : target.length;
     DataObjectSet[] oldData = target;
     target = new DataObjectSet[size+1];
     if (size > 0) System.arraycopy(oldData, 0, target, 0, size);
     target[size] = input;
   }

   public void removeTarget(DataObjectSet input) {

     int size = (target == null) ? 0 : target.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (target[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     DataObjectSet[] oldData = target;
     target = new DataObjectSet[size-1];
     for (int i = 0; i < ipos; i++)
       target[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       target[i-1] = oldData[i];
   }

   public Collection getUse() {
	   if (use == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(use) );
   }

   public void setUse(Collection use) {

     this.use = new Dependency[ use.size() ];
     Iterator it = use.iterator();
     for (int i = 0; i < use.size(); i++)
       this.use[i] = (Dependency) it.next();
   }

   public void addUse(Dependency input){

     int size = (use == null) ? 0 : use.length;
     Dependency[] oldData = use;
     use = new Dependency[size+1];
     if (size > 0) System.arraycopy(oldData, 0, use, 0, size);
     use[size] = input;
   }

   public void removeUse(Dependency input){

     int size = (use == null) ? 0 : use.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (use[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     Dependency[] oldData = use;
     use = new Dependency[size-1];
     for (int i = 0; i < ipos; i++)
       use[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       use[i-1] = oldData[i];
   }
}
