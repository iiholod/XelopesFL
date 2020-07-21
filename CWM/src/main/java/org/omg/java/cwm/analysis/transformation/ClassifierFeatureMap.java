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

import org.omg.java.cwm.objectmodel.core.Classifier;
import org.omg.java.cwm.objectmodel.core.Feature;
import org.omg.java.cwm.objectmodel.core.ModelElement;
import org.omg.java.cwm.objectmodel.core.ProcedureExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * This represents a mapping of Classifiers to Features.
 */
public class ClassifierFeatureMap extends ModelElement
{

   /**
    * Any code or script for the FeatureMap.
    */
   public ProcedureExpression function;

   /**
    * A short description for any code or script performed by the FeatureMap.
    */
   public org.omg.java.cwm.objectmodel.core.String functionDescription;

   /**
    * Identifies if the mapping is from Classifiers (source) to Features (target).
    * The default is true.
    */
   public org.omg.java.cwm.objectmodel.core.Boolean classifierToFeature;
   public ClassifierMap classifierMap;
   public Classifier classifier[];
   public Feature feature[];

   public ClassifierFeatureMap()
   {

   }

   public ProcedureExpression getFunction() {
     return function;
   }

   public void setFunction(ProcedureExpression function) {
     this.function = (ProcedureExpression) function;
   }

   public java.lang.String getFunctionDescription() {
     return functionDescription.getString();
   }

   public void setFunctionDescription(java.lang.String functionDescription) {
	   org.omg.java.cwm.objectmodel.core.String s = new org.omg.java.cwm.objectmodel.core.String();
	   s.setString(functionDescription);
	   this.functionDescription = s;
   }

   public java.lang.Boolean getClassifierToFeature() {
     return classifierToFeature.getBoolean();
   }

   public void setClassifierToFeature(java.lang.Boolean classifierToFeature) {
	   org.omg.java.cwm.objectmodel.core.Boolean b = new org.omg.java.cwm.objectmodel.core.Boolean();
     b.setBoolean(classifierToFeature);
     this.classifierToFeature = b;
   }

   public boolean isClassifierToFeatureValue() {
     return classifierToFeature.isBooleanValue();
   }

   public void setClassifierToFeature(boolean classifierToFeature) {
     setClassifierToFeature( new java.lang.Boolean(classifierToFeature) );
   }

   public ClassifierMap getClassifierMap() {
     return classifierMap;
   }

   public void setClassifierMap(ClassifierMap classifierMap) {
     this.classifierMap = (ClassifierMap) classifierMap;
   }

   public Collection getClassifier() {
	   if (classifier == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(classifier) );
   }

   public void setClassifier(Collection classifier) {

     this.classifier = new Classifier[ classifier.size() ];
     Iterator it = classifier.iterator();
     for (int i = 0; i < classifier.size(); i++)
       this.classifier[i] = (Classifier) it.next();
   }

   public void addClassifier(Classifier input) {

     int size = (classifier == null) ? 0 : classifier.length;
     Classifier[] oldData = classifier;
     classifier = new Classifier[size+1];
     if (size > 0) System.arraycopy(oldData, 0, classifier, 0, size);
     classifier[size] = (Classifier) input;
   }

   public void removeClassifier(Classifier input) {

     int size = (classifier == null) ? 0 : classifier.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (classifier[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     Classifier[] oldData = classifier;
     classifier = new Classifier[size-1];
     for (int i = 0; i < ipos; i++)
       classifier[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       classifier[i-1] = oldData[i];
   }

   public Collection getFeature() {
	   if (feature == null)
		   return new ArrayList();

	   return new ArrayList( Arrays.asList(feature) );
   }

   public void setFeature(Collection feature) {

     this.feature = new Feature[ feature.size() ];
     Iterator it = feature.iterator();
     for (int i = 0; i < feature.size(); i++)
       this.feature[i] = (Feature) it.next();
   }

   public void addFeature( Feature input) {

     int size = (feature == null) ? 0 : feature.length;
     Feature[] oldData = feature;
     feature = new Feature[size+1];
     if (size > 0) System.arraycopy(oldData, 0, feature, 0, size);
     feature[size] = (Feature) input;
   }

   public void removeFeature( Feature input) {

     int size = (feature == null) ? 0 : feature.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (feature[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     Feature[] oldData = feature;
     feature = new Feature[size-1];
     for (int i = 0; i < ipos; i++)
       feature[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       feature[i-1] = oldData[i];
   }
}
