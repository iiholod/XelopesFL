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
import org.omg.java.cwm.objectmodel.core.Namespace;
import org.omg.java.cwm.objectmodel.core.ProcedureExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * This represents a mapping of source Classifiers to target Classifiers. A
 * ClassifierMap may consists of a group of ClassifierFeatureMaps and/or
 * FeatureMaps.
 */
public class ClassifierMap extends Namespace
{

   /**
    * Any code or script for the ClassifierMap.
    */
   public ProcedureExpression function;

   /**
    * A short description for any code or script performed by the ClassifierMap.
    */
   public org.omg.java.cwm.objectmodel.core.String functionDescription;
   public Namespace transformationMap;
   public FeatureMap[] featureMap;
   public ClassifierFeatureMap[] cfMap;
   public Classifier[] source;
   public Classifier[] target;

   public ClassifierMap()
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

   public Collection getCfMap() {
	   if (cfMap == null)
		      return new ArrayList();

	   return new ArrayList( Arrays.asList(cfMap) );
   }

   public void setCfMap(Collection cfMap) {

     this.cfMap = new ClassifierFeatureMap[ cfMap.size() ];
     Iterator it = cfMap.iterator();
     for (int i = 0; i < cfMap.size(); i++)
       this.cfMap[i] = (ClassifierFeatureMap) it.next();
   }

   public void addCfMap( ClassifierFeatureMap input) {

     int size = (cfMap == null) ? 0 : cfMap.length;
     ClassifierFeatureMap[] oldData = cfMap;
     cfMap = new ClassifierFeatureMap[size+1];
     if (size > 0) System.arraycopy(oldData, 0, cfMap, 0, size);
     cfMap[size] = input;
   }

   public void removeCfMap( ClassifierFeatureMap input) {

     int size = (cfMap == null) ? 0 : cfMap.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (cfMap[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     ClassifierFeatureMap[] oldData = cfMap;
     cfMap = new ClassifierFeatureMap[size-1];
     for (int i = 0; i < ipos; i++)
       cfMap[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       cfMap[i-1] = oldData[i];
   }

   public Collection getFeatureMap() {
	   if (featureMap == null)
		      return new ArrayList();

	   return new ArrayList( Arrays.asList(featureMap) );
   }

   public void setFeatureMap(Collection featureMap) {

     this.featureMap = new FeatureMap[ featureMap.size() ];
     Iterator it = featureMap.iterator();
     for (int i = 0; i < featureMap.size(); i++)
       this.featureMap[i] = (FeatureMap) it.next();
   }

   public void addFeatureMap( FeatureMap input) {

     int size = (featureMap == null) ? 0 : featureMap.length;
     FeatureMap[] oldData = featureMap;
     featureMap = new FeatureMap[size+1];
     if (size > 0) System.arraycopy(oldData, 0, featureMap, 0, size);
     featureMap[size] = input;
   }

   public void removeFeatureMap( FeatureMap input) {

     int size = (featureMap == null) ? 0 : featureMap.length;
     if (size == 0)
       return;

     int ipos = -1;
     for (int i = 0; i < size; i++)
       if (featureMap[i].equals(input)) {
         ipos = i;
         break;
       }
     if (ipos == -1)
       return;

     FeatureMap[] oldData = featureMap;
     featureMap = new FeatureMap[size-1];
     for (int i = 0; i < ipos; i++)
       featureMap[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       featureMap[i-1] = oldData[i];
   }

   public Collection getSource() {
	   if (source == null)
		      return new ArrayList();

	   return new ArrayList( Arrays.asList(source) );
   }

   public void setSource(Collection source) {

     this.source = new Classifier[ source.size() ];
     Iterator it = source.iterator();
     for (int i = 0; i < source.size(); i++)
       this.source[i] = (Classifier) it.next();
   }

   public void addSource( Classifier input) {

     int size = (source == null) ? 0 : source.length;
     Classifier[] oldData = source;
     source = new Classifier[size+1];
     if (size > 0) System.arraycopy(oldData, 0, source, 0, size);
     source[size] = input;
   }

   public void removeSource( Classifier input) {

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

     Classifier[] oldData = source;
     source = new Classifier[size-1];
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

     this.target = new Classifier[ target.size() ];
     Iterator it = target.iterator();
     for (int i = 0; i < target.size(); i++)
       this.target[i] = (Classifier) it.next();
   }

   public void addTarget( Classifier input) {

     int size = (target == null) ? 0 : target.length;
     Classifier[] oldData = target;
     target = new Classifier[size+1];
     if (size > 0) System.arraycopy(oldData, 0, target, 0, size);
     target[size] = input;
   }

   public void removeTarget( Classifier input) {

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

     Classifier[] oldData = target;
     target = new Classifier[size-1];
     for (int i = 0; i < ipos; i++)
       target[i] = oldData[i];
     for (int i = ipos+1; i < size; i++)
       target[i-1] = oldData[i];
   }

   public Namespace getTransformationMap() {
     return transformationMap;
   }

   public void setTransformationMap(Namespace transformationMap) {
     this.transformationMap = transformationMap;
   }
}
