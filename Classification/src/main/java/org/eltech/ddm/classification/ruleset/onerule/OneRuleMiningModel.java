package org.eltech.ddm.classification.ruleset.onerule;


import org.eltech.ddm.classification.ruleset.RuleSet;
import org.eltech.ddm.classification.ruleset.RuleSetModel;
import org.eltech.ddm.classification.ruleset.SimpleRule;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;

public class OneRuleMiningModel extends RuleSetModel {

	private static final int[] INDEX_RULE_CANDIDATE_SET = {2};
	private static final String ID_RULE_CANDIDATE_SET = "Rule candidates";

	public OneRuleMiningModel(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
		sets.add(INDEX_RULE_CANDIDATE_SET[0], new RuleSet(ID_RULE_CANDIDATE_SET));
	}

	public void addCandidateRule(SimpleRule rule) throws MiningException {
		addElement(INDEX_RULE_CANDIDATE_SET, rule);
	}

	public RuleSet getCandidateRules() throws MiningException {
		return (RuleSet)getElement(INDEX_RULE_CANDIDATE_SET);
	}

	public void cleareCandidateRules() throws MiningException {
		setElement(INDEX_RULE_CANDIDATE_SET, new RuleSet(ID_RULE_CANDIDATE_SET));
	}

}
