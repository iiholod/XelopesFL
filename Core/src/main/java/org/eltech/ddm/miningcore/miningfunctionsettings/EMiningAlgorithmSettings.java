/**
 *
 */
package org.eltech.ddm.miningcore.miningfunctionsettings;

import org.eltech.ddm.miningcore.NamedObject;
import org.eltech.ddm.miningcore.VerificationReport;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningAlgorithmSettings;

import java.io.Serializable;

/**
 *  CWM Class
 *
 *  A mining algorithm settings object captures the parameters associated with a particular
 *  algorithm. It allows a knowledgeable user to fine tune algorithm parameters. Generally,
 *  not all parameters must be specified, however, those specified are taken into account by
 *  the underlying data mining system.
 *
 *  Separating mining algorithm from mining function provides a natural and convenient
 *  separation for those users experienced with data mining algorithms and those only
 *  familiar with mining functions.
 *
 *  @author Ivan Holod
 */
public class EMiningAlgorithmSettings extends MiningAlgorithmSettings implements Serializable
//javax.datamining.base.AlgorithmSettings
{
	private static final String algorithmsFileName = "config/algorithms.xml"; // ?

	private String algorithm;

	private String classname;

	private String version;

	/**
	 * Verifies if the settings are valid to some degree of correctness as specified by the vendor.
	 * Returns null if the settings object is valid. If the settings object is invalid, it returns
	 * an instance of VerificationReport, which contains a vendor specific explanation.
	 * @return
	 */
	public VerificationReport verify() {

		return null;
	}

	/**
	 * Returns the type of mining algorithm specified for the algorithm settings.
	 * @return the algorithm
	 */
	public String getMiningAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to attributes
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @return the class name
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * @param classname the classname to attributes
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to attributes
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public String toString() {
		String s = String.valueOf(String.valueOf(getName())).concat(" (");
//		for (int i = 0; i < this.inputAttribute.length; i++) {
//			s = String.valueOf(String.valueOf(new StringBuffer(String
//					.valueOf(String.valueOf(s)))
//					.append(this.inputAttribute[i].getName()).append("=")
//					.append(this.inputAttribute[i].getValue()).append(" ")));
//		}
		s = String.valueOf(s).concat(")");
		return s;
	}

	//@Override
	public NamedObject getObjectType() {
		// TODO Auto-generated method stub
		return NamedObject.algorithmSettings;
	}

}
