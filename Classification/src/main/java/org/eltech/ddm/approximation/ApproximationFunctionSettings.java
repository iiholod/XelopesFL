package org.eltech.ddm.approximation;

import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningFunction;
import org.eltech.ddm.supervised.ESupervisedFunctionSettings;


/**
 * CWM Class
 *
 * An ApproximationFunctionSettings is a subclass of SupervisedFunctionSettings that
 * supports features that are unique to approximation function that finds approximates of
 * numerical values.
 *
 * @author Ivan Holod
 *
 */
public class ApproximationFunctionSettings extends ESupervisedFunctionSettings //implements RegressionSettings
{


	/**
	 * The tolerated error is defined in terms of R-squared.
	 */
	private double toleratedError;

	public ApproximationFunctionSettings(ELogicalData ld) {
		super(ld);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MiningFunction getMiningFunction() {
		// TODO Auto-generated method stub
		return MiningFunction.approximation;
	}


/*	//@Override
	public String getTargetAttributeName() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public void setTargetAttributeName(String arg0) throws JDMException {
		// TODO Auto-generated method stub

	}

	//@Override
	public EMiningAlgorithmSettings getAlgorithmSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public String[] getAttributeNames(AttributeRetrievalType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public int getDesiredExecutionTimeInMinutes() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public Collection getLogicalAttributes(LogicalAttributeUsage arg0)
			throws JDMException {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public ELogicalData getLogicalData() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public String getLogicalDataName() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public MiningFunction getMiningFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public Interval getOutlierIdentification(String arg0) throws JDMException {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public OutlierTreatment getOutlierTreatment(String arg0)
			throws JDMException {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public LogicalAttributeUsage getUsage(String arg0) throws JDMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getWeight(String arg0) throws JDMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getWeightAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlgorithmSettings(AlgorithmSettings arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDesiredExecutionTimeInMinutes(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLogicalDataName(String arg0) throws JDMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOutlierIdentification(String arg0, Interval arg1)
			throws JDMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOutlierTreatment(String arg0, OutlierTreatment arg1)
			throws JDMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUsage(String arg0, LogicalAttributeUsage arg1)
			throws JDMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWeight(String arg0, double arg1) throws JDMException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWeightAttribute(String arg0) throws JDMException {
		// TODO Auto-generated method stub

	}

	@Override
	public VerificationReport verify() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getCreationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCreatorInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObjectIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamedObject getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String arg0) throws JDMException {
		// TODO Auto-generated method stub

	}
*/

}
