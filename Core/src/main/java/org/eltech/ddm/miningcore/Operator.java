/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eltech.ddm.miningcore;

public enum Operator {
    EQUAL, 			// Attribute equal to specified value.
    NOT_EQUAL,		// Attribute not equal to specified value.
    LESS_THAN,		// Attribute less than specified value.
    GREATER_THAN,	// Attribute greater than specified value.
    GREATER_OR_EQUAL, // Attribute greater or equal to specified value.
    LESS_OR_EQUAL,	// Attribute less or equal to specified value.
    IS_IN,
    IS_NOT_IN,
    IS_MISSING,		// Attribute value is missing value.
    IS_NOT_MISSING;	// Attribute value is not missing value.



}
