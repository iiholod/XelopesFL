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

import org.omg.java.cwm.objectmodel.core.Constraint;
import org.omg.java.cwm.objectmodel.core.Dependency;
import org.omg.java.cwm.objectmodel.core.ModelElement;
import org.omg.java.cwm.objectmodel.core.Namespace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
//import com.prudsys.pdm.Cwm.WarehouseProcess.*;
//import com.prudsys.pdm.Cwm.WarehouseOperation.*;

/**
 * This represents the usage of a TransformationTask in a TransformationActivity.
 * A TransformationStep relates to one TransformationTask.
 *
 *
 *
 * TransformationSteps are used to coordinate the flow of control between their
 * TransformationTasks. Ordering of the TransformationSteps are defined using the
 * PrecedenceConstrainedBy dependency.
 */
public class TransformationStep extends ModelElement
{
   public Namespace activity;
//   public Constraint precedence;
   public Constraint precedence[]; // manually corrected by M. Thess, 26.12.2003
//   public Dependency precedingStep;
   public Dependency precedingStep[]; // manually corrected by M. Thess, 26.12.2003
//   public Dependency succeedingStep;
   public Dependency succeedingStep[]; // manually corrected by M. Thess, 26.12.2003
   public TransformationTask task;
//   public WarehouseStep warehouseStep[];
//   public StepExecution execution[];

   public TransformationStep()
   {

   }

   public Namespace getActivity() {
     return activity;
   }

   public void setActivity(Namespace activity) {
     this.activity = (Namespace) activity;
   }

   public Collection getPrecedence() {
	   if (precedence == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(precedence) );
   }

   public void setPrecedence(Collection precedence) {

     this.precedence = new Constraint[ precedence.size() ];
     Iterator it = precedence.iterator();
     for (int i = 0; i < precedence.size(); i++)
       this.precedence[i] = (Constraint) it.next();
   }

   public void addPrecedence(Constraint input) {

     int size = (precedence == null) ? 0 : precedence.length;
     Constraint[] oldData = precedence;
     precedence = new Constraint[size+1];
     if (size > 0) System.arraycopy(oldData, 0, precedence, 0, size);
     precedence[size] = (Constraint) input;
   }

   public void removePrecedence(Constraint input) {

     int size = (precedence == null) ? 0 : precedence.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (precedence[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     Constraint[] oldData = precedence;
     precedence = new Constraint[size-1];
     for (int i = 0; i < ipos; i++)
       precedence[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       precedence[i-1] = oldData[i];
   }

   public Collection getPrecedingStep() {
	   if (precedingStep == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(precedingStep) );
   }

   public void setPrecedingStep(Collection precedingStep) {

     this.precedingStep = new Dependency[ precedingStep.size() ];
     Iterator it = precedingStep.iterator();
     for (int i = 0; i < precedingStep.size(); i++)
       this.precedingStep[i] = (Dependency) it.next();
   }

   public void addPrecedingStep(Dependency input) {

     int size = (precedingStep == null) ? 0 : precedingStep.length;
     Dependency[] oldData = precedingStep;
     precedingStep = new Dependency[size+1];
     if (size > 0) System.arraycopy(oldData, 0, precedingStep, 0, size);
     precedingStep[size] = (Dependency) input;
   }

   public void removePrecedingStep(Dependency input) {

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

     Dependency[] oldData = precedingStep;
     precedingStep = new Dependency[size-1];
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

     this.succeedingStep = new Dependency[ succeedingStep.size() ];
     Iterator it = succeedingStep.iterator();
     for (int i = 0; i < succeedingStep.size(); i++)
       this.succeedingStep[i] = (Dependency) it.next();
   }

   public void addSucceedingStep(Dependency input) {

     int size = (succeedingStep == null) ? 0 : succeedingStep.length;
     Dependency[] oldData = succeedingStep;
     succeedingStep = new Dependency[size+1];
     if (size > 0) System.arraycopy(oldData, 0, succeedingStep, 0, size);
     succeedingStep[size] = (Dependency) input;
   }

   public void removeSucceedingStep(Dependency input) {

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

     Dependency[] oldData = succeedingStep;
     succeedingStep = new Dependency[size-1];
     for (int i = 0; i < ipos; i++)
       succeedingStep[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       succeedingStep[i-1] = oldData[i];
   }

   public TransformationTask getTask() {
     return task;
   }

   public void setTask(TransformationTask task) {
     this.task = (TransformationTask) task;
   }
}
