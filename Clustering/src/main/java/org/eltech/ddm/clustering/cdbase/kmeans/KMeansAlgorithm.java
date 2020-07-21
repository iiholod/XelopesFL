

package org.eltech.ddm.clustering.cdbase.kmeans;

import org.eltech.ddm.clustering.ClusteringMiningModel;
import org.eltech.ddm.clustering.cdbase.kmeans.steps.*;
import org.eltech.ddm.clustering.cdbase.steps.InitClusterByVectors;
import org.eltech.ddm.clustering.cdbase.steps.SetCentroidOfCluster;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.ReportType;
import org.eltech.ddm.miningcore.VerificationReport;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;


/**
 * Implementation of a simple K-Means clustering algorithm.
 */
public class KMeansAlgorithm extends MiningAlgorithm
{
  public KMeansAlgorithm(EMiningFunctionSettings miningSettings)
			throws MiningException {
		super(miningSettings);
		// TODO Auto-generated constructor stub
	}

  /**
   * Checks mining algorithm for completeness by calling verify method
   * of superclass. Adiitionally, it checks whether numberOfClusters and
   * maxNumberOfIterations are admissible.
   *
   * @throws IllegalArgumentException if some algorithm attributes are incorrect
   */

  public VerificationReport verify() {
	  //super.verify();
	  String report = "";

	  if(report.length() > 0)
		  return new VerificationReport(ReportType.error, report);
	  else
		  return null;
	  }

  /**
   * Initialization of kmeans algorithm's steps.
   *
   */

	@Override
	public EMiningModel createModel() throws MiningException {
		EMiningModel resultModel = new KMeansMiningModel(miningSettings);

		return resultModel;
	}

	@Override
	public MiningSequence getSequenceAlgorithm() throws MiningException {
		MiningSequence blocks = new MiningSequence(miningSettings,
		  		new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
						new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
								//new InitClusterByRandom(miningSettings))),
								new InitClusterByVectors(miningSettings))),
				new WhileChangClustersLoop(miningSettings,
						new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
								new InitCluster(miningSettings)),
						new MiningLoopVectors(miningSettings,
								new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
										new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
												new AccumulateDistanceVectorCluster(miningSettings)),
										new DistanceVectorCluster(miningSettings),
										new ClosestClusterForVector(miningSettings)),
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
										new CalcNewClusterCenter(miningSettings)),
								new IncVectorCountInCluster(miningSettings)),
						new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
										new SetCentroidOfCluster(miningSettings))))
		);

		blocks.addListenerExecute(new BlockExecuteTimingListner());


		return blocks;
	}

	@Override
	public MiningSequence getCentralizedParallelAlgorithm() throws MiningException {
		return getHorDistributedAlgorithm();
	}

	@Override
	public MiningSequence getHorDistributedAlgorithm() throws MiningException {
		MiningSequence blocks = new MiningSequence(miningSettings,
				new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
						new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
								//new InitClusterByRandom(miningSettings))),
								new InitClusterByVectors(miningSettings))),
				new WhileChangClustersLoop(miningSettings,
						new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
								new InitCluster(miningSettings)),
						new MiningParallel(miningSettings, MemoryType.shared,
								new MiningLoopVectors(miningSettings,
										new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
												new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
														new AccumulateDistanceVectorCluster(miningSettings)),
												new DistanceVectorCluster(miningSettings),
												new ClosestClusterForVector(miningSettings)),
										new MiningParallel(miningSettings, MemoryType.shared,
												new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
														new CalcNewClusterCenter(miningSettings)),
												new IncVectorCountInCluster(miningSettings)))),
						new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
										new SetCentroidOfCluster(miningSettings))))
		);

		blocks.addListenerExecute(new BlockExecuteTimingListner());


		return blocks;
	}

	@Override
	public MiningSequence getVerDistributedAlgorithm() throws MiningException {
		MiningSequence blocks = new MiningSequence(miningSettings,
				new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
						new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
								//new InitClusterByRandom(miningSettings))),
								new InitClusterByVectors(miningSettings))),
				new WhileChangClustersLoop(miningSettings,
						new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
								new InitCluster(miningSettings)),
						new MiningParallel(miningSettings, MemoryType.distributed,
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
										new MiningParallel(miningSettings, MemoryType.shared,
												new MiningLoopVectors(miningSettings,
														new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
																new AccumulateDistanceVectorCluster(miningSettings)))))),
						new MiningLoopVectors(miningSettings,
								new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
										new DistanceVectorCluster(miningSettings),
										new ClosestClusterForVector(miningSettings))),
						new MiningParallel(miningSettings, MemoryType.distributed,
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
										new MiningLoopVectors(miningSettings,
												new CalcNewClusterCenter(miningSettings)))),
						new MiningParallel(miningSettings, MemoryType.shared,
								new MiningLoopVectors(miningSettings,
										new IncVectorCountInCluster(miningSettings))),
						new MiningLoopElement(miningSettings, ClusteringMiningModel.INDEX_CLUSTERS,
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
										new SetCentroidOfCluster(miningSettings))))
		);

		blocks.addListenerExecute(new BlockExecuteTimingListner());

		return blocks;
	}


}
