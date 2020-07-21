package org.omg.java.cwm.analysis.datamining.miningcore.entrypoint;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryMatrix;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryTaxonomy;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.LogicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningresult.MiningResult;
import org.omg.java.cwm.analysis.datamining.miningcore.miningtask.MiningTask;

import java.util.Set;

/**
 * CWM class
 * 
 * The Schema is a container for all data mining top-level objects.
 * 
 * @author Ivan Holod
 *
 */
public class Schema {

	/**
	 * This represents a set of LogicalData objects contained in the schema.
	 */
	private Set<LogicalData> logicalData;
	
	/**
	 * This represents a set of CategoryMatrix objects contained in the schema.
	 */
	private Set<CategoryMatrix> categoryMatrix;
	
	/**
	 * This represents a set of AuxiliaryObject objects contained in the schema.
	 */
	private  Set<AuxiliaryObject> auxObject;
	
	/**
	 * This represents a set of CategoryTaxonomy objects contained in the schema.
	 */
	private  Set<CategoryTaxonomy> taxonomy;
	
	/**
	 * This represents a set of MiningFunctionSettings objects contained in the schema.
	 */
	private Set<MiningFunctionSettings> miningFunctionSettings;
	
	/**
	 * This represents a set of MiningModel objects contained in the schema.
	 */
	private Set<MiningModel> miningModel;
	
	/**
	 * This represents a set of MiningTask objects contained in the schema.
	 */
	private Set<MiningTask> task;
	
	/**
	 * This represents a set of MiningResult objects contained in the schema.
	 */
	private Set<MiningResult> result;
}
