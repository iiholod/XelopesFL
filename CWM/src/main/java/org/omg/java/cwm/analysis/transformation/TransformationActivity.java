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
import org.omg.java.cwm.objectmodel.core.Subsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
//import com.prudsys.pdm.Cwm.WarehouseOperation.*;
//import com.prudsys.pdm.Cwm.WarehouseProcess.*;

/**
 * This represents a transformation activity. Each TransformationActivity consists
 * of a set
 *
 * of TransformationSteps.
 */
public class TransformationActivity extends Subsystem
{

   /**
    * When the TransformationActivity was created.
    */
   public org.omg.java.cwm.objectmodel.core.String creationDate;
//   public ModelElement step;
   public ModelElement[] step; // manually corrected by M. Thess, 26.12.2003
//   public WarehouseActivity warehouseActivity[];
//   public ActivityExecution execution[];

   public TransformationActivity()
   {

   }

   public java.lang.String getCreationDate() {
     return creationDate.getString();
   }

   public void setCreationDate(java.lang.String creationDate) {
	   org.omg.java.cwm.objectmodel.core.String s = new org.omg.java.cwm.objectmodel.core.String();
     s.setString(creationDate);
     this.creationDate = s;
   }

   public Collection getStep() {
	   if (step == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(step) );
   }

   public void setStep(Collection step) {

     this.step = new ModelElement[ step.size() ];
     Iterator it = step.iterator();
     for (int i = 0; i < step.size(); i++)
       this.step[i] = (ModelElement) it.next();
   }

   public void addStep(ModelElement input) {

     int size = (step == null) ? 0 : step.length;
     ModelElement[] oldData = step;
     step = new ModelElement[size+1];
     if (size > 0) System.arraycopy(oldData, 0, step, 0, size);
     step[size] = input;
   }

   public void removeStep(ModelElement input) {

     int size = (step == null) ? 0 : step.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (step[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     ModelElement[] oldData = step;
     step = new ModelElement[size-1];
     for (int i = 0; i < ipos; i++)
       step[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       step[i-1] = oldData[i];
   }
}
