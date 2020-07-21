package org.eltech.ddm.environment;


import org.eltech.ddm.handlers.MiningExecutorFactory;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.handlers.thread.ConcurrencyExecutorFactory;
import org.eltech.ddm.inputdata.file.common.FileSeparator;
import org.eltech.ddm.inputdata.file.csv.CsvFileSeparator;
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Custom CSV Execution environment provides performance boost in case of
 * parallel execution by separating input file into several input streams
 * Creates temporary files in order to be able to read from N resources.
 * <p>
 * Be aware: it's a user responsibility to clean up all temporary files
 * under project directory
 *
 * @author Evgenii Ray
 */
public class ConcurrentCSVExecutionEnvironment extends ExecutionEnvironment {

    private static final Logger LOGGER = Logger.getLogger(ConcurrentCSVExecutionEnvironment.class.getName());

    private int threadNumber = 1;
    private static final int START_POSITION = 0;
    private String mainDataFile;
    private FileSeparator<MiningCsvStream> separator = new CsvFileSeparator();
    private static List<MiningCsvStream> streams = new ArrayList<>();

    /**
     * Main constructor for the environment
     *
     * @param file         - data file of the resource
     * @param threadNumber - count of threads to use
     * @throws ParallelExecutionException - in case of the parallel exec error
     */
    public ConcurrentCSVExecutionEnvironment(String file, int threadNumber) throws MiningException {
        LOGGER.info(String.format("[INITIATING ENVIRONMENT FOR THE NEXT TARGET FILE: %s] [HANDLERS: %d]", file, threadNumber));
        this.mainDataFile = file;
        this.threadNumber = threadNumber;
        streams = threadNumber == 1
                ? Collections.singletonList(new MiningCsvStream(mainDataFile, null, false))
                : separator.separate(mainDataFile, threadNumber);
        initEnvironment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initEnvironment() {
        miningExecutorFactory = new ConcurrencyExecutorFactory(threadNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<MiningExecutor> createExecutors(MiningBlock block) throws MiningException {
        List<MiningExecutor> execs = new ArrayList<>();
        if (block instanceof MiningLoopVectors) {
            MiningLoopVectors bl = (MiningLoopVectors) block;
            int summaryCount = 0;
            for (int i = 0; i < threadNumber; i++) {
                summaryCount = +streams.get(i).getVectorsNumber();
                MiningLoopVectors mlv = new MiningLoopVectors(bl.getFunctionSettings(), START_POSITION, streams.get(i).getVectorsNumber(), (MiningSequence) bl.getIteration().clone());
                MiningExecutor executor = getMiningExecutorFactory().create(mlv, streams.get(i));
                execs.add(executor);
            }
            LOGGER.info("[Total Count of vectors to perform: " + summaryCount + " ]");
        } else {
            MiningExecutor executor;
            if (block.isDataBlock()) {
                executor = getMiningExecutorFactory().create(block, null);
            } else {
                executor = getMiningExecutorFactory().create(block);
            }
            execs.add(executor);
        }
        return execs;
    }


    /**
     * {@inheritDoc}
     */
    public void deploy(MiningAlgorithm algorithm) throws MiningException {
        mainExecutor = createExecutorTree(algorithm.getCentralizedParallelAlgorithm());
    }

    /**
     * {@inheritDoc}
     */
    public MiningExecutorFactory getMiningExecutorFactory() {
        return miningExecutorFactory;
    }
}
