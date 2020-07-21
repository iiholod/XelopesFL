package org.eltech.ddm.classification.naivebayes.continious;

import org.eltech.ddm.environment.DataDistribution;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BayesModelElement extends MiningModelElement {

    protected final Map<Double, double[][]> model = new HashMap<>();
    private final Map<Double, Integer> classLengths = new HashMap<>();
    private Set<Double> classValues = new LinkedHashSet<>();
    private int length;
    private int attrCount;
    private DataDistribution dist;


    public BayesModelElement(String id) {
        super(id);
    }

    public BayesModelElement() {

    }

    @Override
    protected String propertiesToString() {
        return String.format("Class values: %s, Model: %s, Length: %d, Attr-count - %d",
                classLengths.toString(), model.toString(), length, attrCount);
    }

    @Override
    public void merge(List<MiningModelElement> elements) {
        List<BayesModelElement> bayesElements = elements.stream()
                .map(BayesModelElement.class::cast)
                .collect(Collectors.toList());
        attrCount = bayesElements.stream().mapToInt(el -> el.getAttrCount()).sum();
        /*
         * Merging of data model
         */
        Map<Double, List<double[][]>> merged = bayesElements.stream()
                .map(BayesModelElement::getModel)
                .flatMap(en -> en.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        switch (dist) {
            case HORIZONTAL_DISTRIBUTION:
                mergeHorizontalModels(bayesElements, merged);
                break;
            case VERTICAL_DISTRIBUTION:
                mergeVerticalModels(merged);
                break;
        }

    }

    private void mergeVerticalModels(Map<Double, List<double[][]>> merged) {
        for (Double key : merged.keySet()) {
            //attrCount * 2 - dirty hack , resolve it later
            double[][] array = new double[attrCount*2][2];
            int currentIndex = 0;
            for (double[][] part : merged.get(key)) {
                System.out.println();
                for (int i = 0; i < part.length; i++) {
                    array[currentIndex++] = part[i];
                }
            }
            model.put(key, array);
        }
    }

    private void mergeHorizontalModels(List<BayesModelElement> bayesElements, Map<Double, List<double[][]>> merged) {
        this.attrCount = bayesElements.get(0).getAttrCount();
        /*
         * Here we're joining model metadata
         */
        bayesElements.forEach(el -> {
            this.length += el.length;
            this.classValues.addAll(el.getClassValues());
            el.classLengths.forEach((k, v) -> this.classLengths.merge(k, v, Integer::sum));
        });

        /*
         * Partials sum summary
         */
        merged.keySet().forEach(key -> {
            double[][] reduced = merged.get(key).stream()
                    .reduce(new double[attrCount][2], (initial, current) -> {
                        IntStream.range(0, current.length).forEach(index -> {
                            initial[index][0] += current[index][0];
                            initial[index][1] += current[index][1];
                        });
                        return initial;
                    });
            model.put(key, reduced);
        });
    }

    public void incrementLength() {
        this.length++;
    }

    public Map<Double, double[][]> getModel() {
        return model;
    }

    public Map<Double, Integer> getClassLengths() {
        return classLengths;
    }

    public Set<Double> getClassValues() {
        return classValues;
    }

    public int getLength() {
        return length;
    }

    public int getAttrCount() {
        return attrCount;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setAttrCount(int attrCount) {
        this.attrCount = attrCount;
    }

    public DataDistribution getDist() {
        return dist;
    }

    public void setDist(DataDistribution dist) {
        this.dist = dist;
    }
}
