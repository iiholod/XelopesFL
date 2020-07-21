package org.eltech.ddm.miningcore.miningtask;


import org.eltech.ddm.environment.ExecutionEnvironment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningdata.EAttributeAssignmentSet;
import org.eltech.ddm.miningcore.miningdata.EPhysicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningtask.MiningBuildTask;

/**
 * CWM Class
 *
 * This describes a task that builds a mining model, sometimes also called
 * training task.
 *
 * @author Ivan Holod
 *
 */
public class EMiningBuildTask extends MiningBuildTask // implements BuildTask
{

	/**
	 * This is mining algorithm with build model
	 */
	protected MiningAlgorithm miningAlgorithm;
	
	protected ExecutionEnvironment executionEnvironment;
	
	// fields added for JDMAPI
	private String applicationName;

	private String modelDescription;

	/**
	 * Returns the name of the application that uses this task to build a model.
	 *
	 * @return
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Returns the description of the model.
	 *
	 * @return
	 */
	public String getModelDescription() {
		// TODO Auto-generated method stub
		return modelDescription;
	}

	/**
	 * Sets the name of the application that uses this task to build a model.
	 * The application name can be later retrieved from the model after the
	 * model is built. Refer to Model.
	 *
	 * @param name
	 *            -
	 */
	public void setApplicationName(String name) {
		applicationName = name;

	}

	/**
	 * Sets the description of the model.
	 *
	 * @param description
	 *            -
	 */
	public void setModelDescription(String description) {
		modelDescription = description;

	}

	/**
	 * Returns the mining function settings used as a specification for building
	 * a model. Note! This method uses instead getBuildSettingsName of JDMAPI
	 *
	 * @return the miningSettings
	 */
	public EMiningFunctionSettings getMiningSettings() {
		return (EMiningFunctionSettings)miningSettings;
	}

	/**
	 * Sets the mining function settings to be used as a specification for
	 * building a model. Note! This method uses instead setBuildSettingsName of
	 * JDMAPI
	 *
	 * @param miningSettings
	 *            the miningSettings to attributes
	 */
	public void setMiningSettings(EMiningFunctionSettings miningSettings) {
		this.miningSettings = miningSettings;
	}

	/**
	 * Returns the model to be built by this task. Note! This method uses
	 * instead getModelName of JDMAPI
	 *
	 * @return the resultModel
	 */
	public EMiningModel getResultModel() {
		return (EMiningModel)resultModel;
	}

	/**
	 * Sets the model to be built by the task. Note! This method uses instead
	 * setModelName of JDMAPI
	 *
	 * @param resultModel
	 *            the resultModel to attributes
	 */
	public void setResultModel(EMiningModel resultModel) {
		this.resultModel = resultModel;
	}

	/**
	 * Returns the attribute map for build input data. Returns null if no map
	 * has been provided. Note! This method uses instead getBuildDataMap of
	 * JDMAPI
	 *
	 * @return the settingsAssignment
	 */
	public EAttributeAssignmentSet getSettingsAssignment() {
		return (EAttributeAssignmentSet)settingsAssignment;
	}

	/**
	 * Specifies a mapping between physical attribute names and logical
	 * attribute names for input build data Note! This method uses instead
	 * setBuildDataMap of JDMAPI
	 *
	 * @param settingsAssignment
	 *            the settingsAssignment to attributes
	 */
	public void setSettingsAssignment(EAttributeAssignmentSet settingsAssignment) {
		this.settingsAssignment = settingsAssignment;
	}

	/**
	 * Returns the attribute map for validation input data. Returns null if no
	 * map has been provided. Note! This method uses instead
	 * getValidationDataMap of JDMAPI
	 *
	 * @return the settingsValidationAssignment
	 */
	public EAttributeAssignmentSet getSettingsValidationAssignment() {
		return (EAttributeAssignmentSet)settingsValidationAssignment;
	}

	/**
	 * Specifies a mapping between physical attribute names and logical
	 * attribute names for input validation data. The key for the mapping is the
	 * physical attribute name. Note! This method uses instead
	 * setValidationDataMap of JDMAPI
	 *
	 * @param settingsValidationAssignment
	 *            the settingsValidationAssignment to attributes
	 */
	public void setSettingsValidationAssignment(
			EAttributeAssignmentSet settingsValidationAssignment) {
		this.settingsValidationAssignment = settingsValidationAssignment;
	}

	/**
	 * Returns the data to be used for validation while building the model.
	 * Note! This method uses instead getValidationDataName of JDMAPI
	 *
	 * @return the validationData
	 */
	public EPhysicalData getValidationData() {
		return (EPhysicalData)validationData;
	}

	/**
	 * Specifies the name of the data to be used for validation while building
	 * the model. Note! This method uses instead setValidationDataName of JDMAPI
	 *
	 * @param validationData
	 *            the validationData to attributes
	 */
	public void setValidationData(EPhysicalData validationData) {
		this.validationData = validationData;
	}

	public MiningAlgorithm getMiningAlgorithm() {
		return miningAlgorithm;
	}

	public void setMiningAlgorithm(MiningAlgorithm miningAlgorithm) {
		this.miningAlgorithm = miningAlgorithm;
	}

	public ExecutionEnvironment getExecutionEnvironment() {
		return executionEnvironment;
	}

	public void setExecutionEnvironment(ExecutionEnvironment executionEnvironment) {
		this.executionEnvironment = executionEnvironment;
	}

	public boolean verify(){
		if(miningSettings == null)
			return false;
		
		if(((EMiningFunctionSettings)miningSettings).getAlgorithmSettings() == null)
			return false;
		
		if(executionEnvironment == null)
			return false;
		

		if (miningAlgorithm == null)
			return false;

		EMiningAlgorithmSettings algSettings = ((EMiningFunctionSettings)miningSettings).getAlgorithmSettings();
		
		return true;
	}

	public MiningModel execute() throws MiningException {
		
		verify();

		executionEnvironment.deploy(miningAlgorithm);

		resultModel = miningAlgorithm.initModel();
		
		resultModel = executionEnvironment.runAlgorithm((EMiningModel) resultModel);
		
		return resultModel;
	}

}