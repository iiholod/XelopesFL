package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

/**
 * This indicates whether the values of the attributes are cyclic (i.e., the next value of the ending
 * value is the starting value). The default is false.
 */
public enum OrderType {
	asIs,
	alphabetic,
	numeric,
	date
}
