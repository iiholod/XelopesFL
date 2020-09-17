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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
//import com.prudsys.pdm.Cwm.SoftwareDeployment.Component;

/**
 * This represents a set of Transformations that must be executed together as a
 * single task (logical unit).
 *
 *
 *
 * A TransformationTask may have an inverse task. A transformation task that maps
 * a source set "A" into a target set "B" can be reversed by the inverse
 * transformation task that maps "B" into "A".
 */
public class TransformationTask //extends Component
{
   public TransformationStep[] step;
   public TransformationTask[] originalTask;
   public TransformationTask[] inverseTask;
   public Transformation[] transformation;

   public TransformationTask()
   {

   }

   public Collection getInverseTask() {
	   if (inverseTask == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(inverseTask) );
   }

   public void setInverseTask(Collection inverseTask) {

     this.inverseTask = new TransformationTask[ inverseTask.size() ];
     Iterator it = inverseTask.iterator();
     for (int i = 0; i < inverseTask.size(); i++)
       this.inverseTask[i] = (TransformationTask) it.next();
   }

   public void addInverseTask(TransformationTask input) {

     int size = (inverseTask == null) ? 0 : inverseTask.length;
     TransformationTask[] oldData = inverseTask;
     inverseTask = new TransformationTask[size+1];
     if (size > 0) System.arraycopy(oldData, 0, inverseTask, 0, size);
     inverseTask[size] = input;
   }

   public void removeInverseTask(TransformationTask input) {

     int size = (inverseTask == null) ? 0 : inverseTask.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (inverseTask[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     TransformationTask[] oldData = inverseTask;
     inverseTask = new TransformationTask[size-1];
     for (int i = 0; i < ipos; i++)
       inverseTask[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       inverseTask[i-1] = oldData[i];
   }

   public Collection getOriginalTask() {
	   if (originalTask == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(originalTask) );
   }

   public void setOriginalTask(Collection originalTask) {

     this.originalTask = new TransformationTask[ originalTask.size() ];
     Iterator it = originalTask.iterator();
     for (int i = 0; i < originalTask.size(); i++)
       this.originalTask[i] = (TransformationTask) it.next();
   }

   public void addOriginalTask(TransformationTask input) {

     int size = (originalTask == null) ? 0 : originalTask.length;
     TransformationTask[] oldData = originalTask;
     originalTask = new TransformationTask[size+1];
     if (size > 0) System.arraycopy(oldData, 0, originalTask, 0, size);
     originalTask[size] = input;
   }

   public void removeOriginalTask(TransformationTask input) {

     int size = (originalTask == null) ? 0 : originalTask.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (originalTask[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     TransformationTask[] oldData = originalTask;
     originalTask = new TransformationTask[size-1];
     for (int i = 0; i < ipos; i++)
       originalTask[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       originalTask[i-1] = oldData[i];
   }

   public Collection getTransformation() {
	   if (transformation == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(transformation) );
   }

   public void setTransformation(Collection transformation) {

     this.transformation = new Transformation[ transformation.size() ];
     Iterator it = transformation.iterator();
     for (int i = 0; i < transformation.size(); i++)
       this.transformation[i] = (Transformation) it.next();
   }

   public void addTransformation(Transformation input) {

     int size = (transformation == null) ? 0 : transformation.length;
     Transformation[] oldData = transformation;
     transformation = new Transformation[size+1];
     if (size > 0) System.arraycopy(oldData, 0, transformation, 0, size);
     transformation[size] = input;
   }

   public void removeTransformation(Transformation input) {

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

     Transformation[] oldData = transformation;
     transformation = new Transformation[size-1];
     for (int i = 0; i < ipos; i++)
       transformation[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       transformation[i-1] = oldData[i];
   }
}
