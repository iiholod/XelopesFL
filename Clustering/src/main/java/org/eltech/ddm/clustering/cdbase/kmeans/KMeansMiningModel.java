package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.clustering.cdbase.CDBasedClusteringMiningModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


public class KMeansMiningModel extends CDBasedClusteringMiningModel{

	static final int CLUSTER_VECTOR = 2;
	static final int[] INDEX_CLUSTER_VECTOR = {CLUSTER_VECTOR};

	public KMeansMiningModel(EMiningFunctionSettings settings) throws MiningException {
		super(settings);
		sets.add(CLUSTER_VECTOR, new VectrosDistribution("Vectors distribution")) ;
	}



	@Override
	public Object clone() {
		KMeansMiningModel o = null;
		o = (KMeansMiningModel)super.clone();

		return o;
	}

    public VectorAssignment getVectorAssigment(int iCurrentVector) throws MiningException {
		return (VectorAssignment)getElement( EMiningModel.index(KMeansMiningModel.CLUSTER_VECTOR, iCurrentVector));
	}

	public void addVectorAssigment(VectorAssignment vec) throws MiningException {
		addElement( index(CLUSTER_VECTOR), vec);
	}

	public VectorCluster getVectorsCluster(int iCurrentVector, int iCurrentCluster) throws MiningException {
		return (VectorCluster)getElement(index(CLUSTER_VECTOR, iCurrentVector, iCurrentCluster));

	}

	public void addVectorsCluster(int iCurrentVector, VectorCluster vc) throws MiningException {
		addElement( index(CLUSTER_VECTOR, iCurrentVector), vc);
	}
}