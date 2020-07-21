package org.omg.java.cwm.analysis.datamining.miningcore.miningtask;

/**
 * CWM Enumeration
 * 
 * This specifies how the apply output is created. The default is createNew, which means the
 * output is created as a new file/table.
 * 
 * @author Ivan Holod
 *
 */
public enum ApplyOutputOption {
	appendToExisting,
	createNew
}
