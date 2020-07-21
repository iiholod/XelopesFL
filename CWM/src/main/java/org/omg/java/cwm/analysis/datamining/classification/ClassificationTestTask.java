package org.omg.java.cwm.analysis.datamining.classification;

import org.omg.java.cwm.analysis.datamining.supervised.MiningTestTask;

/**
 * CWM Class
 *
 * This represents a task to check the quality of a classification model. An overall
 * accuracy and a class by class confusion matrix can be computed.
 *
 * @author Ivan Holod
 *
 */
public class ClassificationTestTask extends MiningTestTask {

	/**
	 * This references to a matrix holding the absolute numbers of wrong predictions. A cell entry
	 * c(A,B)=n indicates that n test records had class label A in the target field, but class B was
	 * predicted by the model.
	 */
	protected ClassificationTestResult testResult;
}
