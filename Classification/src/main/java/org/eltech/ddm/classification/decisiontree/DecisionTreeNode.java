package org.eltech.ddm.classification.decisiontree;

import org.eltech.ddm.classification.Predicate;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategory;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.IClassifier;

/**
  * Node of a decision tree. <p>
  *
  * From PDM CWM extension. <p>
  *
  * Superclasses:
  * <ul>
  *   <li> MiningTreeNode
  * </ul>
  *
  * In addition, functionality from PMML was added.
  * It corresponds to the PMML element Node.
  *
  * @see DecisionTreeMiningModel
  */
public class DecisionTreeNode extends MiningTreeNode implements IClassifier
{
    // -----------------------------------------------------------------------
    //  Variables definitions
    // -----------------------------------------------------------------------

    /** Meta data. */
    protected EMiningFunctionSettings metaData;

    /** Target attribute. */
    protected ELogicalAttribute target;

    /** Predicate for evaluation. */
    protected Predicate predicate;

    /** Score of node. */
    protected double score = Double.NaN;

    /** Class distribution of node, if known. */
    protected double[] distribution;

    /** Record count of the node. Only used if no distribution available. */
    protected int recordCount = -1;

    /** Class distribution of evaluation attributes. */
    protected double[] distributionEval;

    /** Assign distribution category names to distribution classes. */
    protected String[] distribCategNames;

    /** Cumulated counts. */
    protected double[] cumulatedRecordCountThis;

    /** Cumulated counts. */
    protected double[] cumulatedRecordCountOther;

    /** Total counts. */
    protected double[] totalRecordCountThis;

    /** Total counts. */
    protected double[] totalRecordCountOther;

    // -----------------------------------------------------------------------
    //  Getter and setter methods
    // -----------------------------------------------------------------------

    /**
     * Returns predicate.
     *
     * @return predicate
     */
    public Predicate getPredicate()
    {
      return predicate;
    }

    /**
     * Sets new predicate.
     *
     * @param predicate new predicate
     */
    public void setPredicate(Predicate predicate)
    {
      this.predicate = predicate;
    }

    /**
     * Returns meta data.
     *
     * @return meta data
     */
    public EMiningFunctionSettings getMetaData()
    {
        return metaData;
    }

    /**
     * Sets meta data.
     *
     * @param metaData meta data to attributes
     */
    public void setMetaData(EMiningFunctionSettings metaData)
    {
        this.metaData = metaData;
    }

    /**
     * Returns target attribute.
     *
     * @return target attribute
     */
    public ELogicalAttribute getTarget()
    {
      return target;
    }

    /**
     * Sets target attribute.
     *
     * @param target new target attribute
     */
    public void setTarget(ELogicalAttribute target)
    {
      this.target = target;
    }

    /**
     * Returns score value.
     *
     * @return score value
     */
    public double getScore()
    {
      return score;
    }

    /**
     * Sets new score value.
     *
     * @param score new score value
     */
    public void setScore(double score)
    {
      this.score = score;
    }


    /**
     * Does the node store a distribution?
     *
     * @return true if node has distribution, false otherwise
     */
    public boolean hasDistribution() {

        return distribution != null && distribution.length != 0;
    }

    /**
     * Returns class distribution of node.
     *
     * @return class distribution of node
     */
    public double[] getDistribution()
    {
      return distribution;
    }

    /**
     * Sets class distribution of node.
     *
     * @param distribution new class distribution of node
     */
    public void setDistribution(double[] distribution)
    {
      this.distribution = distribution;
    }

    /**
     * Returns evaluation class distribution of node.
     *
     * @return evaluation class distribution of node
     */
    public double[] getDistributionEval()
    {
      return distributionEval;
    }

    /**
     * Sets class evaluation distribution of node.
     *
     * @param distributionEval new evaluation class distribution of node
     */
    public void setDistributionEval(double[] distributionEval)
    {
      this.distributionEval = distributionEval;
    }


    /**
     * Returns the category names of distribution indexes.
     *
     * @return category names of distribution indexes
     */
    public String[] getDistribCategNames()
    {
      return distribCategNames;
    }

    /**
     * Sets category names to distribution indexes.
     *
     * @param distribCategNames new category names of distribution indexes
     */
    public void setDistribCategNames(String[] distribCategNames)
    {
      this.distribCategNames = distribCategNames;
    }

    /**
     * Does the node store the record count?
     *
     * @return true if node has record count, false otherwise
     */
    public boolean hasRecordCount() {

        return hasDistribution() || recordCount != -1;
    }

    /**
     * Gets node record count. Test for valid record count by first
     * calling the hasRecordCount method.
     *
     * @return recordCount, 0 if no record count defined
     */
    public double getRecordCount() {

      if ( !hasDistribution() ) {
        if (recordCount > -1)
          return recordCount;
        else
          return 0.0;
      }

      double recordCount = 0.0;
      for (int i=0; i < distribution.length; i++)
        recordCount += distribution[i];

      return recordCount;
    }

    /**
     * Get node cumulated record count.
     *
     * @return distribution
     */
    public double[] getCumulatedRecordCountThis()
    {
      return cumulatedRecordCountThis;
    }

    /**
     * Set node cumulated record count.
     *
     * @param cumulatedRecordCountThis distribution
     */
    public void setCumulatedRecordCountThis(double[] cumulatedRecordCountThis)
    {
      this.cumulatedRecordCountThis = cumulatedRecordCountThis;
    }

    /**
     * Get node cumulated record count.
     *
     * @return distribution
     */
    public double[] getCumulatedRecordCountOther()
    {
      return cumulatedRecordCountOther;
    }

    /**
     * Set node cumulated record count.
     *
     * @param cumulatedRecordCountOther distribution
     */
    public void setCumulatedRecordCountOther(double[] cumulatedRecordCountOther)
    {
      this.cumulatedRecordCountOther = cumulatedRecordCountOther;
    }

    /**
     * Get node total record count.
     *
     * @return distribution
     */
    public double[] getTotalRecordCountThis()
    {
      return totalRecordCountThis;
    }

    /**
     * Set node total record count.
     *
     * @param totalRecordCountThis distribution
     */
    public void setTotalRecordCountThis(double[] totalRecordCountThis)
    {
      this.totalRecordCountThis = totalRecordCountThis;
    }

    /**
     * Get node total record count.
     *
     * @return distribution
     */
    public double[] getTotalRecordCountOther()
    {
      return totalRecordCountOther;
    }

    /**
     * Set node total record count.
     *
     * @param totalRecordCountOther distribution
     */
    public void setTotalRecordCountOther(double[] totalRecordCountOther)
    {
      this.totalRecordCountOther = totalRecordCountOther;
    }

    /**
     *
     * Get node cumulated record count for target.
     *
     * @param target index of target
     * @return count
     */
    public double getCumulatedRecordCountThis(double target)
    {
      return cumulatedRecordCountThis[(int)target];
    }

    /**
     * Get node cumulated record count for not target
     *
     * @param target index of target
     * @return count
     */
    double getCumulatedRecordCountOther(double target)
    {
      return cumulatedRecordCountOther[(int)target];
    }

    /**
     *
     * Set node cumulated record counts.
     *
     * @param target index of target
     * @param countThis cumulated count for target
     * @param countOther cumulated count for not target
     */
    public void setCumulatedRecordCounts(double target, double countThis, double countOther)
    {
      cumulatedRecordCountThis[(int)target]  = countThis;
      cumulatedRecordCountOther[(int)target] = countOther;
    }

    // -----------------------------------------------------------------------
    //  Apply tree node to mining vector
    // -----------------------------------------------------------------------
    /**
     * Applies decision tree recursively to all child nodes
     * and returns score value.
     *
     * @param miningVector vector to be classified
     * @return score value of classfication
     * @throws MiningException could not run this method
     */
    public double apply( MiningVector miningVector ) throws MiningException
    { //may be mistake!
      return 0;
    }


    /**
     * Returns score value as string.
     *
     * @return string of score value
     */
    protected String getScoreString() {

      String sScore;
      if (target instanceof ELogicalAttribute)
      {
        ECategory category = target.getCategoricalProperties().getCategory( (int) score);
        if (category == null)
          sScore = "missing";
        else if (category.getValue() != null)
          sScore = category.getValue().toString();
        else
          sScore = category.getDisplayName();
      }
      else
        sScore = Double.toString(score);

      return sScore;
    }
}