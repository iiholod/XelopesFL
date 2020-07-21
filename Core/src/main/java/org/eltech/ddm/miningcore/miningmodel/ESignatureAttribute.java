package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.miningdata.AttributeDataType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.UsageOption;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.SignatureAttribute;

/**
 * CWM Class
 *
 * A SignatureAttribute object describes the input expected to a model. This is
 * automatically produced as part of the model. It indicates not only the basic Attribute
 * properties, but also how outliers and missing values were handled for model build. This
 * is potentially duplicate information from the MiningFunctionSettings, but must be
 * provided since MiningFunctionSettings are optional.
 * If an attribute was normalized or discretized automatically by the Data Mining System,
 * the specific details are provided in the SignatureAttribute object. The user is not
 * expected to use this information to preprocess the data in any way. The Data Mining
 * System uses this information to automatically preprocess data, if required.
 *
 *
 * @author Ivan Holod
 *
 */

public class ESignatureAttribute extends SignatureAttribute //implements javax.datamining.data.SignatureAttribute
{
	/**
	 * The usage intended for this attribute. A model signature consists only of �active� and
	 * �supplemental� attributes. �Inactive� attributes are filtered out as they do not contribute to the
	 * model. Note that �supplemental� attributes do not contribute to model apply.
	 */
	private UsageOption usageOption;


	// fields added for JDMAPI
	private AttributeDataType dataType;

	private double importanceValue;

	private int rank;


	ESignatureAttribute(){

	}

	/**
	 * Returns the type of the attribute.
	 * @return
	 */
	public AttributeDataType getDataType() {
		// TODO Auto-generated method stub
		return dataType;
	}

	/**
	 * Returns the importance of the attribute in the signature.
	 * If importance is not available, it returns 0.
	 * @return
	 */
	public double getImportanceValue() {
		// TODO Auto-generated method stub
		return importanceValue;
	}

	/**
	 * Returns the rank of the attribute in the signature with a value
	 * between 1 and n, where n is the number of attributes in the signature.
	 * If rank is not available, it returns 0.
	 * @return
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Returns the description of the attribute.
	 * This may be used for display name of the attribute.
	 * A display name is a user defined name of the attribute
	 * to be used for display purposes to make the name more meaningful.
	 */
	public String getDescription() {
		return displayName.getString();
	}

	public UsageOption getUsageOption(){
		return usageOption;
	}

	public void setUsageOption(UsageOption usage){
		usageOption = usage;
	}


}