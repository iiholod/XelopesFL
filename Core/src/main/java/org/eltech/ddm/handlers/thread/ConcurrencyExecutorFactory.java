package org.eltech.ddm.handlers.thread;

import org.eltech.ddm.handlers.MiningExecutorFactory;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyExecutorFactory extends MiningExecutorFactory<ConcurrencyMiningExecutor> {
    ExecutorService service;


    public ConcurrencyExecutorFactory(int numThreds) {
        service = Executors.newCachedThreadPool(); //newFixedThreadPool(numThreds);
    }

    @Override
    public ConcurrencyMiningExecutor create(MiningBlock block) throws ParallelExecutionException {
        return new ConcurrencyMiningExecutor(block, service);
    }

    @Override
    public ConcurrencyMiningExecutor create(MiningBlock block, MiningInputStream data) throws ParallelExecutionException {
        ConcurrencyMiningExecutor executor = new ConcurrencyMiningExecutor(block, service);
        executor.setData(data);
        return executor;
    }

}
