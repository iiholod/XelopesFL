package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.ArrayList;

public abstract class MiningDecision extends MiningBlock {

	protected MiningSequence trueBranch;

	protected MiningSequence falseBranch;

	protected ArrayList<BlockExecuteListener> listenersBeforeCondition;
	protected ArrayList<BlockExecuteListener> listenersAfterCondition;

	public MiningDecision(EMiningFunctionSettings settings) throws MiningException {
		super(settings);

		trueBranch = new MiningSequence(settings);
		falseBranch = new MiningSequence(settings);

		listenersBeforeCondition = new ArrayList<>();
		listenersAfterCondition = new ArrayList<>();
	}

	public MiningDecision(EMiningFunctionSettings settings, MiningBlock... trueBlocks) throws MiningException {
		this(settings);
		trueBranch = new MiningSequence(settings, trueBlocks);
	}

	public MiningDecision(EMiningFunctionSettings settings, MiningSequence trueSteps, MiningBlock... falseBlocks) throws MiningException {
		this(settings);
		trueBranch = trueSteps;
		falseBranch = new MiningSequence(settings, falseBlocks);
	}

	public MiningDecision(EMiningFunctionSettings settings, MiningSequence trueSteps, MiningSequence falseSteps) throws MiningException {
		this(settings);
		trueBranch = trueSteps;
		falseBranch = falseSteps;
	}

	abstract protected boolean condition(EMiningModel model) throws MiningException;

	@Override
	public EMiningModel execute(EMiningModel model) throws MiningException {
		boolean cond;

		this.notifyBeforeCondition();
		cond = condition(model);
		this.notifyAfterCondition();

		EMiningModel result = null;
		if (cond){
			if(trueBranch != null)
				result = trueBranch.run(model);
		} else{
			if(falseBranch != null)
				result = falseBranch.run(model);
		}

		return result;
	}

	public boolean isDataBlock(){
		boolean flag = false;
		if(trueBranch != null)
			flag = flag ||  trueBranch.isDataBlock();
		if(falseBranch != null)
			flag = flag ||  falseBranch.isDataBlock();

		return flag;
	}

	/**
	 * @return the trueBranch
	 */
	public MiningSequence getTrueBranch() {
		return trueBranch;
	}

	/**
	 * @return the falseBranch
	 */
	public MiningSequence getFalseBranch() {
		return falseBranch;
	}

	//-------- Listener methods

	public void addListenerBeforeCondition(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersBeforeCondition);
	}

	public void notifyBeforeCondition(){
		MiningBlock.notifyListeners(this, listenersBeforeCondition,EventType.BeforeCondition);
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

	public void removeAllListeners()
	{
		listenersBeforeCondition.clear();
		listenersAfterCondition.clear();
	}
	
	public Object clone() {
		MiningDecision o = null;
	    o = (MiningDecision)super.clone();

	    if(trueBranch != null)
	    	o.trueBranch = (MiningSequence)trueBranch.clone();

	    if(falseBranch != null)
	    	o.falseBranch = (MiningSequence)falseBranch.clone();

	    listenersBeforeCondition = (ArrayList<BlockExecuteListener>) listenersBeforeCondition.clone();
	    listenersAfterCondition = (ArrayList<BlockExecuteListener>) listenersAfterCondition.clone();

	    return o;
	}

}