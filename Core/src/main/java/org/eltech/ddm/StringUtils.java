/*
XELOPES Java Version 3.2
Copyright (C) 2008  prudsys AG

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
version 2 as published by the Free Software Foundation.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

/**
  * Title: XELOPES Data Mining Library
  * Description: The XELOPES library is an open platform-independent and data-source-independent library for Embedded Data Mining.
  * Copyright: Copyright (c) 2002-2008 prudsys AG. All Rights Reserved.
  * License: Use is subject to XELOPES license terms.
  * @author Toni Volkmer
  * @version 1.0
  */

package org.eltech.ddm;

/**
 * Helper class for String handling.
 */
public class StringUtils
{
    /**
     * Quote string.
     *
     * @param string string to be quoted
     * @return quoted string
     */
    public static String quote( String string )
    {
        boolean quote = false;
        // backquote the following characters
        if((string.indexOf('\n') != -1) || (string.indexOf('\r') != -1) ||
          (string.indexOf('\'') != -1) || (string.indexOf('"') != -1) ||
          (string.indexOf('\\') != -1) ||
          (string.indexOf('\t') != -1) || (string.indexOf('%') != -1))
        {
            string = backQuoteChars(string);
            quote = true;
        }
        // Enclose the string in 's if the string contains a recently added
        // backquote or contains one of the following characters.
        if((quote == true) ||
         (string.indexOf('{') != -1) || (string.indexOf('}') != -1) ||
         (string.indexOf(',') != -1) || (string.equals("?")) ||
         (string.indexOf(' ') != -1) || (string.equals("")))
        {
            string = ("'".concat(string)).concat("'");
        }
        return string;
    }

    /**
     * Backquote characters ('\\',   '\'',  '\t',  '"',    '%')
     * in string.
     *
     * @param string string containing characters to be backquoted
     * @return new string with characters backquoted
     */
    public static String backQuoteChars( String string )
    {
        int index;
        StringBuffer newStringBuffer;
        // replace each of the following characters with the backquoted version
        char   charsFind[] =    {'\\',   '\'',  '\t',  '"',    '%'};
        String charsReplace[] = {"\\\\", "\\'", "\\t", "\\\"", "\\%"};
        for(int i = 0; i < charsFind.length; i++)
        {
            if (string.indexOf(charsFind[i]) != -1 )
            {
                newStringBuffer = new StringBuffer();
                while((index = string.indexOf(charsFind[i])) != -1)
                {
                    if (index > 0)
                    {
                        newStringBuffer.append(string.substring(0, index));
                    }
                    newStringBuffer.append(charsReplace[i]);
                    if((index + 1) < string.length())
                    {
                        string = string.substring(index + 1);
                    }
                    else
                    {
                        string = "";
                    }
                }
                newStringBuffer.append(string);
                string = newStringBuffer.toString();
            }
        }
        return convertNewLines(string);
    }

    /**
     * Convert new lines.
     *
     * @param string string to be processed
     * @return resulting string
     */
    public static String convertNewLines( String string )
    {
        int index;
        // Replace with \n
        StringBuffer newStringBuffer = new StringBuffer();
        while((index = string.indexOf('\n')) != -1)
        {
            if(index > 0)
            {
                newStringBuffer.append(string.substring(0, index));
            }
            newStringBuffer.append('\\');
            newStringBuffer.append('n');
            if((index + 1) < string.length())
            {
                string = string.substring(index + 1);
            }
            else
            {
                string = "";
            }
        }
        newStringBuffer.append(string);
        string = newStringBuffer.toString();
        // Replace with \r
        newStringBuffer = new StringBuffer();
        while((index = string.indexOf('\r')) != -1)
        {
            if(index > 0)
            {
                newStringBuffer.append(string.substring(0, index));
            }
            newStringBuffer.append('\\');
            newStringBuffer.append('r');
            if((index + 1) < string.length())
            {
                string = string.substring(index + 1);
            }
            else
            {
                string = "";
            }
        }
        newStringBuffer.append(string);
        return newStringBuffer.toString();
    }

}