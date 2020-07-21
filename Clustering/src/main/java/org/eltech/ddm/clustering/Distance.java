package org.eltech.ddm.clustering;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.objectmodel.core.Expression;

import java.io.Serializable;

public class Distance extends Expression implements Serializable //implements Cloneable, PmmlPresentable
{
	public static final int MEASURE_TYPE_DISTANCE = 10001;

	public static final int MEASURE_TYPE_SIMILARITY = 10002;

	public static final double SIMILARITY_EPSILON = 1.0E-006D;

	private AggregationFunction type = AggregationFunction.euclidian;

	private int measureType = 10001;

	private AttributeComparisonFunction compareFunction = AttributeComparisonFunction.absDiff;

	private boolean normalized = false;

	private double simMeasNormConst = 1.0D;

	private double minkPar = 2.0D;
	private double[] minAtt;
	private double[] maxAtt;
	private double[] fieldWeights;
//	private double minCompareFunction;
//	private double maxCompareFunction;

	public Distance() {
	}
	
	public Distance(AggregationFunction type) {
		this.type = type;
//		this.compareFunction = compareFunction;
	}

	public Distance(int numbAttributes) {
		this.fieldWeights = new double[numbAttributes];
		for (int i = 0; i < numbAttributes; i++) {
			this.fieldWeights[i] = 1.0D;
		}
		this.minAtt = new double[numbAttributes];
		this.maxAtt = new double[numbAttributes];
	}

	public double distance(MiningVector vec1, MiningVector vec2)
			throws MiningException {
		int numbAtt = vec1.getValues().length;
		double[] weights = new double[numbAtt];

		if (this.fieldWeights == null) {
			for (int i = 0; i < numbAtt; i++) {
				weights[i] = 1.0D;
			}
		}
		else
			weights = this.fieldWeights;

		double dist = 0.0D;

		switch (this.type) {
		case euclidian:
			for (int i = 0; i < numbAtt; i++) {
				double diff = AttDist(i, vec1.getValue(i), vec2.getValue(i));
				dist += weights[i] * diff * diff;
			}

			dist = Math.sqrt(dist);

			if (this.measureType != 10002)
				break;
			dist = 1.0D / (1.0D + dist / this.simMeasNormConst);

			break;
		case squaredEuclidian:
			for (int i = 0; i < numbAtt; i++) {
				double diff = AttDist(i, vec1.getValue(i),	vec2.getValue(i));

				dist += weights[i] * diff * diff;
			}

			if (this.measureType != 10002)
				break;
			dist = 1.0D / (1.0D + dist / this.simMeasNormConst);
			break;
		case chebychev:
			for (int i = 0; i < numbAtt; i++) {
				double diff = AttDist(i, vec1.getValue(i),vec2.getValue(i));

				diff = weights[i] * diff;
				if (diff > dist) {
					dist = diff;
				}
			}
			if (this.measureType != 10002)
				break;
			dist = 1.0D / (1.0D + dist / this.simMeasNormConst);
			break;
		case cityBlock:
			for (int i = 0; i < numbAtt; i++) {
				double diff = AttDist(i, vec1.getValue(i),vec2.getValue(i));

				dist += weights[i] * diff;
			}

			if (this.measureType != 10002)
				break;
			dist = 1.0D / (1.0D + dist / this.simMeasNormConst);
			break;
		case minkovski:
			for (int i = 0; i < numbAtt; i++) {
				double diff = Math.abs(vec1.getValue(i) - vec2.getValue(i));
				dist += weights[i] * Math.pow(diff, this.minkPar);
			}
			dist = Math.pow(dist, 1.0D / this.minkPar);

			if (this.measureType != 10002)
				break;
			dist = 1.0D / (1.0D + dist / this.simMeasNormConst);
			break;
		case simpleMatching: {
			double[] counts = getDistCounts(vec1, vec2);
			dist = (counts[0] + counts[3])
					/ (counts[0] + counts[1] + counts[2] + counts[3]);
		}

			break;
		case jaccard: {
			double[] counts = getDistCounts(vec1, vec2);
			dist = counts[0] / (counts[0] + counts[1] + counts[2]);
		}
			break;
		case tanimoto: {
			double[] counts = getDistCounts(vec1, vec2);
			dist = (counts[0] + counts[3])
					/ (counts[0] + 2.0D * (counts[1] + counts[2]) + counts[3]);
		}
			break;
		case binarySimilarity:
			throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Binary similarity not supported");
		default:
			throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Unknown distance type specified.");
		}
//		double[] counts;
		return dist;
	}

	private double AttDist(int index, double p1, double p2) throws MiningException {
		double dist = 0.0D;

		switch (this.compareFunction) {
		case absDiff:
			dist = Math.abs(normValue(index, p1) - normValue(index, p2));
			break;

		case gaussSim:
			dist = (0.0D / 0.0D);
			break;

		case delta:
			if (Math.abs(p1 - p2) < 1.0E-006D)
				dist = 0.0D;
			else {
				dist = 1.0D;
			}
			break;
		
		case equal:
			if (Math.abs(p1 - p2) < 1.0E-006D)
				dist = 1.0D;
			else {
				dist = 0.0D;
			}
			break;
		
		case table:
			dist = (0.0D / 0.0D);
			break;

		default:
			throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA, "Unknown comparison function specified.");
		}

		return dist;
	}

	private double normValue(int index, double value) throws MiningException {
		double normValue = value;

		if (this.normalized) {
			if ((this.minAtt == null) || (this.maxAtt == null)) {
				throw new MiningException(MiningErrorCode.INVALID_INPUT_DATA,
						"Normalization required but min/maxAtt not specified.");
			}
			if ((Double.isNaN(this.minAtt[index]))
					|| (Math.abs(this.maxAtt[index] - this.minAtt[index]) < 1.0E-006D)) {
				normValue = 0.0D;
			} else
				normValue = (value - this.minAtt[index])
						/ (this.maxAtt[index] - this.minAtt[index]);
		}

		return normValue;
	}

	private double[] getDistCounts(MiningVector vec1, MiningVector vec2) {
		double[] counts = new double[4];
		for (int i = 0; i < vec1.getValues().length; i++) {
			boolean vec1Is1 = Math.abs(vec1.getValue(i) - 1.0D) < 1.0E-006D;
			boolean vec1Is0 = Math.abs(vec1.getValue(i) - 0.0D) < 1.0E-006D;
			boolean vec2Is1 = Math.abs(vec2.getValue(i) - 1.0D) < 1.0E-006D;
			boolean vec2Is0 = Math.abs(vec2.getValue(i) - 0.0D) < 1.0E-006D;
			if ((vec1Is1) && (vec2Is1))
				counts[0] += 1;
			if ((vec1Is1) && (vec2Is0))
				counts[1] += 1;
			if ((vec1Is0) && (vec2Is1))
				counts[2] += 1;
			if ((vec1Is0) && (vec2Is0)) {
				counts[3] += 1;
			}
		}
		return counts;
	}
/*
	public Object createPmmlObject() throws MiningException {
		ComparisonMeasure cm = new ComparisonMeasure();

		if (this.measureType == 10001)
			cm.setKind("distance");
		else {
			cm.setKind("similarity");
		}

		switch (this.compareFunction) {
		case 101:
			cm.setCompareFunction("absDiff");
			break;
		case 102:
			cm.setCompareFunction("gaussSim");
			break;
		case 103:
			cm.setCompareFunction("delta");
			break;
		case 104:
			cm.setCompareFunction("equal");
			break;
		case 105:
			cm.setCompareFunction("table");
			break;
		default:
			throw new MiningException("Unknown comparison function specified.");
		}
		cm.setMinimum(String.valueOf(this.minCompareFunction));
		cm.setMaximum(String.valueOf(this.maxCompareFunction));

		switch (this.type) {
		case 1:
			Euclidean euclidean = new Euclidean();

			cm.setEuclidean(euclidean);
			break;
		case 2:
			SquaredEuclidean sqEuclidean = new SquaredEuclidean();

			cm.setSquaredEuclidean(sqEuclidean);
			break;
		case 3:
			Chebychev chebychev = new Chebychev();

			cm.setChebychev(chebychev);
			break;
		case 4:
			CityBlock cityBlock = new CityBlock();

			cm.setCityBlock(cityBlock);
			break;
		case 5:
			Minkowski minkowski = new Minkowski();

			minkowski.setPParameter(String.valueOf(this.minkPar));
			cm.setMinkowski(minkowski);
			break;
		case 6:
			SimpleMatching simpleMatching = new SimpleMatching();

			cm.setSimpleMatching(simpleMatching);
			break;
		case 7:
			Jaccard jaccard = new Jaccard();

			cm.setJaccard(jaccard);
			break;
		case 8:
			Tanimoto tanimoto = new Tanimoto();

			cm.setTanimoto(tanimoto);
			break;
		case 9:
			BinarySimilarity binSimilarity = new BinarySimilarity();

			cm.setBinarySimilarity(binSimilarity);
			break;
		default:
			throw new MiningException("Unknown distance type specified.");
		}
		BinarySimilarity binSimilarity;
		return cm;
	}

	public void parsePmmlObject(Object pmmlObject) throws MiningException {
		ComparisonMeasure cm = (ComparisonMeasure) pmmlObject;

		if (cm.getKind().equals("distance"))
			this.measureType = 10001;
		if (cm.getKind().equals("similarity")) {
			this.measureType = 10002;
		}

		if (cm.getCompareFunction().equals("absDiff"))
			this.compareFunction = 101;
		if (cm.getCompareFunction().equals("gaussSime"))
			this.compareFunction = 102;
		if (cm.getCompareFunction().equals("delta"))
			this.compareFunction = 103;
		if (cm.getCompareFunction().equals("equal"))
			this.compareFunction = 104;
		if (cm.getCompareFunction().equals("table"))
			this.compareFunction = 105;
		this.minCompareFunction = Double.parseDouble(cm.getMinimum());
		this.maxCompareFunction = Double.parseDouble(cm.getMaximum());

		if (cm.getEuclidean() != null)
			this.type = 1;
		if (cm.getSquaredEuclidean() != null)
			this.type = 2;
		if (cm.getChebychev() != null)
			this.type = 3;
		if (cm.getCityBlock() != null)
			this.type = 4;
		Minkowski minkowski;
		if (cm.getMinkowski() != null) {
			this.type = 5;
			minkowski = cm.getMinkowski();
			this.minkPar = Double.parseDouble(minkowski.getPParameter());
		}
		if (cm.getSimpleMatching() != null)
			this.type = 6;
		if (cm.getJaccard() != null)
			this.type = 7;
		if (cm.getTanimoto() != null)
			this.type = 8;
		if (cm.getBinarySimilarity() != null) {
			this.type = 9;
			minkowski = cm.getBinarySimilarity();
		}
	}
*/
	public void setType(AggregationFunction type) {
		this.type = type;
	}

	public AggregationFunction getType() {
		return this.type;
	}

	public void setCompareFunction(AttributeComparisonFunction compareFunction) {
		this.compareFunction = compareFunction;
	}

	public AttributeComparisonFunction getCompareFunction() {
		return this.compareFunction;
	}

	public void setMeasureType(int measureType) {
		this.measureType = measureType;
	}

	public int getMeasureType() {
		return this.measureType;
	}

	public void setFieldWeights(double[] fieldWeights) {
		this.fieldWeights = fieldWeights;
	}

	public double[] getFieldWeights() {
		return this.fieldWeights;
	}

	public void setNormalized(boolean normalized) {
		this.normalized = normalized;
	}

	public boolean isNormalized() {
		return this.normalized;
	}

//	public void setMinCompareFunction(double minCompareFunction) {
//		this.minCompareFunction = minCompareFunction;
//	}
//
//	public double getMinCompareFunction() {
//		return this.minCompareFunction;
//	}
//
//	public void setMaxCompareFunction(double maxCompareFunction) {
//		this.maxCompareFunction = maxCompareFunction;
//	}
//
//	public double getMaxCompareFunction() {
//		return this.maxCompareFunction;
//	}

	public void setMinkPar(double minkPar) {
		this.minkPar = minkPar;
	}

	public double getMinkPar() {
		return this.minkPar;
	}

	public void setMinAtt(double[] minAtt) {
		this.minAtt = minAtt;
	}

	public double[] getMinAtt() {
		return this.minAtt;
	}

	public void setMaxAtt(double[] maxAtt) {
		this.maxAtt = maxAtt;
	}

	public double[] getMaxAtt() {
		return this.maxAtt;
	}

	public double getSimMeasNormConst() {
		return this.simMeasNormConst;
	}

	public void setSimMeasNormConst(double simMeasNormConst) {
		this.simMeasNormConst = simMeasNormConst;
	}
/*	
	public Object clone() {
		Distance o = null;
		o = (Distance) super.clone();

		if(minAtt != null)
			o.minAtt = (double[])minAtt.clone();
		
		if(maxAtt != null)
			o.maxAtt = (double[])maxAtt.clone();
		
		if(fieldWeights != null)
			o.fieldWeights = (double[])fieldWeights.clone();
		
		return o;
	}
*/

	public int getNumberOfAttributes() {
		// TODO Auto-generated method stub
		return this.minAtt.length;
	}
}
