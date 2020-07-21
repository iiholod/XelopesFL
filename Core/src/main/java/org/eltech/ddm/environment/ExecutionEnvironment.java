package org.eltech.ddm.environment;

import org.eltech.ddm.handlers.MiningExecutorFactory;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.ArrayList;
import java.util.List;

public abstract class ExecutionEnvironment<T extends MiningExecutor, F extends MiningExecutorFactory<T>> {
    protected MiningExecutor mainExecutor = null;

    protected F miningExecutorFactory;

    public ExecutionEnvironment() {
        //initEnvironment();
    }

    protected abstract void initEnvironment() throws ParallelExecutionException;

    protected MiningExecutor createExecutorTree(MiningSequence sequence) throws MiningException {
        //MiningExecutor mainHandler = getMiningExecutorFactory().create(sequence);
        List<MiningExecutor> execs = createExecutors(sequence);
        MiningExecutor mainHandler = execs.get(0);
        fullExecutor(mainHandler.getBlock(), mainHandler);
        return mainHandler;
    }

    protected void fullExecutor(MiningBlock miningBlock, MiningExecutor executor) throws MiningException {
        List<MiningBlock> blockList = getChildrenMiningBlock(miningBlock);

        for (MiningBlock block : blockList) {
            if (block instanceof DataMiningBlock)
                ((DataMiningBlock) block).setData(executor.getData());

            if (block instanceof MiningParallel) {
                MiningParallel mp = (MiningParallel) block;
                executor.addParallelBlock(mp);
                MiningBlock blocks[] = mp.getBlocks();
                for (MiningBlock bl : blocks) {
                    List<MiningExecutor> execs = createExecutors(bl);
                    for (MiningExecutor exec : execs) {
                        mp.addExecutor(exec);
                        fullExecutor(exec.getBlock(), exec);
                    }
                }
            } else {
                fullExecutor(block, executor);
            }
        }
    }

    protected List<MiningBlock> getChildrenMiningBlock(MiningBlock block) {

        if (block instanceof MiningSequence) {
            return ((MiningSequence) block).getSequence();
        } else if (block instanceof MiningDecision) {
            List<MiningBlock> list = new ArrayList<>();
            MiningDecision decisionBlock = (MiningDecision) block;
            if (decisionBlock.getTrueBranch() != null) {
                list.addAll(decisionBlock.getTrueBranch().getSequence());
            }
            if (decisionBlock.getFalseBranch() != null) {
                list.addAll(decisionBlock.getFalseBranch().getSequence());
            }
            return list;
        } else if (block instanceof MiningLoop) {
            MiningLoop loop = (MiningLoop) block;
            if (loop.getIteration() != null) {
                return loop.getIteration().getSequence();
            }
        }
        return new ArrayList<>();
    }

    public abstract void deploy(MiningAlgorithm algorithm) throws MiningException;


    protected abstract List<MiningExecutor> createExecutors(MiningBlock bl) throws MiningException;


    public EMiningModel runAlgorithm(EMiningModel initModel) throws MiningException {
        if (mainExecutor == null)
            throw new MiningException(MiningErrorCode.PARALLEL_EXECUTION_ERROR, "An algorithm is not deployed to the environment!");

        mainExecutor.start(initModel);
        EMiningModel resultModel = mainExecutor.getModel();
        return resultModel;
    }

    public F getMiningExecutorFactory() {
        return miningExecutorFactory;
    }

}
