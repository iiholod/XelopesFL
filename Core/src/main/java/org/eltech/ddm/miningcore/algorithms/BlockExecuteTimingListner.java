package org.eltech.ddm.miningcore.algorithms;

import java.io.Serializable;

public class BlockExecuteTimingListner implements BlockExecuteListener, Serializable {
	long start = 0;

	@Override
	public void doEvent(MiningBlock block, EventType e) {
		// TODO Auto-generated method stub
		if(e.equals(EventType.BeforeExecute))
			start =  System.currentTimeMillis();//System.nanoTime();
		else if(e.equals(EventType.AfterExecute)){
			long end = System.currentTimeMillis();//System.nanoTime();
			long timeStep = ( end - start );
			System.out.println("Thread: " + Thread.currentThread().getName() +", MiningBlock: " + block.getClass().getSimpleName() + ", execute time = " +  timeStep + "ms");
		}

	}

	static BlockExecuteTimingListner createListner(){
		return new BlockExecuteTimingListner();
	}


}
