package org.eltech.ddm.miningcore.miningdata;

/**
 * The enumeration OutlierTreatment defines how outliers are to be treated during model build. 
 * @author Ivan Holod
 *
 */
public enum OutlierTreatment {
	systemDefault, 
	systemDetermined, 
	asIs, 
	asMissing
}
