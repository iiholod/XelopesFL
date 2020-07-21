package org.eltech.ddm.classification.steps;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningDecision;
import org.eltech.ddm.miningcore.algorithms.MiningSequence;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeElement;

public class DecisionCurrentAttributIsNotTarget extends MiningDecision {
	private final ELogicalAttribute target;

	public DecisionCurrentAttributIsNotTarget(EMiningFunctionSettings settings,
											  MiningBlock... trueBlocks) throws MiningException {
		super(settings, trueBlocks);
		target = ((ClassificationFunctionSettings) settings).getTarget();
	}
	
	public DecisionCurrentAttributIsNotTarget(EMiningFunctionSettings settings, MiningSequence trueSteps, MiningBlock... falseBlocks) throws MiningException {
		super(settings, trueSteps, falseBlocks);
		target = ((ClassificationFunctionSettings) settings).getTarget();
		
	}

	@Override
	protected boolean condition(EMiningModel model) throws MiningException {
		LogicalAttributeElement attribute = (LogicalAttributeElement) model.getElement(EMiningModel.INDEX_CURRENT_ATTRIBUTE);

		return !attribute.getID().equals(target.getName());
	}

}
