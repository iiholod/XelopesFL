package org.eltech.ddm.classification.naivebayes.continious;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.environment.DataDistribution;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.Distributable;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Represents model for Continues bayes Classifier algorithm.
 * Extends base classification mining model {@link ClassificationMiningModel}
 *
 * @author Evgenii Titkov
 * @value model             - base values model for mining algorithm. It's used for
 * store information with the next provided structure
 * [class-value] ----> { mean : [attr1,attr2 .. attrN] , deviation :[attr1,attr2 .. attrN]}
 * @value currentClassIndex - this value specifies current class index in order to determine current
 * class value during execution phase.
 * @value classValues       - array of all class values
 */
public class ContinuousBayesModel extends ClassificationMiningModel implements Distributable {

    private static transient final Logger LOGGER = Logger.getLogger(ContinuousBayesModel.class.getName());
    private final transient static int BAYES_INTUT_MODEL = 1;

    private Double currentClassValue;
    private transient Iterator<Double> iterator;


    /**
     * Default constructor for a class
     *
     * @param settings - setting for mining function
     * @throws MiningException - in case of unexpected situations
     */
    public ContinuousBayesModel(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        sets.add(BAYES_INTUT_MODEL, new BayesModelElement("Bayes Model"));
    }

    public ContinuousBayesModel() {

    }

    @Override
    public void initModel() throws MiningException {
        MiningModelElement attrs = getElement(INDEX_ATTRIBUTE_SET);
        BayesModelElement miningModelElement = (BayesModelElement) sets.get(BAYES_INTUT_MODEL);
        miningModelElement.setAttrCount(attrs.size());
        for (int i = 0; i < attrs.size(); i++) { // loop for attributes
            if (!target.getName().equals(attrs.getElement(i).getID())) {
                MiningModelElement element = attrs.getElement(i);
                String attrName = element.getID();
                MiningModelElement attrElem = new MiningModelElement(attrName) {
                    @Override
                    protected String propertiesToString() {
                        return "";
                    }

                    @Override
                    public void merge(List<MiningModelElement> elements) {
                    }
                };
                addElement(index(BAYES_INTUT_MODEL), attrElem);
            }
        }

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double apply(MiningVector miningVector) throws MiningException {
        throw new IllegalAccessError("Not implemented");
    }

    /**
     * Calculates final attribute probability
     *
     * @param value     - attribute value
     * @param mean      - attribute mean
     * @param deviation - attribute deviation
     * @return - probability value
     */
    private double attrProbability(double value, double mean, double deviation) {
        double exponent = Math.exp(-(Math.pow(value - mean, 2) / (2 * Math.pow(deviation, 2))));
        return (1 / (Math.sqrt(2 * Math.PI) * deviation)) * exponent;
    }

    /**
     * Applies trained model to the input data
     *
     * @param inputData - input data for algorithm
     * @return - result collection with the next structure {class-value} --> {probability}
     */
    public Map<Double, BigDecimal> apply(double[] inputData) {
        Map<Double, BigDecimal> probabilities = new HashMap<>();
        BayesModelElement element = (BayesModelElement) sets.get(BAYES_INTUT_MODEL);

        element.getModel().keySet().forEach(key -> {
            probabilities.put(key, BigDecimal.ONE);
            double[][] classAttributeList = element.getModel().get(key);

            for (int i = 0; i < classAttributeList.length; i++) {
                double mean = classAttributeList[i][0];
                double dev = classAttributeList[i][1];
                if (mean > 0 && dev > 0) {
                    BigDecimal probability = probabilities.get(key).multiply(BigDecimal.valueOf(attrProbability(inputData[i], mean, dev)));
                    probabilities.put(key, probability);
                }
            }
        });

        return probabilities;
    }

    /**
     * Gets model
     *
     * @return - algorithm model
     */
    public Map<Double, double[][]> getModel() {
        return ((BayesModelElement) sets.get(BAYES_INTUT_MODEL)).getModel();
    }

    private BayesModelElement getBayesElement() {
        return (BayesModelElement) sets.get(BAYES_INTUT_MODEL);
    }


    public void putValue(int atrIndex, double[] values, double key) {
        double[][] data = getModel().get(key) == null ? new double[values.length][2] : getModel().get(key);
        try {
            double atrValue = values[atrIndex];
            data[atrIndex][0] += atrValue;
            data[atrIndex][1] += atrValue * atrValue;
            getModel().put(key, data);
            if (atrIndex == values.length - 1) {
                Map<Double, Integer> classLengths = getBayesElement().getClassLengths();
                Integer length = classLengths.get(key);
                if (length == null) {
                    classLengths.put(key, 1);
                } else {
                    classLengths.put(key, ++length);
                }
                getBayesElement().incrementLength();
                getBayesElement().getClassValues().add(key);
            }

        } catch (Exception ex) {
            LOGGER.severe("Found corrupted record, program will ignore it");
            LOGGER.info("Nothing to worry about, we continue the execution normally...)");
        }
    }

//    /**
//     * Calculates sums which are required to calculate mean and dev in 1 pass
//     *
//     * @param key    - current class key
//     * @param data   - current array data
//     * @param values - values in fill in
//     */
//    private void fillArray(double key, double[][] data, double[] values) {
//        IntStream.range(0, values.length).forEach(attr -> {
//            data[attr][0] += values[attr];
//            data[attr][1] += values[attr] * values[attr];
//        });
//        getModel().put(key, data);
//        Map<Double, Integer> classLengths = getBayesElement().getClassLengths();
//        Integer length = classLengths.get(key);
//        if (length == null) {
//            classLengths.put(key, 1);
//        } else {
//            classLengths.put(key, ++length);
//        }
//
//    }

    public void initIterator() {
        this.iterator = getBayesElement().getClassValues().iterator();
    }

    public double[][] getCurrentModelValue() {
        return getModel().get(currentClassValue);
    }

    public Double getCurrentClassValue() {
        return currentClassValue;
    }

    public int getClassLength(double key) {
        return getBayesElement().getClassLengths().get(key);
    }

    public void next() {
        currentClassValue = iterator.hasNext() ? iterator.next() : null;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public boolean currIsLastElement(int[] indexSet) throws MiningException {
        MiningModelElement elem = sets.get(BAYES_INTUT_MODEL);
        return getCurrentAttributeIndex() >= elem.size();
    }


    @Override
    public void setDistributionType(DataDistribution dist) {
        getBayesElement().setDist(dist);
    }

    @Override
    public DataDistribution getDistributionType() {
        return getBayesElement().getDist();
    }
}