package org.eltech.ddm.classification;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.Operator;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;



/**
  * It corresponds to the PMML element SimplePredicate. <p>
  *
  * Extends Predicate for evaluating an attribute value
  * in form of a simple boolean expression. <p>
  *
  * From PMML. <p>
  *
  * Superclasses:
  * <ul>
  *   <li> Predicate
  * </ul>
  */
public class SimplePredicate extends Predicate implements Cloneable{

   // -----------------------------------------------------------------------
  //  Variables definitions
  // -----------------------------------------------------------------------
  /** Attribute to evaluate. */
  //protected ELogicalAttribute attribute;

  /** Name of the evaluation attribute. */
  protected final String attribute;

  /** Value to compare with. */
  protected final double value;

  /** Simple predicate operator. */
  protected final Operator op;

  // -----------------------------------------------------------------------
  //  Constructors
  // -----------------------------------------------------------------------
  /**
   * Simple predicate with given condition.
   *
   * @param attribute attribute of attribute to be evaluated
   * @param value attribute's value for evaluation
   * @param op evaluation operator
   */
  public SimplePredicate(String attribute, double value, Operator op) {

    this.attribute = attribute;
    this.value     = value;
    this.op        = op;
  }

  // -----------------------------------------------------------------------
  //  Getter and setter methods
  // -----------------------------------------------------------------------
  /**
   * Returns evaluation attribute.
   *
   * @return evaluation attribute
   */
  public String getAttribute() {
    return attribute;
  }

  /**
   * Sets evaluation attribute.
   *
   * @param attribute new evaluation attribute
   */
//  public void setAttribute(ELogicalAttribute attribute) {
//    this.attribute = attribute;
//    this.attribute      = attribute.getName();
//  }



   /**
   * Returns evaluation value.
   *
   * @return evaluation value
   */
  public double getValue() {
    return value;
  }

  /**
   * Sets evaluation value.
   *
   * @param value new evaluation value
   */
//  public void setValue(double value) {
//    this.value = value;
//  }

  /**
   * Returns evaluation operator.
   *
   * @return evaluation operator
   */
  public Operator getOperator() {
    return op;
  }

  /**
   * Sets operator.
   *
   * @param op new operator
   */
//  public void setOperator(Operator op) {
//    this.op = op;
//  }

  // -----------------------------------------------------------------------
  //  Evaluate predicate
  // -----------------------------------------------------------------------
  /**
   * Predicate evaluation of given mining vector.
   *
   * @param miningVector vector for evaluation
   * @return predicate evaluation result
   * @exception MiningException can't evaluate mining vector
   */

  /**
   * Create string representation of simple predicate.
   *
   * @return string based on PMML representation of predicate
   */
  public String toString() {

    StringBuffer sb = new StringBuffer(attribute);
    switch(op)
    {
      case EQUAL: sb.append(" equals "); break;
      case NOT_EQUAL: sb.append(" not equals "); break;
      case LESS_THAN: sb.append(" less "); break;
      case LESS_OR_EQUAL: sb.append(" less or equals "); break;
      case GREATER_THAN: sb.append(" greater "); break;
      case GREATER_OR_EQUAL: sb.append(" greater or equals "); break;
      case IS_MISSING: sb.append(" is missing "); break;
      case IS_NOT_MISSING: sb.append(" is not missing "); break;

      default: sb.append(" unknown operator ");
    }
    sb.append(value);
    return sb.toString();
  }

  @Override
	public boolean equals(Object arg) {
	  SimplePredicate predicate = (SimplePredicate)arg;
	  if(this.attribute.equals(predicate.attribute) &&
	    this.value== predicate.value &&
	    (this.op == predicate.op))
	    return true;

	  return false;
	}

  @Override
	public int hashCode() {
		return this.attribute.hashCode() + new Double(this.value).hashCode() + this.op.hashCode();
	}

    /**
     * Predicate evaluation of given mining vector.
     *
     * @param miningVector vector for evaluation
     * @return predicate evaluation result
     * @exception MiningException can't evaluate mining vector
     */
    public boolean evaluate(MiningVector miningVector) throws MiningException {
		for(int i=0; i<miningVector.getLogicalData().getAttributesNumber(); i++){
			ELogicalAttribute attr = miningVector.getLogicalData().getAttribute(i);
			if(attr.getName().equals(attribute)){
				if(miningVector.getValueCategory(i).getValue().equals(value))
					return true;
				else
					return false;
			}
		}

		return false;
  	}


 /* public boolean evaluate(MiningVector miningVector) throws MiningException {

    MiningFunctionSettings metaData = miningVector.getMetaData();
    if (metaData == null)
      throw new MiningException("MiningVector has no meta data");
    MiningAttribute ma = metaData.getMiningAttribute(attribute);
    if (ma == null)
      throw new MiningException("MiningAttribute "+attribute+" not found in vector");
    double v = miningVector.getValue(ma);
    if ( Category.isMissingValue(v) )
      throw new MiningException("MiningAttribute "+attribute+" is missing value");

    if (ma instanceof LogicalAttribute)
    {
      if (op != SimplePredicate.EQUAL && op != SimplePredicate.NOT_EQUAL)
        throw new MiningException("SimplePredicate: categorical attribute - equal operators are supported only");
      Category c = ((LogicalAttribute)ma).getCategoricalProperties().getCategory((int) v);
      if (c == null)
        throw new MiningException("SimplePredicate: unknown category");
      return c.getValue().toString().equals(value)?(op == EQUAL?true:false):(op == EQUAL?false:true);
    }
    else if (ma instanceof LogicalAttribute) //may be mistake (NumericalAttribute)
    {
      double d = Double.parseDouble(value);
      switch(op)
      {
        case EQUAL: return d == v;
        case NOT_EQUAL: return d != v;
        case LESS: return v < d;
        case GREATER: return v > d;
        case LESS_OR_EQUAL: return v <= d;
        case GREATER_OR_EQUAL: return v >= d;
        case IS_MISSING: return Category.isMissingValue(v);
        case IS_NOT_MISSING: return !Category.isMissingValue(v);
        default: throw new MiningException("SimplePredicate: unknown operator");
      }
    }
    else throw new MiningException("SimplePredicate: unknown attribute type");
  } */

	public Object clone() {
		SimplePredicate o = new SimplePredicate(attribute, value, op);

		return o;
	}
}