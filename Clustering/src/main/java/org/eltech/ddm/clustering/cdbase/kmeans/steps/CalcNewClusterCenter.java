package org.eltech.ddm.clustering.cdbase.kmeans.steps;

import com.opencsv.exceptions.CsvException;
import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.Coordinate;
import org.eltech.ddm.clustering.cdbase.kmeans.KMeansMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.VectorAssignment;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.IOException;

public class CalcNewClusterCenter extends DataMiningBlock {


	public CalcNewClusterCenter(EMiningFunctionSettings settings)
			throws MiningException {
		super(settings);

	}

	@Override
	protected EMiningModel execute(MiningInputStream data, EMiningModel model) throws MiningException {
		int iAttr = model.getCurrentAttributeIndex();
		int iVec = model.getCurrentVectorIndex();
		MiningVector mv = data.getVector(iVec);
		VectorAssignment v = ((KMeansMiningModel)model).getVectorAssigment(iVec);
		int iCloseCluster = v.getIndexCluster();
		Coordinate cc = ((ClusteringMiningModel)model).getClusterCenterCoordinate (iCloseCluster, iAttr);

		double val = mv.getValue(iAttr);

		cc.addMass(val);
		//System.out.println("Thread-" + Thread.currentThread().getName() + " cc  " + cc);

		return model;
	}

}