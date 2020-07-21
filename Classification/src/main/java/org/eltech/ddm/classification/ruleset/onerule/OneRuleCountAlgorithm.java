package org.eltech.ddm.classification.ruleset.onerule;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.ruleset.onerule.steps.*;
import org.eltech.ddm.classification.steps.DecisionCurrentAttributIsNotTarget;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class OneRuleCountAlgorithm extends MiningAlgorithm {
	private int indexTargetAttr;
	private int[] indexTargetValues;

	public OneRuleCountAlgorithm(EMiningFunctionSettings miningSettings)
			throws MiningException {
		super(miningSettings);

		indexTargetAttr = miningSettings.getLogicalData().
				getAttributeIndex(((ClassificationFunctionSettings)miningSettings).getTarget());
		indexTargetValues = new int[]{EMiningModel.ATTRIBUTE_SET, indexTargetAttr};
	}

	@Override
	public EMiningModel createModel() throws MiningException {
		return new OneRuleCountMiningModel(miningSettings);
	}

	@Override
	public MiningSequence getSequenceAlgorithm() throws MiningException {

		MiningSequence blocks = new MiningSequence(miningSettings,
				new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
						//new MiningDecisionElement(miningSettings, ruleNoTarget,	EMiningModel.INDEX_CURRENT_ATTRIBUTE,
						new DecisionCurrentAttributIsNotTarget(miningSettings,
								new MiningLoopVectors(miningSettings,
										new IncrementCorrectVectorsCount(miningSettings)))),
				new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
						new DecisionCurrentAttributIsNotTarget(miningSettings,
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_CURRENT_ATTRIBUTE,
										new MiningLoopElement(miningSettings, indexTargetValues,
												new SelectMaxCorrectVectorsCount(miningSettings)),
										new AddBestOneRule(miningSettings),
										new SummCorrectVectorsCount(miningSettings)),
								new SelectBestRuleSet(miningSettings),
								new RemoveAttributeRuleSet(miningSettings))
				)
		);

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
				new MiningParallel( miningSettings, MemoryType.distributed,
				  		new MiningLoopVectors(miningSettings,
								new MiningParallel( miningSettings, MemoryType.shared,
										new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
												//new MiningDecisionElement(miningSettings, ruleNoTarget,	EMiningModel.INDEX_CURRENT_ATTRIBUTE,
												new DecisionCurrentAttributIsNotTarget(miningSettings,
														new IncrementCorrectVectorsCount(miningSettings)))))),
				new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
						new DecisionCurrentAttributIsNotTarget(miningSettings,
								new MiningParallel( miningSettings, MemoryType.shared,
										new MiningLoopElement(miningSettings, EMiningModel.INDEX_CURRENT_ATTRIBUTE,
												new MiningLoopElement(miningSettings, indexTargetValues,
														new SelectMaxCorrectVectorsCount(miningSettings)),
												new MiningParallel( miningSettings, MemoryType.shared,
														new AddBestOneRule(miningSettings),
														new SummCorrectVectorsCount(miningSettings)))),
								new MiningParallel( miningSettings, MemoryType.distributed,
										new SelectBestRuleSet(miningSettings),
										new RemoveAttributeRuleSet(miningSettings))
						)
				)
		);


		blocks.addListenerExecute(new BlockExecuteTimingListner());

		return blocks;
	}

	@Override
	public MiningSequence getVerDistributedAlgorithm() throws MiningException {
		MiningSequence blocks = new MiningSequence(miningSettings,
				new MiningParallel( miningSettings, MemoryType.distributed,
						new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
								//new MiningDecisionElement(miningSettings, ruleNoTarget,	EMiningModel.INDEX_CURRENT_ATTRIBUTE,
								new DecisionCurrentAttributIsNotTarget(miningSettings,
										new MiningParallel( miningSettings, MemoryType.shared,
												new MiningLoopVectors(miningSettings,
														new IncrementCorrectVectorsCount(miningSettings)))))),
				new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
						new DecisionCurrentAttributIsNotTarget(miningSettings,
								new MiningParallel( miningSettings, MemoryType.shared,
										new MiningLoopElement(miningSettings, EMiningModel.INDEX_CURRENT_ATTRIBUTE,
												new MiningLoopElement(miningSettings, indexTargetValues,
														new SelectMaxCorrectVectorsCount(miningSettings)),
												new MiningParallel( miningSettings, MemoryType.shared,
														new AddBestOneRule(miningSettings),
														new SummCorrectVectorsCount(miningSettings)))),
								new MiningParallel( miningSettings, MemoryType.distributed,
										new SelectBestRuleSet(miningSettings),
										new RemoveAttributeRuleSet(miningSettings))
						)
				)
		);



		blocks.addListenerExecute(new BlockExecuteTimingListner());

		return blocks;
	}

	public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
		EMiningModel resultModel = new OneRuleCountMiningModel(miningSettings);

		return resultModel;
	}

 }
