package org.eltech.ddm.miningcore.algorithms;

public enum EventType {
	BeforeInitLoop,
	AfterInitLoop,
	BeforeCondition,
	AfterCondition,
	BeforePreIteration, 
	AfterPreIteration,
	BeforePostIteration, 
	AfterPostIteration,
	BeforeExecute, 
	AfterExecute,
	BeforeSplit,
	AfterSplit,
	BeforeJoin,
	AfterJoin;
}
