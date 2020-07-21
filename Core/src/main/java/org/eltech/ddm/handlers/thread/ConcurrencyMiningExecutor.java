package org.eltech.ddm.handlers.thread;

import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MemoryType;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningExecutor;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Adapter for thread. One adapter is created for one thread
 *
 * @author Alexey Nakidkin
 *
 */
public class ConcurrencyMiningExecutor extends MiningExecutor implements Cloneable {

	ExecutorService service;

	Future future;

	private EMiningModel resultModel;

	/**
	 *  This is  memory type of the system which will be fulfilling the execution of the parallel algorithm
	 */
	protected MemoryType memoryType = MemoryType.distributed;

	ConcurrencyMiningExecutor(MiningBlock block, ExecutorService service) {
		super(block);
		this.service = service;
	}

	@Override
	/**
	 * Start thread
	 */
	public void start (final EMiningModel model) throws MiningException {
		if (block == null)
			throw new ParallelExecutionException("The executor has not a mining block");

		future = service.submit(() -> {
			try {
				resultModel = call(model);
			} catch (MiningException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	/**
	 * Waiting finish of thread execution
	 */
	public EMiningModel getModel() {
			while (!future.isDone()){};
			return resultModel;
	}


	public Object clone() {
		ConcurrencyMiningExecutor o;
		o = (ConcurrencyMiningExecutor) super.clone();
		o.data = data;

		return o;
	}

}
