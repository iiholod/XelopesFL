package org.omg.java.cwm.analysis.datamining.supervised;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.analysis.datamining.miningcore.miningtask.MiningTask;

/**
 * CWM Class
 *
 * This represents a mining task that is used to check some aspect of the quality of a
 * classification or approximation model.
 *
 * @author Ivan Holod
 *
 */
public class MiningTestTask extends MiningTask {

	/**
	 * This indicates to perform lift computation as part of test task, if true. The default is false.
	 */
	protected boolean computeLift;

	/**
	 * Positive category for which the lift computation is performed. Not applicable for
	 * approximation model lift computation.
	 */
	protected Category positiveTargetCategory;
}
