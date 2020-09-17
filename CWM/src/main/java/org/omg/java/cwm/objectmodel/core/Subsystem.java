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

package org.omg.java.cwm.objectmodel.core;

import java.util.Collection;

/**
 * A subsystem is a grouping of model elements that represents a behavioral unit
 * in a physical system. A subsystem offers interfaces and has operations.
 * In the metamodel, Subsystem is a subclass of both Package and Classifier. As
 * such it may have a set of Features.
 * The purpose of the subsystem construct is to provide a grouping mechanism for
 * specifying a behavioral unit of a physical system. Apart from defining a
 * namespace for its contents, a subsystem serves as a specification unit for the
 * behavior of its contained model elements.
 * The contents of a subsystem is defined in the same way as for a package, thus it
 * consists of owned elements and imported elements, with unique names within the
 * subsystem.
 */
public class Subsystem extends Classifier
{

   /**
    * @roseuid 3C5E9F17032B
    */
   public Subsystem()
   {

   }

   // -----------------------------------------------------------------------
   //  Methods of Package class not really implemented!!!!
   // -----------------------------------------------------------------------
   
   public void setImportedElement( Collection input) {   
   }

    public Collection getImportedElement() {
	   return null;
   }

    public void addImportedElement(ModelElement input) {
   }

    public void removeImportedElement(ModelElement input) {
   }

}
