package org.eltech.ddm.classification.ruleset;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

public class RuleSetModel extends ClassificationMiningModel {

	private static final int[] RULE_SET = {1};

	// ======= current state of model (build model task) ==================
	public RuleSetModel(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);

		sets.add(RULE_SET[0], new RuleSet("Rules")) ;
	}

	@Override
	public void initModel() throws MiningException {

	}

	@Override
	public double apply(MiningVector miningVector) throws MiningException {
		double result = Double.NaN;
		double minError = Double.MAX_VALUE;
		MiningModelElement ruleSet = getElement(RULE_SET);
		for(int i = 0; i < ruleSet.size(); i++){
			MiningModelElement rule = ruleSet.getElement(i);
			if (((SimpleRule) rule).getPredicate().evaluate(miningVector)){
				if(minError > ((SimpleRule) rule).getError()){
					minError = ((SimpleRule) rule).getError();
					result = target.getCategoricalProperties().getIndex(((SimpleRule) rule).getScore());
				}
			}
		}

		return result;
	}

	public Object clone() {
		RuleSetModel o = null;
		o = (RuleSetModel)super.clone();

		return o;
	}


    public RuleSet getRuleSet() throws MiningException {
		return (RuleSet)getElement(RuleSetModel.RULE_SET);
    }

	public void setRuleSet(RuleSet rules) throws MiningException {
		setElement(RuleSetModel.RULE_SET, rules);
	}
}
