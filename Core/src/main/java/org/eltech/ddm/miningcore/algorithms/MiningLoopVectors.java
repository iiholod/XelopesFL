package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class MiningLoopVectors extends MiningLoop {

	// Number of  mining model's elements are handled by the loop
	private int startPositon;

	public int getStartPositon() {
		return startPositon;
	}

	public void setStartPositon(int startPositon) {
		this.startPositon = startPositon;
	}

	public int getCountElement() {
		return countElement;
	}

	public void setCountElement(int countElement) {
		this.countElement = countElement;
	}

	// Number of  mining model's elements are handled by the loop
	private int countElement;

    public MiningLoopVectors(EMiningFunctionSettings settings, MiningBlock... blocks) throws MiningException {
		super(settings);
		startPositon = 0;
		countElement = -1;
		iteration = new MiningSequence(settings, blocks);
	}

	/**
     * Constructor for distributed execution of the loop
	 * @param settings - function mining settings
	 * @param startPos - start position of the loop
	 * @param countElement - count of mining model's elements wich are handled by the loop
	 * @param block - iteration block
	 * @throws MiningException
	 */
	public MiningLoopVectors(EMiningFunctionSettings settings, int startPos, int countElement, MiningSequence block) throws MiningException {
		super(settings);
		startPositon = startPos;
		this.countElement = countElement;
		iteration = block;
	}

	public MiningLoopVectors() {
	}

	@Override
	protected EMiningModel initLoop(EMiningModel model) throws MiningException {
		model.setCurrentVector(startPositon);
    	return model;
	}

	@Override
	protected boolean conditionLoop(EMiningModel model) throws MiningException {
		if(countElement < 0)
    		throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA);
			//return (model.getCurrentVectorIndex() < data.getVectorsNumber());
		else {
			return ((model.getCurrentVectorIndex() - startPositon) < countElement);
		}
	}

	@Override
	protected EMiningModel beforeIteration(EMiningModel model) throws MiningException {
		return model;
	}

	@Override
	protected EMiningModel afterIteration(EMiningModel model) throws MiningException {
		model.setCurrentVector(model.getCurrentVectorIndex() + 1);
		return model;
	}

	@Override
	public boolean isDataBlock() {
		return true;
	}
}
