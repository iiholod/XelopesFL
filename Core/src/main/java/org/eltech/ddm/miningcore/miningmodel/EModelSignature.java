package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.ModelSignature;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.SignatureAttribute;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * The model signature is a description of the input that captures the signature of the
 * input mining data and can be used to apply a data mining model.
 *
 * @author Ivan Holod
 *
 */
public class EModelSignature extends ModelSignature
	//implements javax.datamining.data.ModelSignature
	{



	EModelSignature(){
		signature = new ArrayList<SignatureAttribute>();
	}

	/**
	 * Returns the SignatureAttribute instance with the specified name present in the model signature.
	 * @param attributeName - The name of the attribute whose signature is to be returned
	 * @return
	 * @throws MiningException
	 */
	public ESignatureAttribute getAttribute(String attributeName)
			throws MiningException {

		for (SignatureAttribute attr : signature) {
			if(attr.getName().equals(attributeName))
				return (ESignatureAttribute)attr;
		}

		return null;
	}

	/**
	 * Returns a collection of SignatureAttribute instances present in the model signature.
	 * @return
	 * @throws MiningException
	 */
	public int getAttributesNumber() throws MiningException {
		return signature.size();
	}

	/**
	 * Returns a collection of all attributes in the specified order.
	 * If ordering is descending, the most important attributes are returned first.
	 * If ascending, the least important attributes are returned first.
	 * If no ranking is available for the attributes, this method returns an empty collection and the values returned for getRank and getImportanceValue in SignatureAttribute are undefined.
	 * @param arg0
	 * @return
	 * @throws JDMException
	 */
//	public Collection getAttributesByRank(SortOrder arg0) throws JDMException {
//		// TODO Auto-generated method stub
//		return null;
//	}



}