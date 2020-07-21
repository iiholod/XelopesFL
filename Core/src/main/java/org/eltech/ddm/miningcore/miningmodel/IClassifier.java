package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;

import java.io.Serializable;

public interface IClassifier extends Serializable {

	double apply(MiningVector miningVector) throws MiningException;
}