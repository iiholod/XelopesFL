package org.eltech.ddm.miningcore.miningdata;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeUsage;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeUsageSet;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CWM Class
 *
 * An AttributeUsageSet object contains a collection of AttributeUsage objects. This
 * specifies how MiningAttributes are to be used or manipulated by a model. The
 * specification may contain at most one AttributeUsage object of each MiningAttribute
 * in the LogicalDataSpecification. The default usage is active for an attribute if no
 * entry for a MiningAttribute is present.
 *
 * @author Ivan Holod
 *
 */
public class EAttributeUsageSet extends AttributeUsageSet implements Cloneable, Serializable {


	public EAttributeUsageSet(){
		feature = new ArrayList<>();
	}

	public void addAttributeUsage(EAttributeUsage attributeUsage){
		if(attributeUsage != null)
			feature.add(attributeUsage);
	}

	public EAttributeUsage getAttributeUsage(String logicalAttrName){

		for (AttributeUsage attrUsage : feature) {
			if( ((EAttributeUsage)attrUsage).getLogicalAttribute(logicalAttrName)!= null)
				return (EAttributeUsage)attrUsage;
		}

		return null;
	}

	public EAttributeUsage getAttributeUsage(int i){

		if (i < feature.size())
			return (EAttributeUsage)feature.get(i);

		return null;
	}

	public int getAttributeUsagesNumber(){
		return feature.size();
	}

	public Object clone() {
		EAttributeUsageSet o = (EAttributeUsageSet) super.clone();

		if (feature != null) {
			ArrayList<AttributeUsage> f = new ArrayList<>();
			for (AttributeUsage a : feature)
				f.add((EAttributeUsage) a.clone());
			o.feature = f;
		}

		return o;
	}

}
