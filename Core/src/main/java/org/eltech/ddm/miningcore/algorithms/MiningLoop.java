package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class MiningLoop extends MiningBlock implements Cloneable, Serializable {

	protected MiningSequence iteration;

	protected transient ArrayList<BlockExecuteListener> listenersBeforeInitLoop;
	protected transient ArrayList<BlockExecuteListener> listenersAfterInitLoop;
	protected transient ArrayList<BlockExecuteListener> listenersBeforeCondition;
	protected transient ArrayList<BlockExecuteListener> listenersAfterCondition;
	protected transient ArrayList<BlockExecuteListener> listenersBeforePreIteration;
	protected transient ArrayList<BlockExecuteListener> listenersAfterPreIteration;
	protected transient ArrayList<BlockExecuteListener> listenersBeforePostIteration;
	protected transient ArrayList<BlockExecuteListener> listenersAfterPostIteration;

    public MiningLoop(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		iteration =  new MiningSequence(settings);

		listenersBeforeInitLoop = new ArrayList<>();
		listenersAfterInitLoop = new ArrayList<>();
		listenersBeforeCondition = new ArrayList<>();
		listenersAfterCondition = new ArrayList<>();
		listenersBeforePreIteration = new ArrayList<>();
		listenersAfterPreIteration = new ArrayList<>();
		listenersBeforePostIteration = new ArrayList<>();
		listenersAfterPostIteration = new ArrayList<>();
	}

	public MiningLoop() {
	}

	public MiningLoop(EMiningFunctionSettings settings, MiningBlock... blocks) throws MiningException {
		super(settings);
		iteration = new MiningSequence(settings, blocks);
	}

	abstract protected EMiningModel initLoop(EMiningModel model) throws MiningException;//TODO

	abstract protected boolean conditionLoop(EMiningModel model) throws MiningException;//TODO

	abstract protected EMiningModel beforeIteration(EMiningModel model) throws MiningException;//TODO

	abstract protected EMiningModel afterIteration(EMiningModel model) throws MiningException;//TODO

	@Override
	public EMiningModel execute( EMiningModel model) throws MiningException {
		boolean cond;

		EMiningModel result = initLoop(model);

		cond = conditionLoop(result);

		while(cond){
			result = beforeIteration(result);

			result = iteration.run(result);

			result = afterIteration(result);

			cond = conditionLoop(result);
		}
		return result;
	}


	public boolean isDataBlock(){
		return iteration.isDataBlock();
	}
	/**
         * @return the iteration
         */
	public MiningSequence getIteration() {
		return iteration;
	}

	//-------- Listener methods

	public void addListenerBeforeInitLoop(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersBeforeInitLoop);
	}

	public void notifyBeforeInitLoop(){
		MiningBlock.notifyListeners(this, listenersBeforeInitLoop, EventType.BeforeInitLoop);
	}

	public boolean removeListenerBeforeInitLoop(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersBeforeInitLoop);
	}

	public void addListenerAfterInitLoop(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersAfterInitLoop);
	}

	public void notifyAfterInitLoop(){
		MiningBlock.notifyListeners(this, listenersAfterInitLoop, EventType.AfterInitLoop);
	}

	public boolean removeListenerAfterInitLoop(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersAfterInitLoop);
	}

	//----

	public void addListenerBeforeCondition(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersBeforeCondition);
	}

	public void notifyBeforeCondition(){
		MiningBlock.notifyListeners(this, listenersBeforeCondition, EventType.BeforeCondition);
	}

	public boolean removeListenerBeforeCondition(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersBeforeCondition);
	}

	public void addListenerAfterCondition(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersAfterCondition);
	}

	public void notifyAfterCondition(){
		MiningBlock.notifyListeners(this, listenersAfterCondition, EventType.AfterCondition);
	}

	public boolean removeListenerAfterCondition(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersAfterCondition);
	}

	//---

	public void addListenerBeforePreIteration(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersBeforePreIteration);
	}

	public void notifyBeforePreIteration(){
		MiningBlock.notifyListeners(this, listenersBeforePreIteration, EventType.BeforePreIteration );
	}

	public boolean removeListenerBeforePreIteration(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersBeforePreIteration);
	}

	public void addListenerAfterPreIteration(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersAfterPreIteration);
	}

	public void notifyAfterPreIteration(){
		MiningBlock.notifyListeners(this, listenersAfterPreIteration, EventType.AfterPreIteration );
	}

	public boolean removeListenerAfterPreIteration(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersAfterPreIteration);
	}

	//---

	public void addListenerBeforePostIteration(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersBeforePostIteration);
	}

	public void notifyBeforePostIteration(){
		MiningBlock.notifyListeners(this, listenersBeforePostIteration, EventType.BeforePostIteration);
	}

	public boolean removeListenerBeforePostIteration(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersBeforePostIteration);
	}

	public void addListenerAfterPostIteration(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersAfterPostIteration);
	}

	public void notifyAfterPostIteration(){
		MiningBlock.notifyListeners(this, listenersAfterPostIteration, EventType.AfterPostIteration);
	}

	public boolean removeListenerAfterPostIteration(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersAfterPostIteration);
	}

	public void removeAllListeners()
	{
		listenersBeforeInitLoop.clear();
		listenersAfterInitLoop.clear();
		listenersBeforeCondition.clear();
		listenersAfterCondition.clear();
		listenersBeforePreIteration.clear();
		listenersAfterPreIteration.clear();
		listenersBeforePostIteration.clear();
		listenersAfterPostIteration.clear();
	}
	
	public Object clone() {
		MiningLoop o = null;
	    o = (MiningLoop)super.clone();

//	    listenersBeforeInitLoop = (ArrayList<BlockExecuteListener>) listenersBeforeInitLoop.clone();
//	    listenersAfterInitLoop = (ArrayList<BlockExecuteListener>) listenersAfterInitLoop.clone();
//	    listenersBeforeCondition = (ArrayList<BlockExecuteListener>) listenersBeforeCondition.clone();
//	    listenersAfterCondition = (ArrayList<BlockExecuteListener>) listenersAfterCondition.clone();
//	    listenersBeforePreIteration = (ArrayList<BlockExecuteListener>) listenersBeforePreIteration.clone();
//	    listenersAfterPreIteration = (ArrayList<BlockExecuteListener>) listenersAfterPreIteration.clone();
//	    listenersBeforePostIteration = (ArrayList<BlockExecuteListener>) listenersBeforePostIteration.clone();
//	    listenersAfterPostIteration = (ArrayList<BlockExecuteListener>) listenersAfterPostIteration.clone();

	    if(iteration != null)
	    	o.iteration = (MiningSequence)iteration.clone();


	    return o;
	}
	

}