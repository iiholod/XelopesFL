package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class MiningBlock implements Cloneable, Serializable {

	protected EMiningFunctionSettings functionSettings;
	protected EMiningAlgorithmSettings algorithmSettings;

	protected transient ArrayList<BlockExecuteListener> listenersBeforeExecute = new ArrayList<BlockExecuteListener>();
	protected transient ArrayList<BlockExecuteListener> listenersAfterExecute = new ArrayList<BlockExecuteListener>();

	/**
	 * Constructor of algorithm's block
	 * @param settings - settings for build model
	 */
	protected MiningBlock(EMiningFunctionSettings settings) {
		this.functionSettings = settings;
		this.algorithmSettings = functionSettings.getAlgorithmSettings();
    }

    public MiningBlock(){

	}


	/**
	 * Method execute step of algorithm
	 */
	public EMiningModel run(EMiningModel model) throws MiningException{

		this.notifyBeforeExecute();
		EMiningModel result = this.execute(model);
		this.notifyAfterExecute();

		return result;
	}

	/**
	 * Method execute  mining calculator of algorithm
	 */
	protected abstract EMiningModel execute(EMiningModel model) throws MiningException;

	public boolean isDataBlock(){
		return false;
	}

	public EMiningFunctionSettings getFunctionSettings(){
		return functionSettings;
	}

	//-------- Listener methods
	public void addListenerBeforeExecute(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersBeforeExecute);
	}

	public void notifyBeforeExecute(){
		MiningBlock.notifyListeners(this, listenersBeforeExecute, EventType.BeforeExecute );
	}

	public boolean removeListenerBeforeExecute(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersBeforeExecute);
	}

	public void addListenerAfterExecute(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersAfterExecute);
	}

	public void addListenerExecute(BlockExecuteListener listener){
		MiningBlock.addListener(listener, listenersBeforeExecute);
		MiningBlock.addListener(listener, listenersAfterExecute);
	}

	public void notifyAfterExecute(){
		MiningBlock.notifyListeners(this, listenersAfterExecute, EventType.AfterExecute );
	}

	public boolean removeListenerAfterExecute(BlockExecuteListener listener){
		return MiningBlock.removeListener(listener, listenersAfterExecute);
	}

	//--- handy methods to reduce repeated code

	protected static void addListener(
			BlockExecuteListener listener,
			ArrayList<BlockExecuteListener> listenersList){

		if(listenersList == null)
			listenersList = new ArrayList<BlockExecuteListener>();

		listenersList.add(listener);
	}

	protected static void notifyListeners(
			MiningBlock block,
			ArrayList<BlockExecuteListener> listenersList,
			EventType e){
		if( listenersList != null ){
			int l = listenersList.size();
			for (int i = 0; i < l; i++ ) {
				BlockExecuteListener listener = listenersList.get(i);
				listener.doEvent(block, e);
			}
		}
	}

	protected static boolean removeListener(
			BlockExecuteListener listener,
			ArrayList<BlockExecuteListener> listenersList){

		return listenersList != null && listenersList.remove(listener);
	}

	public void removeAllListeners()
	{
		listenersBeforeExecute.clear();
		listenersAfterExecute.clear();

	}

	public ArrayList<BlockExecuteListener> getListenersBeforeExecute()
	{
		return listenersBeforeExecute;
	}

	public ArrayList<BlockExecuteListener> getListenersAfterExecute()
	{
		return listenersAfterExecute;
	}

	public Object clone() {
		Object o = null;
	    try {
	    	o = super.clone();

		    listenersBeforeExecute = (ArrayList<BlockExecuteListener>) listenersBeforeExecute.clone();
		    listenersAfterExecute =  (ArrayList<BlockExecuteListener>) listenersAfterExecute.clone();
	    } catch(CloneNotSupportedException e) {
	      System.err.println(this.getClass().toString() + " can't be cloned");
	    }

	    return o;
	}
}