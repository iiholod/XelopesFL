/**
 *
 */
package org.eltech.ddm.miningcore.miningfunctionsettings;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.NamedObject;
import org.eltech.ddm.miningcore.VerificationReport;
import org.eltech.ddm.miningcore.miningdata.EAttributeUsage;
import org.eltech.ddm.miningcore.miningdata.EAttributeUsageSet;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.UsageOption;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;

import java.util.ArrayList;
import java.util.Collection;

/**
 * CWM Class
 *
 * A MiningFunctionSettings object captures the high level specification input for
 * building a data mining model. The intent of mining function settings is to allow a user
 * to specify the type of the desired result without having to specify a particular
 * algorithm.
 * Although mining function settings allow for the specification of algorithm, if this is
 * omitted, the underlying data mining system is responsible for selecting the algorithm
 * based on basic user-provided parameters.
 *
 * Subclasses throw exceptions if invalid algorithm-function pairs are supplied.
 *
 * @author Holod Ivan
 *
 */
public abstract class EMiningFunctionSettings extends MiningFunctionSettings implements Cloneable //BuildSettings
{
	// Features added for JDMAPI
	//private String logicalDataName;

	public EMiningFunctionSettings(ELogicalData ld) {
		logicalData = ld;
		attributeUsageSet = new EAttributeUsageSet();
	}

	public EMiningFunctionSettings(){

	}


	/**
	 * Returns the algorithm settings associated with the build settings.
	 */
	public EMiningAlgorithmSettings getAlgorithmSettings() {
		return (EMiningAlgorithmSettings)algorithmSettings;
	}

	/**
	 * Sets the algorithm settings to be associated with the build settings.
	 * A null value can be specified to indicate that no algorithm settings
	 * are specified and that the DME should select a suitable algorithm.
	 * If the DME does not support the specified algorithm for the function, an exception is raised.
	 * @param algorithmSettings
	 */
	public void setAlgorithmSettings(EMiningAlgorithmSettings algorithmSettings) {
			this.algorithmSettings = algorithmSettings;
	}


	/**
	 * Returns the desired execution time specified by the user, if any.
	 * The default is Java Integer.MAX_VALUE indicating execution time is not constrained.
	 * @return
	 */
	public int getDesiredExecutionTimeInMinutes() {
		return desiredExecutionTimeInMinutes;
	}

	/**
	 * Sets the desired execution time specified by the user, if any.
	 * This is a hint provided by the user to help constrain the build time.
	 * It may affect the quality of the model. It may be ignored by the DME.
	 * The default is Java Integer.MAX_VALUE indicating execution time is not constrained.
	 * @param minutes - The desired execution time in minutes.
	 */
	public void setDesiredExecutionTimeInMinutes(int minutes) {
		desiredExecutionTimeInMinutes = minutes;

	}


	/**
	 * Returns a collection of LogicalAttribute with the specified usage.
	 * The returned collection may be empty, not null.
	 * @param usage - The usage of the attributes to be returned.
	 * @return
	 * @throws MiningException
	 */
	public Collection<ELogicalAttribute> getLogicalAttributes(UsageOption usage)
			throws MiningException {
		ArrayList<ELogicalAttribute> collection = new ArrayList<ELogicalAttribute>();

		for(int i = 0; i < ((EAttributeUsageSet)attributeUsageSet).getAttributeUsagesNumber(); i++){
			EAttributeUsage attrUsage = ((EAttributeUsageSet)attributeUsageSet).getAttributeUsage(i);
			if(attrUsage.getUsage() == usage)
			{
				for(int j = 0; j < attrUsage.getAttributeNumber(); j++)
					collection.add(attrUsage.getLogicalAttribute(j));
			}
		} ;
		return collection;
	}


	/**
	 * Returns the usage of the specified logical attribute. If the specified
	 * logical attribute is not found in the logical data, an exception is thrown.
	 * @param logicalAttrName - The name of the logical attribute whose usage is to be returned.
	 * @return
	 * @throws MiningException
	 */
	public EAttributeUsage getAttributeUsage(String logicalAttrName) throws MiningException {
		EAttributeUsage attrUsage = ((EAttributeUsageSet)attributeUsageSet).getAttributeUsage(logicalAttrName);
		if(attrUsage == null)
			throw new MiningException(MiningErrorCode.INVALID_ARGUMENT,
					"Attribute " + logicalAttrName + "is not in attribute usage attributes.");

		return attrUsage;
	}

	public void addAttributeUsage(EAttributeUsage attributeUsage){
		((EAttributeUsageSet)attributeUsageSet).addAttributeUsage(attributeUsage);
	}

	/**
	 * Returns a reference to a LogicalData instance associated with the build settings.
	 * @return
	 */
	public ELogicalData getLogicalData() {
		return (ELogicalData)logicalData;
	}

	/**
	 * Sets a reference to a LogicalData instance associated with the build settings.
	 */
	public void getLogicalData(ELogicalData logicalData) {
		this.logicalData = logicalData;
	}

	/**
	 * Returns the type of mining function specified for the build settings.
	 * @return
	 */
	public abstract MiningFunction getMiningFunction();

	/**
	 * Verifies if the settings are valid to some degree of correctness as specified by the vendor.
	 * Returns null if the settings object is valid.
	 * If the settings object is invalid, it returns an instance of VerificationReport.
	 */
	public VerificationReport verify() {
		// TODO ����� ��������� ������ �� �������� �� UsageSet  � LogicalData
		return null;
	}

	/**
	 *
	 */
	//@Override
	public NamedObject getObjectType() {
		return NamedObject.buildSettings;
	}

	public Object clone() {
		EMiningFunctionSettings o = null;
	    o = (EMiningFunctionSettings)super.clone();

	    if(algorithmSettings != null)
	    	o.algorithmSettings = (EMiningAlgorithmSettings)algorithmSettings.clone();

	    if(attributeUsageSet != null)
	    	o.attributeUsageSet = (EAttributeUsageSet)attributeUsageSet.clone();

	    if(logicalData != null)
	    	o.logicalData = (ELogicalData)logicalData.clone();

	    return o;
	}

	// ---- not implements for JDMAPI -----------------

	/*
	 * Sets the usage of the specified logical attribute.
	 * @param logicalAttrName - The name of the logical attribute whose usage is to be attributes.
	 * @param usage - The usage to be assigned to the specified logical attribute.
	 * @throws MiningException
	 * Method JDMAPI. Use instead its method addAttributeUsage(AttributeUsage)
	 */
	//	public void setUsage(String logicalAttrName, UsageOption usage)	throws MiningException {
	//		if(logicalData == null)
	//			throw new MiningException(MiningErrorCode.INVALID_ARGUMENT ,
	//						"Logical Data is null.");
	//
	//		LogicalAttribute logicalAttribute = (LogicalAttribute) logicalData.getAttribute(logicalAttrName);
	//
	//		AttributeUsage attributeUsage = attributeUsageSet.getAttributeUsage(logicalAttrName);
	//		if(attributeUsage == null){
	//			attributeUsage = new  AttributeUsage(logicalAttribute);
	//			attributeUsage.setUsage(usage);
	//		}
	//		else if (attributeUsage.getUsage() != usage){
	//
	//		}
	//
	//
	//		attributeUsage.addLogicalAttribute(logicalAttribute);
	//	};

	/* (non-Javadoc)
	 * @see javax.datamining.base.BuildSettings#getOutlierTreatment(java.lang.String)
	 * Method JDMAPI. Use instead its method getAttributeUsage(String)
	 */
	//public OutlierTreatment getOutlierTreatment(String arg0)throws JDMException ;

	/* (non-Javadoc)
	 * @see javax.datamining.base.BuildSettings#setOutlierTreatment(java.lang.String, javax.datamining.OutlierTreatment)
	 * Method JDMAPI. Use instead its method addAttributeUsage(AttributeUsage)
	 */
	//public void setOutlierTreatment(String arg0, OutlierTreatment arg1)	throws JDMException;
	/*
	 * Returns the weight of the specified logical attribute by name.
	 * The default weight is 1 for all attributes.
	 * If the attribute name is not found, an exception is thrown.
	 * @param logicalAttrName - The name of the logical attribute whose weight is to be returned.
	 * @return
	 * @throws JDMException
	 * Method JDMAPI. Use instead its method getAttributeUsage(String)
	 */
	//public double getWeight(String arg0) throws JDMException ;

	/* (non-Javadoc)
	 * @see javax.datamining.base.BuildSettings#setWeight(java.lang.String, double)
	 * Method JDMAPI. Use instead its method addAttributeUsage(AttributeUsage)
	 */
	//public void setWeight(String arg0, double arg1) throws MininigException;
	/*
	 * Sets the name of the attribute that contains case (record) weights.
	 * This method does not verify if the name exists in the logical data.
	 * This is verified using the verify method.
	 * Throws an exception if the attribute name is currently attributes as the target in supervised settings.
	 * @return
	 * Method JDMAPI. Use instead its method getLogicalAttributes(UsageOption usage)
	 */
	//public String getWeightAttribute();

	/*
	 * Sets the name of the attribute that contains case (record) weights.
	 * This method does verify if the name exists in the logical data.
	 * Throws an exception if the attribute name is currently attributes as the target in supervised settings or
	 * if logical data has not attribute with this name.
	 * NOTE! Implementation of the method is differented from JDMAPI.
	 * @param logicalAttrName -
	 * @throws MininigException
	 * Method JDMAPI. Use instead its method setUsage(LogicalAttribute, UsageOption)
	 */
	//	public void setWeightAttribute(String logicalAttrName) throws MiningException {
	//		UsageOption usage = attributeUsageSet.getUsage(logicalAttrName);
	//		if(usage == UsageOption.target)
	//			throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "Attribute " + logicalAttrName + " is target.");
	//
	//		if (logicalData == null)
	//
	//
	//		AttributeUsage attrUsage = attributeUsageSet.getAttributeUsage(UsageOption.weightActive);
	//		attrUsage.
	//	}

	/* (non-Javadoc)
	 * @see javax.datamining.base.BuildSettings#getOutlierIdentification(java.lang.String)
	 *
	 *  Method JDMAPI. Use instead its class NumericalAttributeProperties
	 */
	//public Interval getOutlierIdentification(String arg0) throws JDMException;

	/* (non-Javadoc)
	 * @see javax.datamining.base.BuildSettings#setOutlierIdentification(java.lang.String, javax.datamining.data.Interval)
	 *  Method JDMAPI. Use instead its class NumericalAttributeProperties
	 */
	//public void setOutlierIdentification(String arg0, Interval arg1);

	/*
	 * Returns an array of the attributes with the specified specification type.
	 * Returns null if there is no such attribute in the settings.
	 * @param retrievalType - The retrieval type for the attributes to be returned.
	 * @return
	 *
	 *  Method JDMAPI. Use instead its method getLogicalAttributes(UsageOption usage)
	 */
	//public String[] getAttributeNames(AttributeRetrievalType retrievalType);


	/*
 	 * Sets the logical data to reference a named LogicalData object.
	 * If this method is used, then the constraints on the weight, usage, outlierTreatment
	 * methods may not identify any constraint violations.
	 * @param name -
	 * @throws JDMException
	 *  Method JDMAPI. Use instead its method setLogicalData(LogicalData)
	 *
	 */
	//public void setLogicalDataName(String name) throws MiningException;

	/*
	 * Returns the name of the LogicalData object if attributes via setLogicalDataName(String) method.
	 * Returns null if the build settings has been obtained from a model.
	 * @return
	 *  Method JDMAPI. Use instead its method LogicalData getLogicalData()
	 */
	// public String getLogicalDataName();


}
