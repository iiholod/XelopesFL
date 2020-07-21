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
 *
 * @author Valentine Stepanenko (valentine.stepanenko@zsoft.ru)
 * @version 1.0
 */

package org.omg.java.cwm.objectmodel.core;

import java.io.Serializable;

/**
 * String defines a piece of text. Strings do not normally have a defined length;
 * rather, they are considered to be arbitrarily long (practical limits on the
 * length of Strings exist, but are implementation dependent). When String is used
 * as the type of an Attribute, string length sometimes can be specified (see the
 * Relational and Record packages for examples).
 * <p/>
 * <p/>
 * <p/>
 * The default for the String data type is an empty string.
 */

public class String implements Serializable {
    private java.lang.String string = "";

    /**
     * @roseuid 3C5E465F00B6
     */
    public String() {
        string = "";
    }

    public String(java.lang.String value) {
        string = value;
    }

    public java.lang.String getString() {
        return string;
    }

    public void setString(java.lang.String string) {
        this.string = string;
    }
}
