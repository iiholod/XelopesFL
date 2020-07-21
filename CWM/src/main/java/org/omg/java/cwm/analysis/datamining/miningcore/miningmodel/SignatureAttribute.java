package org.omg.java.cwm.analysis.datamining.miningcore.miningmodel;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.MiningAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.UsageOption;

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

public class SignatureAttribute extends MiningAttribute{

	/**
	 * The usage intended for this attribute. A model signature consists only of active and
	 * supplemental attributes. Inactive attributes are filtered out as they do not contribute to the
	 * model. Note that supplemental attributes do not contribute to model apply.
	 */
  private UsageOption usageOption;


}