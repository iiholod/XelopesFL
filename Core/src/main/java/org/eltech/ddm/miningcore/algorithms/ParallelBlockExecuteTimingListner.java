package org.eltech.ddm.miningcore.algorithms;

public class ParallelBlockExecuteTimingListner extends BlockExecuteTimingListner {
	
	long split = 0;
	long join = 0;

	@Override
	public void doEvent(MiningBlock block, EventType e) {
		super.doEvent(block, e);
		
		switch (e) {
		case BeforeSplit:
			split = System.nanoTime();
			break;
		case AfterSplit:
			long end = System.nanoTime();
			long timeSplit = ( end - split );
			System.out.println("Thread: " + Thread.currentThread().getName() +", MiningBlock: " + block.getClass().getSimpleName() + ", split time = " +  timeSplit + "ns");
			break;
		case BeforeJoin:
			join = System.nanoTime();
			break;
		case AfterJoin:
			 end = System.nanoTime();
			long timeJoin = ( end - join );
			System.out.println("Thread: " + Thread.currentThread().getName() +", MiningBlock: " + block.getClass().getSimpleName() + ", union time = " +  timeJoin + "ns");
			break;	
			
		default:
			break;
		}
		


	}

	static BlockExecuteTimingListner createListner(){
		return new ParallelBlockExecuteTimingListner();
	}

}
