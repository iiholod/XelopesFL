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

//import com.prudsys.pdm.Cwm.Expressions.ExpressionNode;

/**
 * This represents a specialized Transformation which can be modeled as an
 * expression tree.
 */
public class TransformationTree extends Transformation
{

   /**
    * Identifies the type of TransformationTree, which can be unary or binary.
    */
   public TreeType type;

   /**
    * Identifies the expression tree that embodies the TransformationTree.
    */
//   public ExpressionNode body;

   public TransformationTree()
   {

   }

//   public org.omg.cwm.foundation.expressions.ExpressionNode getBody() {
//     return body;
//   }
//
//   public void setBody(org.omg.cwm.foundation.expressions.ExpressionNode body) {
//     this.body = (ExpressionNode) body;
//   }

   public java.lang.String getType() {
     return type.getString();
   }

   public void setType(java.lang.String type) {

     TreeType tt = new TreeType();
     tt.setString(type);
     this.type = tt;
   }
}
