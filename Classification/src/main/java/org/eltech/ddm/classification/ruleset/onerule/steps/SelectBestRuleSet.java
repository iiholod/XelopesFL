package org.eltech.ddm.classification.ruleset.onerule.steps;

import org.eltech.ddm.classification.ruleset.RuleSet;
import org.eltech.ddm.classification.ruleset.RuleSetModel;
import org.eltech.ddm.classification.ruleset.onerule.OneRuleMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class SelectBestRuleSet extends MiningBlock {

	public SelectBestRuleSet(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
	}

	protected EMiningModel execute(EMiningModel model) throws MiningException {
		RuleSet ruleSet = ((RuleSetModel)model).getRuleSet();
		RuleSet ruleCandSet = ((OneRuleMiningModel)model).getCandidateRules();

		if (ruleSet.getNumberCorrectVectors() < ruleCandSet.getNumberCorrectVectors()) {
			((RuleSetModel)model).setRuleSet(ruleCandSet);
			//System.out.println(Thread.currentThread() + ": Model:  " + ((RuleSetModel)model).getRuleSet());
		}

		return   model;
	}
	


}
