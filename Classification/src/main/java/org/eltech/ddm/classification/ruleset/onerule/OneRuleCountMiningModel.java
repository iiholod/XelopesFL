package org.eltech.ddm.classification.ruleset.onerule;


import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeElement;
import org.eltech.ddm.miningcore.miningmodel.LogicalAttributeValueElement;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class OneRuleCountMiningModel extends OneRuleMiningModel {

	private final static int COUNT_MATRIX = 3;
	private final static int[] INDEX_COUNT_MATRIX = {COUNT_MATRIX};

    public OneRuleCountMiningModel(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);
		sets.add(COUNT_MATRIX, new MiningModelElement("Count matrix") {

			@Override
			protected String propertiesToString() {
				return "";
			}

			@Override
			public void merge(List<MiningModelElement> elements) throws MiningException {

			}
		});
	}

	@Override
	public void initModel() throws MiningException {
		super.initModel();

    	MiningModelElement attrs = getElement(INDEX_ATTRIBUTE_SET);
		LogicalAttributeElement tlattr = (LogicalAttributeElement)attrs.getElement(indexTarget);
    	for(int i = 0; i < attrs.size(); i++) { // loop for attributes
			LogicalAttributeElement lattr = (LogicalAttributeElement)attrs.getElement(i);
    		String attrName = lattr.getID();
			MiningModelElement attrElem = new MiningModelElement(attrName) {

				@Override
				protected String propertiesToString() {
					return "";
				}

				@Override
				public void merge(List<MiningModelElement> elements) throws MiningException {

				}
			};
			addElement(INDEX_COUNT_MATRIX, attrElem);

			for(int j = 0; j < lattr.size(); j++) { // loop for value of independet attribute
				LogicalAttributeValueElement lattrv = (LogicalAttributeValueElement)lattr.getElement(j);
				String catName = lattrv.getValue().getName();
				VectorsCount4AttributeValue vc4av = new VectorsCount4AttributeValue(attrName + "=" + catName);
				addElement(index(COUNT_MATRIX, i), vc4av);

				for(int t = 0; t < tlattr.size(); t++) { // loop for value of target attribute
					LogicalAttributeValueElement tlattrv = (LogicalAttributeValueElement)tlattr.getElement(t);
					String catTrName = tlattrv.getValue().getName();
					VectorsCount4TargetValue vc4tv = new VectorsCount4TargetValue(attrName + "=" + catName + ";" + catTrName);

					addElement(index(COUNT_MATRIX, i, j), vc4tv);
				}
			}
		}

	}


	public VectorsCount4AttributeValue getCountMatrix(int indexAttr, int indexValue) throws MiningException {
    	return  (VectorsCount4AttributeValue)getElement(EMiningModel.index(OneRuleCountMiningModel.COUNT_MATRIX, indexAttr,  indexValue));
	}

	public VectorsCount4TargetValue getCountMatrix(int indexAttr, int indexValue, int indexTargetValue) throws MiningException {
		return  (VectorsCount4TargetValue)getElement(EMiningModel.index(OneRuleCountMiningModel.COUNT_MATRIX, indexAttr,  indexValue, indexTargetValue));
	}
}

