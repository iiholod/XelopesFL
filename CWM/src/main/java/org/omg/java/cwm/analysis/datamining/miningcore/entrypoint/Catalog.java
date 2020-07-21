package org.omg.java.cwm.analysis.datamining.miningcore.entrypoint;

import org.omg.java.cwm.objectmodel.core.Package;

import java.util.ArrayList;

/**
 * CWM Class
 * 
 * This object is the top level entry point for the CWM Data Mining package. It contains
 * a set of schema.
 * 
 * @author Ivan Holod
 *
 */
public class Catalog extends Package {
	
	/**
	 * This represents a set of schema contained in the catalog.
	 */
	private ArrayList<Schema> schema;
}
