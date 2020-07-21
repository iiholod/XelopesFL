package org.eltech.ddm.classification.naivebayes.continious;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NaiveBayesTemplate {

    private static final String FIRST_CLASS = "FIRST CLASS";
    private static final String SECOND_CLASS = "SECOND CLASS";

    private static final Map<String, List<double[]>> SAMPLE_MAP = new HashMap<>();


    static {
        SAMPLE_MAP.put(FIRST_CLASS,
                Stream.of(
                        array(190, 85),
                        array(187, 80),
                        array(187, 90),
                        array(190, 95)).collect(Collectors.toList()));

        SAMPLE_MAP.put(SECOND_CLASS,
                Stream.of(
                        array(170, 80),
                        array(168, 79),
                        array(165, 65),
                        array(175, 70),
                        array(175, 75),
                        array(175, 80),
                        array(180, 75),
                        array(167, 75),
                        array(170, 69),
                        array(170, 70),
                        array(170, 70),
                        array(173, 74)).collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        NaiveBayesTemplate template = new NaiveBayesTemplate();
        Map<String, List<double[]>> summary = template.mapByClass(SAMPLE_MAP);

        List<Map<String, Double>> probabilityList = template.calculateProbabilities(summary, Arrays.asList(array(170,82),array(185, 95)));
        List<String> predictions = template.predictions(probabilityList);
        System.out.println(Arrays.toString(predictions.toArray()));
    }


    private List<String> predictions(List<Map<String, Double>> probabilityList) {
        return probabilityList.stream()
                .map(map -> map.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey())
                .collect(Collectors.toList());
    }


    private Map<String, List<double[]>> mapByClass(Map<String, List<double[]>> map) {
        Map<String, List<double[]>> result = new HashMap<>();
        map.keySet().forEach(key -> {
            Map<Integer, List<Double>> attrMap = mapDataByAttribute(map.get(key));
            double[] mean = attrMap.keySet().stream().mapToDouble(mapKey -> mean(attrMap.get(mapKey))).toArray();
            double[] deviation = IntStream.range(0, mean.length).mapToDouble(value -> deviation(mean[value], attrMap.get(value))).toArray();
            result.put(key, Arrays.asList(mean, deviation));
        });
        return result;
    }

    /**
     * Maps values for each attributes
     *
     * @param data - initial data
     * @return - mapped collection
     */
    private Map<Integer, List<Double>> mapDataByAttribute(List<double[]> data) {
        Map<Integer, List<Double>> converted = new HashMap<>();
        data.forEach(array -> IntStream.range(0, data.get(0).length).forEach(value -> {
            if (converted.get(value) != null) {
                converted.get(value).add(array[value]);
            } else {
                converted.put(value, new ArrayList<>(Collections.singletonList(array[value])));
            }
        }));
        return converted;
    }


    /**
     * Syntax sugar
     *
     * @param values - set of values
     * @return - array of values
     */
    private static double[] array(double... values) {
        return values;
    }


    private double attrProbability(double value, double mean, double deviation) {
        double exponent = Math.exp(-(Math.pow(value - mean, 2) / (2 * Math.pow(deviation, 2))));
        return (1 / (Math.sqrt(2 * Math.PI) * deviation)) * exponent;
    }

    private Map<String, Double> calculateProbability(Map<String, List<double[]>> summary, double[] inputData) {
        Map<String, Double> probabilities = new HashMap<>();
        summary.keySet().forEach(key -> {
            probabilities.put(key, 1D);
            List<double[]> classAttributeList = summary.get(key);

            for (int i = 0; i < classAttributeList.size(); i++) {
                double mean = classAttributeList.get(0)[i];
                double dev = classAttributeList.get(1)[i];
                double probability = probabilities.get(key) * attrProbability(inputData[i], mean, dev);
                probabilities.put(key, probability);
            }
        });

        return probabilities;
    }

    private List<Map<String, Double>> calculateProbabilities(Map<String, List<double[]>> summary, List<double[]> inputSet) {
        return inputSet.stream().map(input -> calculateProbability(summary, input)).collect(Collectors.toList());
    }


    /**
     * Calculates mean of the given values
     *
     * @param values - arrays of values
     * @return - mean
     */
    private double mean(List<Double> values) {
        return values.stream().mapToDouble(v -> v).average().orElse(0);
    }

    /**
     * Calculates gaussian deviation of the given values
     *
     * @param mean   - mean for a given values
     * @param values - arrays of values
     * @return - gaussian deviation
     */
    private double deviation(double mean, List<Double> values) {
        return values.stream()
                .reduce(0d, (previous, current) -> previous + (Math.pow(current - mean, 2) / values.size()));
    }


}
