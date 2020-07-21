package org.eltech.ddm.handlers;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningExecutor;

public abstract class MiningExecutorFactory<T extends MiningExecutor>  {

    public abstract T create(MiningBlock block) throws ParallelExecutionException;

    public abstract T create(MiningBlock block, MiningInputStream data) throws ParallelExecutionException;

}
