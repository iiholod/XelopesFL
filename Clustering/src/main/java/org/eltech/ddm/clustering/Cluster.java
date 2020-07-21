package org.eltech.ddm.clustering;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class Cluster extends MiningModelElement  //javax.datamining.clustering.Cluster PmmlPresentable, MiningMatrixElement
{
	private long vectorCount = 0;

	public Cluster(String id){
		super(id);
	}

	/**
	 * Returns the number of cases in the portion of the training data assigned to the cluster
	 * during the model build, inclusive of children counts. This is a non-negative value.
	 * @return
	 */
	public long getVectorCount() {
		return vectorCount;
	}

	public void incVectorCount(){
		vectorCount ++;
	}

	public void setVectorCount(int i) {
		vectorCount = i;
	}
	@Override
	public void merge(List<MiningModelElement> elements) throws MiningException {
		long delta = 0;
		for(MiningModelElement cluster: elements) {
			delta = delta + (((Cluster) cluster).getVectorCount() - getVectorCount());
		}
		vectorCount += delta;
	}

	public Object clone() {
		Cluster o = null;
		o = (Cluster)super.clone();

		o.vectorCount = vectorCount;

		return o;
	}

	@Override
	public String propertiesToString() {
		return ", num="+ vectorCount;
	}


}