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
 * This is used to define explicit order-of-execution relationships among
 * TransformationSteps. It may be used independent of or in conjunction with
 * PrecedenceConstraint
 */
public class StepPrecedence extends Dependency
{
//   public ModelElement precedingStep;
   public ModelElement precedingStep[]; // manually corrected by M. Thess, 26.12.2003
//   public ModelElement succeedingStep;
   public ModelElement succeedingStep[]; // manually corrected by M. Thess, 26.12.2003

   public StepPrecedence() {
   }

   public Collection getPrecedingStep() {
	   if (precedingStep == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(precedingStep) );
   }

   public void setPrecedingStep(Collection precedingStep) {

     this.precedingStep = new ModelElement[ precedingStep.size() ];
     Iterator it = precedingStep.iterator();
     for (int i = 0; i < precedingStep.size(); i++)
       this.precedingStep[i] = (ModelElement) it.next();
   }

   public void addPrecedingStep(ModelElement input) {

     int size = (precedingStep == null) ? 0 : precedingStep.length;
     ModelElement[] oldData = precedingStep;
     precedingStep = new ModelElement[size+1];
     if (size > 0) System.arraycopy(oldData, 0, precedingStep, 0, size);
     precedingStep[size] = (ModelElement) input;
   }

   public void removePrecedingStep(ModelElement input) {

     int size = (precedingStep == null) ? 0 : precedingStep.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (precedingStep[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     ModelElement[] oldData = precedingStep;
     precedingStep = new ModelElement[size-1];
     for (int i = 0; i < ipos; i++)
       precedingStep[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       precedingStep[i-1] = oldData[i];
   }

   public Collection getSucceedingStep() {
	   if (succeedingStep == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(succeedingStep) );
   }

   public void setSucceedingStep(Collection succeedingStep) {

     this.succeedingStep = new ModelElement[ succeedingStep.size() ];
     Iterator it = succeedingStep.iterator();
     for (int i = 0; i < succeedingStep.size(); i++)
       this.succeedingStep[i] = (ModelElement) it.next();
   }

   public void addSucceedingStep(ModelElement input) {

     int size = (succeedingStep == null) ? 0 : succeedingStep.length;
     ModelElement[] oldData = succeedingStep;
     succeedingStep = new ModelElement[size+1];
     if (size > 0) System.arraycopy(oldData, 0, succeedingStep, 0, size);
     succeedingStep[size] = (ModelElement) input;
   }

   public void removeSucceedingStep(ModelElement input) {

     int size = (succeedingStep == null) ? 0 : succeedingStep.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (succeedingStep[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     ModelElement[] oldData = succeedingStep;
     succeedingStep = new ModelElement[size-1];
     for (int i = 0; i < ipos; i++)
       succeedingStep[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       succeedingStep[i-1] = oldData[i];
   }
}
