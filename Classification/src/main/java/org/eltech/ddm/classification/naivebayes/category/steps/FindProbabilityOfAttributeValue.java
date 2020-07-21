package org.eltech.ddm.classification.naivebayes.category.steps;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.naivebayes.category.NaiveBayesModel;
import org.eltech.ddm.classification.naivebayes.category.TargetValueCount;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FindProbabilityOfAttributeValue extends DataMiningBlock {
	private final ELogicalAttribute targetAttr;

	public FindProbabilityOfAttributeValue(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		// TODO Auto-generated constructor stub

		targetAttr = ((ClassificationFunctionSettings)settings).getTarget();
	}

	protected EMiningModel execute(MiningInputStream data, EMiningModel model) throws MiningException {
		MiningVector mv = data.getVector(model.getCurrentVectorIndex());

		if(mv != null){
			int iCurrAttr = model.getCurrentAttributeIndex();
			int indexValueTarg  = (int)mv.getValue(targetAttr.getName());
			int indexValueAttr = (int)mv.getValue(iCurrAttr);

			TargetValueCount tvc = ((NaiveBayesModel)model).getInputTargetValueCount(iCurrAttr, indexValueAttr, indexValueTarg);
			tvc.incCount();

			System.out.println("Thread-" + Thread.currentThread().getName() + " id vectror=" + mv.getIndex() + " attr-" + iCurrAttr + " =" + tvc);
		}

		return model;
	}

}
