package org.eltech.ddm.miningcore.miningdata;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.NumericalAttributeProperties;

import java.io.Serializable;

/**
 * CWM Class
 *
 * A NumericalAttributeProperties object is used to describe properties of the numerical
 * attribute.
 * This metadata may or may not be used by the underlying algorithm. It may be
 * leveraged to determine if data being supplied as input to a mining operation is
 * sufficiently similar to the data used to build the model.
 *
 * @author Ivan Holod
 *
 */
public class ENumericalAttributeProperties extends NumericalAttributeProperties  implements Cloneable, Serializable {


	/**
	 * @return the lowerBound
	 */
	public double getLowerBound() {
		return lowerBound;
	}

	/**
	 * @param lowerBound the lowerBound to attributes
	 */
	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	/**
	 * @return the upperBound
	 */
	public double getUpperBound() {
		return upperBound;
	}

	/**
	 * @param upperBound the upperBound to attributes
	 */
	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}

	/**
	 * Returns true if the domain of the numerical attribute is discrete. The default is false.
	 * @return the isDiscrete
	 */
	public boolean isDiscrete() {
		return isDiscrete;
	}

	/**
	 * @param isDiscrete the isDiscrete to attributes
	 */
	public void setDiscrete(boolean isDiscrete) {
		this.isDiscrete = isDiscrete;
	}

	/**
	 * @return the isCyclic
	 */
	public boolean isCyclic() {
		return isCyclic;
	}

	/**
	 * @param isCyclic the isCyclic to attributes
	 */
	public void setCyclic(boolean isCyclic) {
		this.isCyclic = isCyclic;
	}

	/**
	 * @return the anchor
	 */
	public double getAnchor() {
		return anchor;
	}

	/**
	 * @param anchor the anchor to attributes
	 */
	public void setAnchor(double anchor) {
		this.anchor = anchor;
	}

	/**
	 * @return the cycleBegin
	 */
	public double getCycleBegin() {
		return cycleBegin;
	}

	/**
	 * @param cycleBegin the cycleBegin to attributes
	 */
	public void setCycleBegin(double cycleBegin) {
		this.cycleBegin = cycleBegin;
	}

	/**
	 * @return the cycleEnd
	 */
	public double getCycleEnd() {
		return cycleEnd;
	}

	/**
	 * @param cycleEnd the cycleEnd to attributes
	 */
	public void setCycleEnd(double cycleEnd) {
		this.cycleEnd = cycleEnd;
	}

	/**
	 * @return the discreteStepSize
	 */
	public double getDiscreteStepSize() {
		return discreteStepSize;
	}

	/**
	 * @param discreteStepSize the discreteStepSize to attributes
	 */
	public void setDiscreteStepSize(double discreteStepSize) {
		this.discreteStepSize = discreteStepSize;
	}

	public double getEndPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getStartPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object clone() {
		Object o = null;
	    try {
	      o = super.clone();
	    } catch(CloneNotSupportedException e) {
	      System.err.println(this.getClass().toString() + " can't be cloned");
	    }

	    return o;
	}

}