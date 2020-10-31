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

import org.omg.java.cwm.objectmodel.core.Feature;
import org.omg.java.cwm.objectmodel.core.ModelElement;
import org.omg.java.cwm.objectmodel.core.ProcedureExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * This represents a mapping of source Features to target Features.
 */
public class FeatureMap extends ModelElement
{

   /**
    * Any code or script for the FeatureMap.
    */
   public ProcedureExpression function;

   /**
    * A short description for any code or script performed by the FeatureMap.
    */
   public org.omg.java.cwm.objectmodel.core.String functionDescription;
   public ClassifierMap classifierMap;
   public Feature[] target;
   public Feature[] source;

   public FeatureMap()
   {

   }

   public ProcedureExpression getFunction() {
     return function;
   }

   public void setFunction(ProcedureExpression function) {
     this.function = function;
   }

   public java.lang.String getFunctionDescription() {
     return functionDescription.getString();
   }

   public void setFunctionDescription(java.lang.String functionDescription) {
	   org.omg.java.cwm.objectmodel.core.String s = new org.omg.java.cwm.objectmodel.core.String();
     s.setString(functionDescription);
     this.functionDescription = s;
   }

   public ClassifierMap getClassifierMap() {
     return classifierMap;
   }

   public void setClassifierMap(ClassifierMap classifierMap) {
     this.classifierMap = classifierMap;
   }

   public Collection getSource() {
	   if (source == null)
		      return new ArrayList();

	   return new ArrayList( Arrays.asList(source) );
   }

   public void setSource(Collection source) {

     this.source = new Feature[ source.size() ];
     Iterator it = source.iterator();
     for (int i = 0; i < source.size(); i++)
       this.source[i] = (Feature) it.next();
   }

   public void addSource(Feature input) {

     int size = (source == null) ? 0 : source.length;
     Feature[] oldData = source;
     source = new Feature[size+1];
     if (size > 0) System.arraycopy(oldData, 0, source, 0, size);
     source[size] = input;
   }

   public void removeSource(Feature input) {

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

     Feature[] oldData = source;
     source = new Feature[size-1];
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

     this.target = new Feature[ target.size() ];
     Iterator it = target.iterator();
     for (int i = 0; i < target.size(); i++)
       this.target[i] = (Feature) it.next();
   }

   public void addTarget(Feature input) {

     int size = (target == null) ? 0 : target.length;
     Feature[] oldData = target;
     target = new Feature[size+1];
     if (size > 0) System.arraycopy(oldData, 0, target, 0, size);
     target[size] = input;
   }

   public void removeTarget(Feature input) {

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

     Feature[] oldData = target;
     target = new Feature[size-1];
     for (int i = 0; i < ipos; i++)
       target[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       target[i-1] = oldData[i];
   }
}
