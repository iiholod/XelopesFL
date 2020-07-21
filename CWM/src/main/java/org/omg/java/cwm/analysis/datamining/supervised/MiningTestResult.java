package org.omg.java.cwm.analysis.datamining.supervised;

import org.omg.java.cwm.analysis.datamining.miningcore.miningresult.MiningResult;

/**
 * CWM Class
 * 
 * This represents the result of a test task applied to a supervised model.
 * 
 * @author Ivan Holod
 *
 */
public class MiningTestResult extends MiningResult {

	/**
	 * This represents the number of records applied to the test task.
	 */
	private int numberOfTestRecords;
	
	
	/**
	 * This represents the lift result if the test task is specified to perform lift computation as part of
	 * the task.
	 */
	private LiftAnalysis liftAnalysis;
	
}
