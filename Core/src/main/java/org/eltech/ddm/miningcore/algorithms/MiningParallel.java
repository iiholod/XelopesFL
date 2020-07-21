package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.Distributable;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class MiningParallel extends MiningBlock {

    protected final MiningBlock[] blocks;

    protected MemoryType memoryType;

    protected int handlersNumber;

    protected List<MiningExecutor> executors;

    /**
     * Constructor for parallelization
     * @param settings
     * @param memory - type of memory(shared or distributed)
     * @param blocks - list of parallel mining blocks
     * @throws MiningException
     */
    public MiningParallel(EMiningFunctionSettings settings, MemoryType memory, MiningBlock... blocks) throws MiningException {
        super(settings);
        this.blocks = blocks;
        memoryType = memory;
        handlersNumber = blocks.length;
        executors = new ArrayList<>();
     }

     @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {

        if(blocks.length == 1){ // data-parallelization
            if(!(blocks[0] instanceof MiningLoop))
                throw new MiningException(MiningErrorCode.PARALLEL_EXECUTION_ERROR);

            MiningLoop block = (MiningLoop)blocks[0];
            MiningExecutor handler = executors.get(0);

            int startPos = 0;
            if(block instanceof MiningLoopElement) {
                int[] index = ((MiningLoopElement) block).getIndexSet();
                MiningModelElement elem = model.getElement(index);
                int countElement = elem.size() / handlersNumber;
                int mod = elem.size() % handlersNumber;
                handler.setBlock(new MiningLoopElement(functionSettings, index, startPos, countElement + mod, block.getIteration()));
                startPos += countElement + mod;
                for (int i = 1; i < handlersNumber; i++) {
                    MiningExecutor h = (MiningExecutor)handler.clone();
                    h.setBlock(new MiningLoopElement(functionSettings, index, startPos, countElement, block.getIteration()));
                    executors.add(h);
                    startPos += countElement;
                }
            }
        }

        List<EMiningModel> models = fork(model);

        if(memoryType == MemoryType.distributed) {
            if(model instanceof Distributable){
                Distributable distributable = ((Distributable) models.get(0));
                ((Distributable) model).setDistributionType(distributable.getDistributionType());
            }
            model.join(models);
            return model;
        } else {
            return models.get(0);
        }
    }

	private List<EMiningModel> fork(EMiningModel model)
			throws MiningException {

        for(int i = 0;  i < executors.size(); i++){
            MiningExecutor executor = executors.get(i);
            if(memoryType == MemoryType.distributed)
                executor.start((EMiningModel) model.clone());
            else
                executor.start( model.share());
        }

//        MiningExecutor lastExecutor =  executors.get(executors.size()-1);
        ArrayList<EMiningModel> resModels = new ArrayList<>();
//        if(memoryType == MemoryType.distributed)
//            resModels.add(lastExecutor.getBlock().execute((EMiningModel) model.clone()));
//        else
//            resModels.add(lastExecutor.getBlock().execute(model.share()));

        // finished all executors
        for(int i = 0;  i < executors.size(); i++){
            MiningExecutor executor = executors.get(i);
            resModels.add(executor.getModel());
        }

        return resModels;
    }

    public boolean isDataBlock(){
        boolean flag = false;
        for (MiningBlock block: blocks)
            flag = flag ||  block.isDataBlock();

        return flag;
    }

    public void addExecutor(MiningExecutor handler){
         executors.add(handler);
    }

    public List<MiningExecutor> getExecutors(){
        return executors;
    }

    public MiningBlock[] getBlocks(){
        return blocks;
    }

    public void setHandlersNumber(int handlersNumber) {
        this.handlersNumber = handlersNumber;
    }

    @Override
    public Object clone() {
        MiningParallel o = (MiningParallel) super.clone();

        if (executors != null) {
            o.executors = new ArrayList<MiningExecutor>();
            for (MiningExecutor exec : executors) {
                o.executors.add((MiningExecutor)exec.clone());
            }
        }

        return o;
    }

    /*
         *   Definition listeners for parallel block
         */
    protected ArrayList<BlockExecuteListener> listenersBeforeSplit = new ArrayList<BlockExecuteListener>();
    protected ArrayList<BlockExecuteListener> listenersAfterSplit = new ArrayList<BlockExecuteListener>();
    protected ArrayList<BlockExecuteListener> listenersBeforeJoin = new ArrayList<BlockExecuteListener>();
    protected ArrayList<BlockExecuteListener> listenersAfterJoin = new ArrayList<BlockExecuteListener>();

    public void addListenerExecute(BlockExecuteListener listener) {
        super.addListenerExecute(listener);
        addListener(listener, listenersBeforeSplit);
        addListener(listener, listenersAfterSplit);
        addListener(listener, listenersBeforeJoin);
        addListener(listener, listenersAfterJoin);
    }

    public void addListenerBeforeSplit(BlockExecuteListener listener) {
        MiningBlock.addListener(listener, listenersBeforeSplit);
    }

    public void notifyBeforeSplit() {
        MiningBlock.notifyListeners(this, listenersBeforeSplit, EventType.BeforeSplit);
    }

    public void removeListenerBeforeSplit(BlockExecuteListener listener) {
        MiningBlock.removeListener(listener, listenersBeforeSplit);
    }

    public void addListenerAfterSplit(BlockExecuteListener listener) {
        MiningBlock.addListener(listener, listenersAfterSplit);
    }

    public void notifyAfterSplit() {
        MiningBlock.notifyListeners(this, listenersAfterSplit, EventType.AfterSplit);
    }

    public void removeListenerAfterSplit(BlockExecuteListener listener) {
        MiningBlock.removeListener(listener, listenersAfterSplit);
    }

    public void addListenerBeforeJoin(BlockExecuteListener listener) {
        MiningBlock.addListener(listener, listenersBeforeJoin);
    }

    public void notifyBeforeJoin() {
        MiningBlock.notifyListeners(this, listenersBeforeJoin, EventType.BeforeJoin);
    }

    public void removeListenerBeforeJoin(BlockExecuteListener listener) {
        MiningBlock.removeListener(listener, listenersBeforeJoin);
    }

    public void addListenerAfterJoin(BlockExecuteListener listener) {
        MiningBlock.addListener(listener, listenersAfterJoin);
    }

    public void notifyAfterJoin() {
        MiningBlock.notifyListeners(this, listenersAfterJoin, EventType.AfterJoin);
    }

    public void removeListenerAfterJoin(BlockExecuteListener listener) {
        MiningBlock.removeListener(listener, listenersAfterJoin);
    }

    public void removeAllListeners() {
        listenersBeforeSplit.clear();
        listenersAfterSplit.clear();
        listenersBeforeJoin.clear();
        listenersAfterJoin.clear();
    }


}
