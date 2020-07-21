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

/**
 * Boolean defines an enumeration that denotes a logical condition.
 *
 *
 *
 * The default for data type Boolean is false.
 */

public class Boolean
{
   private java.lang.Boolean bool;

   /**
    * @roseuid 3C5E465E0354
    */
   public Boolean()
   {

   }

   public java.lang.Boolean getBoolean() {
     return bool;
   }

   public void setBoolean(java.lang.Boolean bool) {
     this.bool = bool;
   }

   public boolean isBooleanValue() {
     return bool.booleanValue();
   }

   public void setBooleanValue(boolean bool) {
     this.bool = new java.lang.Boolean(bool);
   }
}
