package org.eltech.ddm.classification.naivebayes.category;

import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class TargetValueCount extends MiningModelElement {

	private int count = 0;

	public TargetValueCount(String id) {
		super(id);
	}

	@Override
	public String propertiesToString() {
		return ",count=" + count;
	}

	@Override
	public void merge(List<MiningModelElement> valueCounts){
		int delta = 0;
		for(MiningModelElement vCount: valueCounts) {
			delta = delta + (((TargetValueCount)vCount).count - count);
		}
		count += delta;
	}

	public synchronized int getCount() {
		return count;
	}

	public synchronized void incCount() {
		this.count++;
	}


}
