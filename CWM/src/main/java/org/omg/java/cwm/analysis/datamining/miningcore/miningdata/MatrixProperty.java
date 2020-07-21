package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;


/**
 * CWM Enumeration
 * 
 * This specifies the type of matrix: Diagonal, symmetric, or any. If diagonal, then all
 * values outside the diagonal are 0. If symmetric, then value(i,j)=value(j,i).
 * 
 * @author Ivan Holod
 *
 */
public enum MatrixProperty {
	symmetric,
	diagonal,
	any
}
