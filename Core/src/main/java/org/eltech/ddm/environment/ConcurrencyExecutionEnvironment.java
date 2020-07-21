package org.eltech.ddm.environment;

import org.eltech.ddm.handlers.MiningExecutorFactory;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.handlers.thread.ConcurrencyExecutorFactory;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.common.CloneableStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;

import java.util.ArrayList;
import java.util.List;

public class ConcurrencyExecutionEnvironment extends ExecutionEnvironment {

    private int numberThreads = 1;
    private MiningInputStream data;

    public ConcurrencyExecutionEnvironment(MiningInputStream data) throws MiningException {
        this.numberThreads = 1;
        this.data = data;
        initEnvironment();
    }

    public ConcurrencyExecutionEnvironment(int numberThreads, MiningInputStream data) throws MiningException {
        this.numberThreads = numberThreads;
        this.data = data;
        initEnvironment();
    }

    @Override
    protected void initEnvironment() throws ParallelExecutionException {
        miningExecutorFactory = new ConcurrencyExecutorFactory(numberThreads);
    }

    @Override
    protected List<MiningExecutor> createExecutors(MiningBlock block) throws MiningException {
        List<MiningExecutor> execs = new ArrayList<>();
        if (block instanceof MiningLoopVectors) {
            MiningLoopVectors bl = (MiningLoopVectors) block;
            int startPos = 0;
            int countElement = data.getVectorsNumber() / numberThreads;
            int mod = data.getVectorsNumber() % numberThreads;
            MiningLoopVectors mlv = new MiningLoopVectors(bl.getFunctionSettings(), startPos, countElement + mod, bl.getIteration());
            MiningExecutor executor = getMiningExecutorFactory().create(mlv, data);
            execs.add(executor);
            startPos += countElement + mod;
            for (int i = 1; i < numberThreads; i++) {
                mlv = new MiningLoopVectors(bl.getFunctionSettings(), startPos, countElement, (MiningSequence) bl.getIteration().clone());
                executor = getMiningExecutorFactory().create(mlv, data instanceof CloneableStream ? ((CloneableStream) data).deepCopy() : data);
                execs.add(executor);
                startPos += countElement;
            }
        } else {
            MiningExecutor executor = null;
            if (block.isDataBlock())
                executor = getMiningExecutorFactory().create(block, data);
            else
                executor = getMiningExecutorFactory().create(block);
            execs.add(executor);
        }
        return execs;
    }

    public void deploy(MiningAlgorithm algorithm) throws MiningException {
//		if(numberThreads > 1) {
        mainExecutor = createExecutorTree(algorithm.getCentralizedParallelAlgorithm());
        //fullExecutorsByData(mainExecutor);
//		} else {
//			mainExecutor = getMiningExecutorFactory().create(algorithm.getSequenceAlgorithm(), data);
//		}
    }


    public MiningExecutorFactory getMiningExecutorFactory() {
        return miningExecutorFactory;
    }

}
