package org.eltech.ddm.classification.ruleset;

import org.eltech.ddm.classification.SimplePredicate;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class SimpleRule extends MiningModelElement {

	/**
	 * Predicate of this rule
	 */
	protected SimplePredicate predicate;

	/**
	 * Number of vectors are equaled the predicate
	 */
	protected int numberOfPredicatedVectors;

	/**
	 * Error of this rule
	 */
	protected double error;

	/**
	 * Score (issue) of this rule
	 */
	protected Object score;



	public SimpleRule(SimplePredicate predicate) {
		super(predicate.toString());
		this.predicate = predicate;
	}

	public double getError() {
		return error;
	}

	public SimplePredicate getPredicate() {
		return predicate;
	}

	@Override
	public MiningModelElement createNewCopyElement() {
		return new SimpleRule(predicate);
	}

	@Override
	public String getID() {
		return toString();
	}

	public Object getScore() {
		return score;
	}

	public void setScore(Object result) {
		this.score = result;
	}

	public void setError(double error) {
		this.error = error;
	}

	public int getNumberOfPredicatedVectors() {
		return numberOfPredicatedVectors;
	}

	public void setNumberOfPredicatedVectors(int numberOfPredicatedVectors) {
		this.numberOfPredicatedVectors = numberOfPredicatedVectors;
	}

	@Override
	public void merge(List<MiningModelElement> rules){
		int delta = 0;
		for(MiningModelElement rule: rules) {
			delta = delta + (((SimpleRule)rule).getNumberOfPredicatedVectors() - numberOfPredicatedVectors);
		}
		numberOfPredicatedVectors += delta;
	}


	@Override
	public boolean equals(Object arg0) {
		SimpleRule sr = (SimpleRule) arg0;
		if(arg0 == null)
			return false;

		boolean fPredEq = false;
		if((predicate != null) && (sr.getPredicate() != null))
			fPredEq = predicate.equals(sr.getPredicate());
		else
			fPredEq = (predicate == null) && (sr.getPredicate() == null);

		boolean fScoreEq = false;
		if((score != null) && (sr.getScore() != null))
			fScoreEq = score.equals(sr.getScore());
		else
			fScoreEq = (score == null) && (sr.getScore() == null);

		return fPredEq&&fScoreEq;
	}

	@Override
	protected String propertiesToString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("if [")
				.append(predicate.getAttribute()).append(" = ")
				.append(predicate.getValue()).append("] then ")
				.append(score);
		return stringBuilder.toString();
	}


}
