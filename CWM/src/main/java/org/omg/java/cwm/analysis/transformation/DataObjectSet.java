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
 * This represents a set of data objects that can be the source or target of a
 * Transformation.
 */
public class DataObjectSet extends ModelElement
{
   public Transformation sourceTransformation[];
   public Transformation targetTransformation[];
   public ModelElement element[];

   public DataObjectSet()
   {

   }

   public Collection getElement() {
	   if (element == null)
		      return new ArrayList();

	   return new ArrayList( Arrays.asList(element) );
   }

   public void setElement(Collection element) {

     this.element = new ModelElement[ element.size() ];
     Iterator it = element.iterator();
     for (int i = 0; i < element.size(); i++)
       this.element[i] = (ModelElement) it.next();
   }

   public void addElement( ModelElement input) {

     int size = (element == null) ? 0 : element.length;
     ModelElement[] oldData = element;
     element = new ModelElement[size+1];
     if (size > 0) System.arraycopy(oldData, 0, element, 0, size);
     element[size] = (ModelElement) input;
   }

   public void removeElement( ModelElement input) {

     int size = (element == null) ? 0 : element.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (element[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     ModelElement[] oldData = element;
     element = new ModelElement[size-1];
     for (int i = 0; i < ipos; i++)
       element[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       element[i-1] = oldData[i];
   }

   public Collection getSourceTransformation() {
	   if (sourceTransformation == null)
		      return new ArrayList();

		return new ArrayList( Arrays.asList(sourceTransformation) );
   }

   public void setSourceTransformation(Collection sourceTransformation) {

     this.sourceTransformation = new Transformation[ sourceTransformation.size() ];
     Iterator it = sourceTransformation.iterator();
     for (int i = 0; i < sourceTransformation.size(); i++)
       this.sourceTransformation[i] = (Transformation) it.next();
   }

   public void addSourceTransformation( Transformation input) {

     int size = (sourceTransformation == null) ? 0 : sourceTransformation.length;
     Transformation[] oldData = sourceTransformation;
     sourceTransformation = new Transformation[size+1];
     if (size > 0) System.arraycopy(oldData, 0, sourceTransformation, 0, size);
     sourceTransformation[size] = (Transformation) input;
   }

   public void removeSourceTransformation( Transformation input) {

     int size = (sourceTransformation == null) ? 0 : sourceTransformation.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (sourceTransformation[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     Transformation[] oldData = sourceTransformation;
     sourceTransformation = new Transformation[size-1];
     for (int i = 0; i < ipos; i++)
       sourceTransformation[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       sourceTransformation[i-1] = oldData[i];
   }

   public Collection getTargetTransformation() {
	   if (targetTransformation == null)
		      return new ArrayList();

	   return new ArrayList( Arrays.asList(targetTransformation) );
   }

   public void setTargetTransformation(Collection targetTransformation) {

     this.targetTransformation = new Transformation[ targetTransformation.size() ];
     Iterator it = targetTransformation.iterator();
     for (int i = 0; i < targetTransformation.size(); i++)
       this.targetTransformation[i] = (Transformation) it.next();
   }

   public void addTargetTransformation( Transformation input) {

     int size = (targetTransformation == null) ? 0 : targetTransformation.length;
     Transformation[] oldData = targetTransformation;
     targetTransformation = new Transformation[size+1];
     if (size > 0) System.arraycopy(oldData, 0, targetTransformation, 0, size);
     targetTransformation[size] = (Transformation) input;
   }

   public void removeTargetTransformation( Transformation input) {

     int size = (targetTransformation == null) ? 0 : targetTransformation.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (targetTransformation[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     Transformation[] oldData = targetTransformation;
     targetTransformation = new Transformation[size-1];
     for (int i = 0; i < ipos; i++)
       targetTransformation[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       targetTransformation[i-1] = oldData[i];
   }
}
