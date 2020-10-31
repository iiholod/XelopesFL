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

import org.omg.java.cwm.objectmodel.core.ModelElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * This represents a specialized Transformation which consists of a group of
 * ClassifierMaps.
 */
public class TransformationMap extends Transformation
{
//   public ModelElement classifierMap;
   public ModelElement[] classifierMap;  // manually corrected by M. Thess, 25.12.2003

   public TransformationMap()
   {

   }

   public Collection getClassifierMap() {
	   if (source == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(source) );
   }

   public void setClassifierMap(Collection classifierMap) {

     this.classifierMap = new ModelElement[ classifierMap.size() ];
     Iterator it = classifierMap.iterator();
     for (int i = 0; i < classifierMap.size(); i++)
       this.classifierMap[i] = (ModelElement) it.next();
   }

   public void addClassifierMap(ModelElement input) {

     int size = (classifierMap == null) ? 0 : classifierMap.length;
     ModelElement[] oldData = classifierMap;
     classifierMap = new ModelElement[size+1];
     if (size > 0) System.arraycopy(oldData, 0, classifierMap, 0, size);
     classifierMap[size] = input;
   }

   public void removeClassifierMap(ModelElement input) {

     int size = (classifierMap == null) ? 0 : classifierMap.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (classifierMap[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     ModelElement[] oldData = classifierMap;
     classifierMap = new ModelElement[size-1];
     for (int i = 0; i < ipos; i++)
       classifierMap[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       classifierMap[i-1] = oldData[i];
   }
}
