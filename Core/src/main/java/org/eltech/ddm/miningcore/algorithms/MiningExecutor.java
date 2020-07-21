package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Basis class for parallel executer adapter
 *
 * @author Ivan Kholod
 *
 */
public abstract class MiningExecutor implements Cloneable, Serializable {//, Serializable {

	protected MiningBlock block;

	/**
	 * Input data set
	 */
	protected MiningInputStream data;

	protected List<MiningParallel> parallBlocks;

	protected MiningExecutor(MiningBlock block) {
		this.setBlock(block);
	}

	public MiningExecutor(){

	}

	/**
	 * Start execution of parallel branch
	 *
	 * @throws MiningException
	 */
	protected EMiningModel call(EMiningModel model) throws MiningException{
		return getBlock().run(model);
	}


	public abstract void start(EMiningModel model) throws MiningException;

	/**
	 * Waiting finish of handler execution and getting result
	 *
	 * @throws ParallelExecutionException
	 */
	public abstract EMiningModel getModel() throws ParallelExecutionException;

	public Object clone() {
		MiningExecutor o = null;
		try {
			o = (MiningExecutor) super.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return o;
	}

	/**
	 * Executed step of mining algorithm
	 */
	public MiningBlock getBlock() {
		return block;
	}

	public void setBlock(MiningBlock block) {
		this.block = block;
	}

	public void addParallelBlock(MiningParallel mp) {
		if(parallBlocks == null)
			parallBlocks = new ArrayList<>();

		parallBlocks.add(mp);
	}

	public  List<MiningParallel> getParallelBlocks(){
		return parallBlocks;
	}

	public MiningInputStream getData() {
		return data;
	}

	public void setData(MiningInputStream data) {
		this.data = data;
	}
}