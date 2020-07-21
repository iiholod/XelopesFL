/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eltech.ddm.classification.naivebayes.category;

import org.eltech.ddm.classification.naivebayes.category.steps.FindProbabilityOfAttributeValue;
import org.eltech.ddm.classification.naivebayes.category.steps.FindProbabilityOfTargetValue;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


/**
 *
 * @author Ivan Kholod
 */
public class NaiveBayesAlgorithm extends MiningAlgorithm {


	public NaiveBayesAlgorithm(EMiningFunctionSettings miningSettings)
			throws MiningException {
		super(miningSettings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EMiningModel createModel() throws MiningException {
		return new NaiveBayesModel(miningSettings);
	}

	@Override
	public MiningSequence getSequenceAlgorithm() throws MiningException {
		MiningSequence blocks = new MiningSequence(miningSettings,
				new MiningLoopVectors(miningSettings,
						new FindProbabilityOfAttributeValue(miningSettings),
						new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
								new FindProbabilityOfAttributeValue(miningSettings))));

		blocks.addListenerExecute(new BlockExecuteTimingListner());

		return blocks;
	}

	@Override
	public MiningSequence getCentralizedParallelAlgorithm() throws MiningException {
		return getHorDistributedAlgorithm();
	}

	@Override
	public MiningSequence getHorDistributedAlgorithm() throws MiningException {
		MiningSequence blocks = new MiningSequence(miningSettings,
				new MiningParallel( miningSettings, MemoryType.shared,
						new MiningLoopVectors(miningSettings,
								new FindProbabilityOfTargetValue(miningSettings),
								new MiningParallel( miningSettings, MemoryType.shared,
										new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
												new FindProbabilityOfAttributeValue(miningSettings))))));

		blocks.addListenerExecute(new BlockExecuteTimingListner());
		return blocks;
	}

	@Override
	public MiningSequence getVerDistributedAlgorithm() throws MiningException {
		MiningSequence blocks = new MiningSequence(miningSettings,
				new MiningParallel( miningSettings, MemoryType.distributed,
						new MiningParallel( miningSettings, MemoryType.distributed,
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
										new MiningParallel( miningSettings, MemoryType.shared,
												new MiningLoopVectors(miningSettings,
														new FindProbabilityOfAttributeValue(miningSettings))))),
						new MiningParallel( miningSettings, MemoryType.shared,
								new MiningLoopVectors(miningSettings,
										new FindProbabilityOfTargetValue(miningSettings)))));

		blocks.addListenerExecute(new BlockExecuteTimingListner());

		return blocks;
	}


	public EMiningModel createModel(MiningInputStream inputStream)
			throws MiningException {
		EMiningModel resultModel = new NaiveBayesModel(miningSettings);

		return resultModel;
	}

}
