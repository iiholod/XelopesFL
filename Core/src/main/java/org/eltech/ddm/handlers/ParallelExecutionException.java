package org.eltech.ddm.handlers;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;

public class ParallelExecutionException extends MiningException {

	public ParallelExecutionException(String msg) {
		// TODO Auto-generated constructor stub
		super(MiningErrorCode.PARALLEL_EXECUTION_ERROR, msg);
	}

}
