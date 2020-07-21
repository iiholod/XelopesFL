package org.omg.java.cwm.analysis.datamining.miningcore.miningmodel;


import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;
import org.omg.java.cwm.objectmodel.core.Attribute;
import org.omg.java.cwm.objectmodel.core.ModelElement;
import org.omg.java.cwm.objectmodel.core.String;


/**
 * CWM Class
 *
 * A MiningModel holds the metadata of the result of a mining (training) task. This
 * information is sufficient to determine whether a model can be applied to a given data.
 *
 * @author Ivan Holod
 *
 */
public class MiningModel extends ModelElement {

	public MiningModel(){

	}
	/**
	 * Data mining function (as opposed to algorithm); for example, classification or clustering,
	 * attributeImportance, associationRules, classification, approximation, clustering
	 */
	private MiningFunction function;

	/**
	 * Specific implementation of the data mining function (e.g., CART decision tree or SOM
	 * clustering). The following algorithm names are predefined (their functions in parentheses):
	 * � decisionTree (classification, approximation)
	 * � neuralNetwork (classification, approximation)
	 * � naiveBayes (classification)
	 * � selfOrganizingMap (clustering)
	 * � kMeans (clustering)
	 * � competitiveLearning
	 */
	protected String algorithmName = new String();

	/**
	 * This optionally represents the key value when the model is to be located.
	 */
	protected Object keyValue;

	/**
	 * The settings that were used to generate the model.
	 */
	protected MiningFunctionSettings settings;

	/**
	 * The set of attributes (SignatureAttributes) that were used to build the model.
	 */
	protected ModelSignature modelSignature;

	/**
	 * This optionally provides a way to locate the model in the metadata repository.
	 */
	//protected Class modelLocation;

	/**
	 * This optionally identifies the key attribute when the model is located via modelLocation.
	 */
	protected Attribute keyAttribute;

	public MiningFunctionSettings getSettings() {
		return settings;
	}

	public void setSettings(MiningFunctionSettings settings) {
		this.settings = settings;
	}
}