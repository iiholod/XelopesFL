package org.eltech.ddm.classification.decisiontree;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;

public class DecisionTreeMiningModel extends ClassificationMiningModel{

	public String TreeModel;

	// public MissingValueStrategy;
	//
	// public MissingValuePenalty;

	public MiningTreeNode Node;

	// public splitCharacteristic;
	//
	// public noTrueChildStrategy;

	public DecisionTreeMiningModel(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
		// TODO Auto-generated constructor stub
	}

	public void createNode() {
	}

	@Override
	public void initModel() throws MiningException {
		// TODO Auto-generated method stub

	}

	@Override
	public double apply(MiningVector miningVector) throws MiningException {
		// TODO Auto-generated method stub
		return 0;
	}

}