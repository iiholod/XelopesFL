package org.eltech.ddm.classification.naivebayes.category;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeElement;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeValueElement;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;


public class NaiveBayesModel extends ClassificationMiningModel implements Cloneable{

	/**
	 * for each attribute and each its value
	 * contains the counts associated with the values of the target attribute
	 */

	final static private int BAYES_INPUT = 1;

	/**
	 * contains the counts associated with the values of the target attribute
	 */

	final static private int BAYES_OUTPUT = 2;

//	protected TargetValueCounts bayesOutput;
//
//	protected HashMap<ELogicalAttribute, HashMap<ECategory,TargetValueCounts>> bayesInputs;


	public NaiveBayesModel(EMiningFunctionSettings settings) throws MiningException{
		super(settings);

		sets.add(BAYES_INPUT, new MiningModelElement("Input") {
			@Override
			protected String propertiesToString() {
				return "";
			}

			@Override
			public void merge(List<MiningModelElement> elements) {

			}
		});
		sets.add(BAYES_OUTPUT, new MiningModelElement("Output") {
			@Override
			protected String propertiesToString() {
				return "";
			}

			@Override
			public void merge(List<MiningModelElement> elements) {

			}
		});
	}

	@Override
	public void initModel() throws MiningException {

		MiningModelElement attrs = getElement(INDEX_ATTRIBUTE_SET);
		LogicalAttributeElement tlattr = (LogicalAttributeElement) attrs.getElement(indexTarget);
		for (int i = 0; i < attrs.size(); i++) { // loop for attributes
			LogicalAttributeElement lattr = (LogicalAttributeElement) attrs.getElement(i);
			String attrName = lattr.getID();
			MiningModelElement attrElem = new MiningModelElement(attrName) {
				@Override
				protected String propertiesToString() {
					return "";
				}

				@Override
				public void merge(List<MiningModelElement> elements) {
				}
			};
			addElement(index(BAYES_INPUT), attrElem);

			for (int j = 0; j < lattr.size(); j++) { // loop for value of independet attribute
				LogicalAttributeValueElement lattrv = (LogicalAttributeValueElement) lattr.getElement(j);
				String catName = lattrv.getValue().getName();
				MiningModelElement attrValElem = new MiningModelElement(catName) {
					@Override
					protected String propertiesToString() {
						return "";
					}

					@Override
					public void merge(List<MiningModelElement> elements) {
					}
				};

				addElement(index(BAYES_INPUT, i), attrValElem);

				for (int t = 0; t < tlattr.size(); t++) { // loop for value of target attribute
					LogicalAttributeValueElement tlattrv = (LogicalAttributeValueElement) tlattr.getElement(t);
					String catTrName = tlattrv.getValue().getName();
					TargetValueCount tvc = new TargetValueCount(attrName + "=" + catName + ";" + catTrName);
					addElement(index(BAYES_INPUT, i, j), tvc);
				}
			}
		}

		for (int t = 0; t < tlattr.size(); t++) { // loop for value of target attribute
			LogicalAttributeValueElement tlattrv = (LogicalAttributeValueElement) tlattr.getElement(t);
			String catTrName = tlattrv.getValue().getName();
			TargetValueCount tvc = new TargetValueCount(catTrName);
			addElement(index(BAYES_OUTPUT), tvc);
		}
	}

	@Override
	public double apply(MiningVector miningVector) {
		double[] probabilities = getProbabilities(miningVector);

		double maxP = 0;
		int maxIndex = 0;
		for(int i =0; i < probabilities.length; i++){
			if(probabilities[i]>maxP){
				maxP = probabilities[i];
				maxIndex = i;
			}
		}

		return maxIndex;
	}

	public double[] getProbabilities(MiningVector miningVector) {
		//int numberAttr = miningVector.getValues().length;
		int numberTargetValues = target.getCategoricalProperties().getSize();

		// calculate probabilities for all target categories
//		double summP = 0;
//		int jCatT = 0;
//		for(IMiningElement count: (MiningModelSet)getSet(BAYES_OUTPUT)){
//			probabilities[jCatT] = 1;
//			int countVectorsTarget = ((TargetValueCount)count).getCount();
//			for(int iAttr = 0; iAttr < getSet(ATTRIBUTE_SET).size(); iAttr++){
//				if(iAttr == indexTarget)
//					continue;
//				int iCat = (int)miningVector.getValue(iAttr);
//				ELogicalAttribute attr = miningVector.getLogicalData().getAttribute(iAttr);
//				//MiningModelIndex index = new MiningModelIndex(i, category, j);
//				//int countVectorsAttr =  ((TargetValueCount)index.getSet(this)).getCount();
//				int countVectorsAttr =  ((TargetValueCount)getSet(BAYES_INPUT).getSet(iAttr).getSet(iCat).getElement(jCatT)).getCount();
//				probabilities[jCatT] *= countVectorsAttr / countVectorsTarget;
//			}
//			summP += probabilities[jCatT];
//			jCatT++;
//		}
//
//		// normalisation
//		for(int i = 0; i < numberTargetValues; i++)
//			probabilities[i] /= summP;

		return new double[numberTargetValues];
	}

	public TargetValueCount getInputTargetValueCount(int iCurrAttr, int indexValueAttr, int indexValueTarg) throws MiningException {
		return (TargetValueCount) getElement(
				EMiningModel.index(NaiveBayesModel.BAYES_INPUT, iCurrAttr, indexValueAttr, indexValueTarg));
	}

	public TargetValueCount getOutputTargetValueCount(int indexValueTarg) throws MiningException {
		return (TargetValueCount)getElement(EMiningModel.index(NaiveBayesModel.BAYES_OUTPUT, indexValueTarg));
	}

	public MiningModelElement getOutput() throws MiningException {
		return getElement(EMiningModel.index(NaiveBayesModel.BAYES_OUTPUT));
	}
}