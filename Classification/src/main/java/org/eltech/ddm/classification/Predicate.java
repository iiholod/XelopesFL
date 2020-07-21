package org.eltech.ddm.classification;


/**
  * It corresponds to the PMML element Predicate. <p>
  *
  * Abstract class for evaluating predicates in decision trees. <p>
  *
  * From PMML. (PMML entity PREDICATES.)
  */
public abstract class Predicate
{
  // -----------------------------------------------------------------------
  //  Variables definitions
  // -----------------------------------------------------------------------

  // -----------------------------------------------------------------------
  //  Constructors
  // -----------------------------------------------------------------------
  /**
   * Empty constructor.
   */
  public Predicate() {
  }


  /**
   * Returns string representation of predicate (few words).
   *
   * @return string representation of predicate
   */
  public String toString() {
    return "abstract predicate";
  }
}
