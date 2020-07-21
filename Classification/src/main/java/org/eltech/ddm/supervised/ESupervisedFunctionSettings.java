package org.eltech.ddm.supervised;

import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;

/**
 * CWM Class
 *
 * A SupervisedFunctionSettings is a subclass of MiningFunctionSettings that supports
 * features that are unique and shared by supervised functions (e.g., classification and
 * approximation, as well as algorithms (e.g., decision trees and neural networks).
 *
 * @author Ivan Holod
 *
 */
public abstract class ESupervisedFunctionSettings extends EMiningFunctionSettings {

	public ESupervisedFunctionSettings(ELogicalData ld) {
		super(ld);
		// TODO Auto-generated constructor stub
	}

}
