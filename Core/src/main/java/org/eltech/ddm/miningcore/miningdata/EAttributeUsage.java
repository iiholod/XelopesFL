package org.eltech.ddm.miningcore.miningdata;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeUsage;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.LogicalAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.UsageOption;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * An AttributeUsage object specifies how a MiningAttribute is to be used for mining
 * operations.
 *
 * @author Ivan Holod
 *
 */
public class EAttributeUsage extends AttributeUsage implements Cloneable {



	// Added
	/**
	 * Outlier treatment for the specified logical attribute. Default is systemDefault
	 */
	private OutlierTreatment outlierTreatment = OutlierTreatment.systemDefault;

	public EAttributeUsage(ELogicalAttribute attr) {
		attribute = new ArrayList<LogicalAttribute>();
		attribute.add(attr);
	}

	public void addLogicalAttribute(ELogicalAttribute logicalAttribute) {
		if (logicalAttribute != null)
			attribute.add(logicalAttribute);
	}

	public ELogicalAttribute getLogicalAttribute(String logicalAttrName) {
		for (LogicalAttribute attr : attribute) {
			if(attr.getName().equals(logicalAttrName))
				return (ELogicalAttribute)attr;
		}
		return null;
	}

	public ELogicalAttribute getLogicalAttribute(int index) {
		if(index < attribute.size())
				return (ELogicalAttribute)attribute.get(index);
		return null;
	}

	public int getAttributeNumber(){
		return attribute.size();
	}

	/**
	 * @return the usage
	 */
	public UsageOption getUsage() {
		return usage;
	}

	/**
	 * @param usage
	 *            the usage to attributes
	 */
	public void setUsage(UsageOption usage) {
		this.usage = usage;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to attributes
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the suppressDiscretization
	 */
	public boolean isSuppressDiscretization() {
		return suppressDiscretization;
	}

	/**
	 * @param suppressDiscretization
	 *            the suppressDiscretization to attributes
	 */
	public void setSuppressDiscretization(boolean suppressDiscretization) {
		this.suppressDiscretization = suppressDiscretization;
	}

	/**
	 * @return the suppressNormalization
	 */
	public boolean isSuppressNormalization() {
		return suppressNormalization;
	}

	/**
	 * @param suppressNormalization
	 *            the suppressNormalization to attributes
	 */
	public void setSuppressNormalization(boolean suppressNormalization) {
		this.suppressNormalization = suppressNormalization;
	}

	/**
	 * @param outlierTreatment the outlierTreatment to attributes
	 */
	public void setOutlierTreatment(OutlierTreatment outlierTreatment) {
		this.outlierTreatment = outlierTreatment;
	}

	/**
	 * @return the outlierTreatment
	 */
	public OutlierTreatment getOutlierTreatment() {
		return outlierTreatment;
	}

	public Object clone() {
		EAttributeUsage o = null;
		o = (EAttributeUsage) super.clone();

		if (attribute != null) {
			ArrayList<LogicalAttribute> la = new ArrayList<LogicalAttribute>();
			for (LogicalAttribute a : attribute)
				la.add((ELogicalAttribute) a.clone());
			o.attribute = la;
		}

		return o;
	}

}