package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;

/**
 *
 * @author push_king
 */
public abstract class MiningAlgorithm {

	/**
	 * This specifies the logical data specification and specific parameters for
	 * the mining task.
	 */
	protected EMiningFunctionSettings miningSettings;


	public MiningAlgorithm(EMiningFunctionSettings miningSettings) throws MiningException {
		//this();
		this.miningSettings = miningSettings;
	}

	public abstract EMiningModel createModel() throws MiningException;

	public EMiningModel initModel() throws MiningException{
		EMiningModel resultModel = createModel();
		//resultModel.setNumberVectors(inputStream.getVectorsNumber());
		resultModel.initModel();

		return resultModel;
	}

	public abstract MiningSequence getSequenceAlgorithm() throws MiningException;
	public abstract MiningSequence getCentralizedParallelAlgorithm() throws MiningException;
	public abstract MiningSequence getHorDistributedAlgorithm() throws MiningException;
	public abstract MiningSequence getVerDistributedAlgorithm() throws MiningException;


	public MiningFunctionSettings getMiningSettings() {
		return miningSettings;
	}


}